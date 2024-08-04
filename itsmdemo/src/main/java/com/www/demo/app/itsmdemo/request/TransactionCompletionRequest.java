package com.www.demo.app.itsmdemo.request;

import lombok.Data;

@Data
public class TransactionCompletionRequest {
	private String upiId;
    private String upiPassword;
    private String otp;
    private double amount;
}
