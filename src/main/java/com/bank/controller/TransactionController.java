package com.bank.controller;

import com.bank.model.Transaction;
import com.bank.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @PostMapping("/transfer")
    public String transfer(@RequestBody Transaction transactionRequest) {
        transactionService.transfer(transactionRequest.getFromAccountId(),
                transactionRequest.getToAccountId(),
                transactionRequest.getAmount());
        return "Transfer successful from Account " + transactionRequest.getFromAccountId() + " to Account " + transactionRequest.getToAccountId();
    }

    @GetMapping("/history/{accountId}")
    public List<Transaction> getTransactionHistory(@PathVariable Long accountId) {
        return transactionService.getTransactionsByAccount(accountId);
    }
}
