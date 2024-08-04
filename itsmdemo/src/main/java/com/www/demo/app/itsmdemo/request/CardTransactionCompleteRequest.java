package com.www.demo.app.itsmdemo.request;

import lombok.Data;

@Data
public class CardTransactionCompleteRequest {
	private String accountNumber;
	private Double amount;
	private String cardNumber;
	private String cardExpiry;
	private String cardCvv;
	private String otp;
}
