package com.www.demo.app.itsmdemo.request;

import lombok.Data;

@Data
public class NetbankingTransactionRequest {
//	private String accountNumber;
	private Double amount;
	private String netbankingUserId;
	private String netbankingPassword;

}
