package com.www.demo.app.itsmdemo.request;

import lombok.Data;

@Data
public class CardTransactionRequest {
//	private String accountNumber;
	private Double amount;
	private String cardNumber;
	private String cardExpiry;
	private String cardCvv;

}
