package com.www.demo.app.itsmdemo.request;

import lombok.Data;

@Data
public class NetBankingRequest {
	private String netbankingUserId;
	private String netbankingPassword;
	private Double amount;
}
