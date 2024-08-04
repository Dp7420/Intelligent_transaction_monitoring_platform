package com.www.demo.app.itsmdemo.service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.www.demo.app.itsmdemo.entity.Account;
import com.www.demo.app.itsmdemo.entity.Transaction;
import com.www.demo.app.itsmdemo.repository.AccountRepository;
import com.www.demo.app.itsmdemo.repository.TransactionRepository;
import com.www.demo.app.itsmdemo.request.CardTransactionCompleteRequest;
import com.www.demo.app.itsmdemo.request.CardTransactionRequest;
import com.www.demo.app.itsmdemo.request.NetBankingRequest;
import com.www.demo.app.itsmdemo.request.NetBankingVerifyRequest;
import com.www.demo.app.itsmdemo.request.TransactionCompletionRequest;
import com.www.demo.app.itsmdemo.request.TransactionRequest;

@Service
public class TransactionService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private TransactionRepository transactionRepository;
    
    @Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    @Autowired
    private EmailService emailService;

    private static final String TOPIC = "transactions";

    @Transactional
    public ResponseEntity<?> initiateTransactionWithUpi(TransactionRequest request) {
        Optional<Account> optionalAccount = accountService.getAccountByUpiId(request.getUpiId());
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("Account not found");
        }

        Account account = optionalAccount.get();

        // Check available balance
        if (account.getBalance() < request.getAmount()) {
            return ResponseEntity.badRequest().body("Insufficient balance");
        }

        // Verify UPI password
        if (!request.getUpiPassword().equals(account.getUpiPassword())) {
            return ResponseEntity.badRequest().body("UPI password is incorrect");
        }

        // Send OTP email
        emailService.sendOtpEmail(account.getEmail());

        return ResponseEntity.ok("OTP sent to email. Please verify OTP to complete the transaction.");
    }

    @Transactional
    public ResponseEntity<?> verifyOtpAndCompleteTransactionWithUpi(TransactionCompletionRequest request) {
        Optional<Account> optionalAccount = accountService.getAccountByUpiId(request.getUpiId());
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("Account not found");
        }

        Account account = optionalAccount.get();

        // Verify UPI password
        if (!request.getUpiPassword().equals(account.getUpiPassword())) {
            return ResponseEntity.badRequest().body("UPI password is incorrect");
        }

        if (!emailService.verifyOtp(account.getEmail(), request.getOtp())) {
            emailService.sendTransactionStatusEmail(account.getEmail(), false, "", String.valueOf(request.getAmount()), "OTP verification failed");
            return ResponseEntity.badRequest().body("OTP verification failed");
        }

        // Check available balance
        if (account.getBalance() < request.getAmount()) {
            emailService.sendTransactionStatusEmail(account.getEmail(), false, "", String.valueOf(request.getAmount()), "Insufficient balance");
            return ResponseEntity.badRequest().body("Insufficient balance");
        }

        return ResponseEntity.ok(processTransaction(account, request.getAmount(), "UPI", account.getEmail()));
    }

    @Transactional
    public ResponseEntity<?> initiateTransactionWithNetbanking(NetBankingRequest request) {
        Optional<Account> optionalAccount = accountRepository.findByNetbankingUserId(request.getNetbankingUserId());
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("Account not found");
        }

        Account account = optionalAccount.get();

        if (account.isLocked()) {
            return ResponseEntity.badRequest().body("Account is locked due to multiple failed attempts. Please try again later.");
        }

        // Check available balance
        if (account.getBalance() < request.getAmount()) {
            return ResponseEntity.badRequest().body("Insufficient balance");
        }

        // Verify netbanking details
        if (!request.getNetbankingUserId().equals(account.getNetbankingUserId()) || !request.getNetbankingPassword().equals(account.getNetbankingPassword())) {
            account.incrementFailedAttempts();
            if (account.getFailedAttempts() >= 3) {
                account.lockAccount();
                accountService.updateAccount(account);
                return ResponseEntity.badRequest().body("Account locked due to multiple failed attempts. Please try again later.");
            }
            accountService.updateAccount(account);
            return ResponseEntity.badRequest().body("Netbanking details are incorrect");
        }

        account.resetFailedAttempts();
        accountService.updateAccount(account);

        emailService.sendOtpEmail(account.getEmail());
        return ResponseEntity.ok("OTP sent to email. Please verify OTP to complete the transaction.");
    }

    @Transactional
    public ResponseEntity<?> verifyOtpAndCompleteTransactionWithNetbanking(NetBankingVerifyRequest request) {
        Optional<Account> optionalAccount = accountRepository.findByNetbankingUserId(request.getNetbankingUserId());
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("Account not found");
        }

        Account account = optionalAccount.get();

        // Verify netbanking details again to ensure security
        if (!request.getNetbankingUserId().equals(account.getNetbankingUserId()) || !request.getNetbankingPassword().equals(account.getNetbankingPassword())) {
            return ResponseEntity.badRequest().body("Netbanking details are incorrect");
        }

        if (account.isPaymentOptionBlocked("Netbanking")) {
            return ResponseEntity.badRequest().body("Netbanking is blocked due to multiple failed OTP attempts");
        }

        if (!emailService.verifyOtp(account.getEmail(), request.getOtp())) {
            account.incrementOtpFailedAttemptsNetbanking();
            accountService.updateAccount(account);
            return ResponseEntity.badRequest().body("OTP verification failed");
        }

        account.resetOtpFailedAttemptsNetbanking();
        accountService.updateAccount(account);

        if (account.getBalance() < request.getAmount()) {
            return ResponseEntity.badRequest().body("Insufficient balance");
        }

        return ResponseEntity.ok(processTransaction(account, request.getAmount(), "Netbanking", account.getEmail()));
    }

    @Transactional
    public ResponseEntity<?> initiateTransactionWithCard(CardTransactionRequest request) {
        Optional<Account> optionalAccount = accountRepository.findByCardNumber(request.getCardNumber());
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("Account not found");
        }

        Account account = optionalAccount.get();

        if (account.isLocked()) {
            return ResponseEntity.badRequest().body("Account is locked due to multiple failed attempts. Please try again later.");
        }

        // Check available balance
        if (account.getBalance() < request.getAmount()) {
            return ResponseEntity.badRequest().body("Insufficient balance");
        }

        // Verify card details
        if (!request.getCardNumber().equals(account.getCardNumber()) || !request.getCardExpiry().equals(account.getCardExpiry()) || !request.getCardCvv().equals(account.getCardCvv())) {
            account.incrementFailedAttempts();
            if (account.getFailedAttempts() >= 3) {
                account.lockAccount();
                accountService.updateAccount(account);
                return ResponseEntity.badRequest().body("Account locked due to multiple failed attempts. Please try again later.");
            }
            accountService.updateAccount(account);
            return ResponseEntity.badRequest().body("Card details are incorrect");
        }

        account.resetFailedAttempts();
        accountService.updateAccount(account);

        emailService.sendOtpEmail(account.getEmail());
        return ResponseEntity.ok("OTP sent to email. Please verify OTP to complete the transaction.");
    }

    @Transactional
    public ResponseEntity<?> verifyOtpAndCompleteTransactionWithCard(CardTransactionCompleteRequest request) {
        Optional<Account> optionalAccount = accountRepository.findByCardNumber(request.getCardNumber());
        if (optionalAccount.isEmpty()) {
            return ResponseEntity.badRequest().body("Account not found");
        }

        Account account = optionalAccount.get();

        // Verify card details again to ensure security
        if (!request.getCardNumber().equals(account.getCardNumber()) || !request.getCardExpiry().equals(account.getCardExpiry()) || !request.getCardCvv().equals(account.getCardCvv())) {
            return ResponseEntity.badRequest().body("Card details are incorrect");
        }

        if (account.isPaymentOptionBlocked("Card")) {
            return ResponseEntity.badRequest().body("Card payments are blocked due to multiple failed OTP attempts");
        }

        if (!emailService.verifyOtp(account.getEmail(), request.getOtp())) {
            account.incrementOtpFailedAttemptsCard();
            accountService.updateAccount(account);
            return ResponseEntity.badRequest().body("OTP verification failed");
        }

        account.resetOtpFailedAttemptsCard();
        accountService.updateAccount(account);

        if (account.getBalance() < request.getAmount()) {
            return ResponseEntity.badRequest().body("Insufficient balance");
        }

        return ResponseEntity.ok(processTransaction(account, request.getAmount(), "Card", account.getEmail()));
    }

    private Transaction processTransaction(Account account, double amount, String type, String email) {
        account.setBalance(account.getBalance() - amount);
        accountRepository.save(account);

        Transaction transaction = new Transaction();
        transaction.setTransactionId(UUID.randomUUID().toString());
        transaction.setTransactionType(type);
        transaction.setAmount(amount);
        transaction.setStatus("Success");
        transaction.setIsFraudulent(false);
        transaction.setCurrency("USD");
        transaction.setLocation("Nanded");
        transaction.setTimestamp(new Date());
        transaction.setAccount(account);
        transactionRepository.save(transaction);

        emailService.sendTransactionStatusEmail(email, true, transaction.getTransactionId(), String.valueOf(amount), "Success");

        // Publish to Kafka for fraud detection
        kafkaTemplate.send(TOPIC, transaction);

        return transaction;
    }
}
