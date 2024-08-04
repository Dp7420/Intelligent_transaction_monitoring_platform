package com.www.demo.app.itsmdemo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.www.demo.app.itsmdemo.entity.Transaction;

@RestController
public class TestController {
	@Autowired
    private KafkaTemplate<String, Transaction> kafkaTemplate;

    private static final String TOPIC = "transactions";

    @PostMapping("/sendTransaction")
    public String sendTransaction(@RequestBody Transaction transaction) {
        kafkaTemplate.send(TOPIC, transaction);
        return "Transaction sent successfully";
    }

}
