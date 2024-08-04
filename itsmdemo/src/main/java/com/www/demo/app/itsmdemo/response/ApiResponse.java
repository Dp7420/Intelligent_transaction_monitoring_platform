package com.www.demo.app.itsmdemo.response;

import lombok.Data;

@Data
public class ApiResponse {

	private String transactionId;
	private boolean isFraudulent;

	public ApiResponse(String transactionId, boolean isFraudulent) {
		this.transactionId = transactionId;
		this.isFraudulent = isFraudulent;
	}

}
