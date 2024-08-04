package com.www.demo.app.itsmdemo.controller;

//import org.springframework.beans.factory.annotation.Autowired;

//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.www.demo.app.itsmdemo.request.CardTransactionRequest;
//import com.www.demo.app.itsmdemo.request.NetbankingTransactionRequest;
//import com.www.demo.app.itsmdemo.request.UpiTransactionRequest;
//import com.www.demo.app.itsmdemo.service.TransactionService;
//
//@RestController
//@RequestMapping("/api/transactions")
//public class TransactionController {
//
//	@Autowired
//	private TransactionService transactionService;
//
//	@PostMapping("/upi")
//	public String createTransactionWithUpi(@RequestBody UpiTransactionRequest request) {
//		return transactionService.createTransactionWithUpi(request.getAccountNumber(), request.getAmount(),
//				request.getUpiPassword());
//	}
//
//	@PostMapping("/netbanking")
//	public String createTransactionWithNetbanking(@RequestBody NetbankingTransactionRequest request) {
//		return transactionService.createTransactionWithNetbanking(request.getAccountNumber(), request.getAmount(),
//				request.getNetbankingUserId(), request.getNetbankingPassword());
//	}
//
//	@PostMapping("/card")
//	public String createTransactionWithCard(@RequestBody CardTransactionRequest request) {
//		return transactionService.createTransactionWithCard(request.getAccountNumber(), request.getAmount(),
//				request.getCardNumber(), request.getCardExpiry(), request.getCardCvv());
//	}
//}

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.www.demo.app.itsmdemo.request.CardTransactionCompleteRequest;
import com.www.demo.app.itsmdemo.request.CardTransactionRequest;
import com.www.demo.app.itsmdemo.request.NetBankingRequest;
import com.www.demo.app.itsmdemo.request.NetBankingVerifyRequest;
import com.www.demo.app.itsmdemo.request.TransactionCompletionRequest;
import com.www.demo.app.itsmdemo.request.TransactionRequest;
import com.www.demo.app.itsmdemo.service.TransactionService;

@RestController
@RequestMapping("/api/transactions")
@CrossOrigin(origins = "http://localhost:4200")
public class TransactionController {

	@Autowired
	private TransactionService transactionService;

	@PostMapping("/upi/initiate")
	public ResponseEntity<?> initiateTransactionWithUpi(@RequestBody TransactionRequest request) {
		return ResponseEntity.ok(transactionService.initiateTransactionWithUpi(request));
	}

	@PostMapping("/upi/complete")
	public ResponseEntity<?> completeTransactionWithUpi(@RequestBody TransactionCompletionRequest request) {
		// Your logic to handle transaction completion
		return ResponseEntity.ok(transactionService.verifyOtpAndCompleteTransactionWithUpi(request));
	}

	@PostMapping("/netbanking/initiate")
	public ResponseEntity<?> initiateTransactionWithNetbanking(@RequestBody NetBankingRequest request) {
		return ResponseEntity.ok(transactionService.initiateTransactionWithNetbanking(request));
	}

	@PostMapping("/netbanking/complete")
	public ResponseEntity<?> verifyOtpAndCompleteTransactionWithNetbanking(
			@RequestBody NetBankingVerifyRequest request) {
		return ResponseEntity.ok(transactionService.verifyOtpAndCompleteTransactionWithNetbanking(request));
	}

	@PostMapping("/card/initiate")
	public ResponseEntity<?> initiateTransactionWithCard(@RequestBody CardTransactionRequest request) {
		return ResponseEntity.ok(transactionService.initiateTransactionWithCard(request));
	}

	@PostMapping("/card/complete")
	public ResponseEntity<?> verifyOtpAndCompleteTransactionWithCard(
			@RequestBody CardTransactionCompleteRequest request) {
		return ResponseEntity.ok(transactionService.verifyOtpAndCompleteTransactionWithCard(request));
	}
}
