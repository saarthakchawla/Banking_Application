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
    private double amount;
    private Date timestamp;

    private Long fromAccountId;
    private Long toAccountId;

    public Transaction() {
        this.timestamp = new Date();
    }
}