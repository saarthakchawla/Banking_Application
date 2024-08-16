package com.bank.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private double amount;
    private Date timestamp;

    private Long accountId;

    public Transaction() {
        this.timestamp = new Date();
    }

    public Transaction(String type, double amount, Long accountId) {
        this.type = type;
        this.amount = amount;
        this.timestamp = new Date();
        this.accountId = accountId;
    }
}
