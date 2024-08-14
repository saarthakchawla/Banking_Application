package com.bank.controller;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/transfer")
    public ResponseEntity<String> transfer(@RequestParam Long fromAccountId,
                                           @RequestParam Long toAccountId,
                                           @RequestParam double amount) {
        Account fromAccount = accountRepository.findById(fromAccountId)
                .orElseThrow(() -> new RuntimeException("Source account not found with ID: " + fromAccountId));
        Account toAccount = accountRepository.findById(toAccountId)
                .orElseThrow(() -> new RuntimeException("Destination account not found with ID: " + toAccountId));

        if (fromAccount.getBalance() < amount) {
            throw new RuntimeException("Insufficient funds in source account.");
        }

        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);

        Transaction debitTransaction = new Transaction("debit", amount, fromAccount);
        Transaction creditTransaction = new Transaction("credit", amount, toAccount);

        transactionRepository.save(debitTransaction);
        transactionRepository.save(creditTransaction);

        accountRepository.save(fromAccount);
        accountRepository.save(toAccount);

        return ResponseEntity.ok("Transferred " + amount + " from Account ID " + fromAccountId + " to Account ID " + toAccountId);
    }

    @GetMapping("/{accountId}")
    public List<Transaction> getTransactionsByAccount(@PathVariable Long accountId) {
        Account account = accountRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + accountId));
        List<Transaction> transactions = account.getTransactions();
        transactions.sort(Comparator.comparing(Transaction::getTimestamp).reversed());
        return transactions;
    }
}
