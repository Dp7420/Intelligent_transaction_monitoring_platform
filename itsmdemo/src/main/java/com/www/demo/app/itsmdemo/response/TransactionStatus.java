package com.www.demo.app.itsmdemo.response;

import com.www.demo.app.itsmdemo.entity.Transaction;

import lombok.Data;

@Data
public class TransactionStatus {
	private Transaction transaction;
	private boolean isFraudulent;

	public TransactionStatus(Transaction transaction, boolean isFraudulent) {
		this.transaction = transaction;
		this.isFraudulent = isFraudulent;
	}
}
