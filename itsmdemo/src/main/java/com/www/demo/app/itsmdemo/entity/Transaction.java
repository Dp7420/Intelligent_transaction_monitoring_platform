package com.www.demo.app.itsmdemo.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String transactionId;



    @Column(nullable = false)
    private Date timestamp;
    private String location;

    private Boolean isFraudulent;
    private Double amount;
    private String currency;
    private String transactionType;
    private String status;
    private String description;
    
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}