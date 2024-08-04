package com.www.demo.app.itsmdemo.request;

import lombok.Data;

@Data
public class TransactionRequest {
	private String upiId;
	private String upiPassword;
	private double amount;

}
