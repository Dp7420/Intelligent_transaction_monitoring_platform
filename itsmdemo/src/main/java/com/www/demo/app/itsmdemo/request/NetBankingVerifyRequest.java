package com.www.demo.app.itsmdemo.request;

import lombok.Data;

@Data
public class NetBankingVerifyRequest {
	private String netbankingUserId;
	private String netbankingPassword;
	private Double amount;
	private String otp;
}
