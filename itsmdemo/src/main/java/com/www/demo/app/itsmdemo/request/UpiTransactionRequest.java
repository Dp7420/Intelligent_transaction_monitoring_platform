package com.www.demo.app.itsmdemo.request;

import lombok.Data;

@Data
public class UpiTransactionRequest {
	private String accountNumber;
	private Double amount;
	private String upiPassword;

}
