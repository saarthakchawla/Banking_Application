package com.bank.service;

import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.repository.AccountRepository;
import com.bank.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    public void transfer(Long fromAccountId, Long toAccountId, double amount) {
        try {
            Account fromAccount = accountRepository.findById(fromAccountId)
                    .orElseThrow(() -> new RuntimeException("Source account not found with ID: " + fromAccountId));
            Account toAccount = accountRepository.findById(toAccountId)
                    .orElseThrow(() -> new RuntimeException("Destination account not found with ID: " + toAccountId));

            if (fromAccount.getBalance() < amount) {
                throw new RuntimeException("Insufficient funds in source account.");
            }

            // Update balances
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);

            // Create transaction records
            Transaction debitTransaction = new Transaction("debit", amount, fromAccountId);
            Transaction creditTransaction = new Transaction("credit", amount, toAccountId);

            // Save transactions
            transactionRepository.save(debitTransaction);
            transactionRepository.save(creditTransaction);

            // Save updated account balances
            accountRepository.save(fromAccount);
            accountRepository.save(toAccount);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public List<Transaction> getTransactionsByAccount(Long accountId) {
        try {
            return transactionRepository.findByAccountIdOrderByTimestampDesc(accountId);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
