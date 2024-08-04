package com.www.demo.app.itsmdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.www.demo.app.itsmdemo.entity.Account;
import com.www.demo.app.itsmdemo.repository.AccountRepository;

import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public ResponseEntity<?> createAccount(Account account) {
        if (isAccountValid(account)) {
            accountRepository.save(account);
            return ResponseEntity.ok("Account created successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid Details all the information..!");
        }
    }

    public Optional<Account> getAccount(String accountNumber) {
        return accountRepository.findByAccountNumber(accountNumber);
    }

    public void updateAccount(Account account) {
        accountRepository.save(account);
    }

    public Optional<Account> getAccountByUpiId(String upiId) {
        return accountRepository.findByUpiId(upiId);
    }

    public Optional<Account> getAccountByNetbankingUserId(String netbankingUserId) {
        return accountRepository.findByNetbankingUserId(netbankingUserId);
    }

    public Optional<Account> getAccountByCardNumber(String cardNumber) {
        return accountRepository.findByCardNumber(cardNumber);
    }
    
    public Account findByAccountNumber(String accountNumber) {
        Optional<Account> acc = accountRepository.findByAccountNumber(accountNumber);
        return acc.orElse(null);
    }
    
    public Account findByAccountId(Long id) {
        return accountRepository.findByAccountId(id);
    }

    private boolean isAccountValid(Account account) {
        return account.getAccountNumber() != null && account.getEmail() != null && account.getMobile() != null
                && account.getBalance() != null && account.getUpiPassword() != null
                && account.getNetbankingUserId() != null && account.getNetbankingPassword() != null
                && account.getCardNumber() != null && account.getCardExpiry() != null && account.getCardCvv() != null
                && account.getUpiId() != null;
    }
}
