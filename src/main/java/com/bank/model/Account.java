package com.bank.model;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String ownerName;
    private int age;
    private String gender;
    private String city;
    private double balance = 5000;

    public Account() {}

    public Account(String ownerName, int age, String gender, String city, double balance) {
        this.ownerName = ownerName;
        this.age = age;
        this.gender = gender;
        this.city = city;
        this.balance = balance;
    }
}
