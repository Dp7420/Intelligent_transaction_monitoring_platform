package com.www.demo.app.itsmdemo.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.www.demo.app.itsmdemo.entity.Account;
import com.www.demo.app.itsmdemo.service.AccountService;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;


    @PostMapping("/create")
    public ResponseEntity<?> createAccount(@RequestBody Account account) {
    	return ResponseEntity.ok(accountService.createAccount(account));
    }

    @GetMapping("/get")
    public Optional<Account> getAccount(@RequestParam String accountNumber) {
        return accountService.getAccount(accountNumber);
    }
}
