package com.www.demo.app.itsmdemo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import com.www.demo.app.itsmdemo.entity.Account;
import com.www.demo.app.itsmdemo.entity.Transaction;

@Service
public class TransactionConsumer {

	@Autowired
	private AccountService accountService;

	@Autowired
	private EmailService emailService;

	@KafkaListener(topics = "transactions", groupId = "transaction-group", containerFactory = "kafkaListenerContainerFactory")
	public void listen(Transaction transaction, Acknowledgment acknowledgment) {
		System.out.println("Received transaction: " + transaction);

		if (isFraudulent(transaction)) {
			System.out.println("Fraud detected for transaction: " + transaction);
			handleFraudulentTransaction(transaction);
		} else {
			acknowledgment.acknowledge();
		}
	}

	private boolean isFraudulent(Transaction transaction) {
		return isAmountSuspicious(transaction) || isVelocitySuspicious(transaction)
				|| isLocationSuspicious(transaction);
	}

	private boolean isAmountSuspicious(Transaction transaction) {
		return transaction.getAmount() > 10000;
	}

	private boolean isVelocitySuspicious(Transaction transaction) {
		List<Transaction> recentTransactions = getRecentTransactions(transaction.getAccount().getAccountNumber(), 60);
		return recentTransactions.size() > 5;
	}

	private boolean isLocationSuspicious(Transaction transaction) {
		return isUnusualLocation(transaction.getAccount().getAccountNumber(), transaction.getLocation());
	}

	private List<Transaction> getRecentTransactions(String accountId, int seconds) {
		return new ArrayList<>();
	}

	private boolean isUnusualLocation(String accountId, String location) {
		return false;
	}

	private void handleFraudulentTransaction(Transaction transaction) {
		// Example: Lock account and send alert email
		Account account = transaction.getAccount();
		account.lockAccount();
//		accountService.updateAccount(account);
		emailService.sendFraudAlertEmail(account.getEmail(), transaction.getTransactionId(),"");
	}
}
