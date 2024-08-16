package com.bank.service;

import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public List<Account> getAllAccounts() {
        try {
            return accountRepository.findAll();
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public Account getAccountById(Long id) {
        try {
            return accountRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public Account createAccount(Account account) {
        try {
            return accountRepository.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public Account updateAccount(Long id, Account accountDetails) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));
            account.setOwnerName(accountDetails.getOwnerName());
            account.setAge(accountDetails.getAge());
            account.setGender(accountDetails.getGender());
            account.setCity(accountDetails.getCity());
            account.setBalance(accountDetails.getBalance());
            return accountRepository.save(account);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }

    public void deleteAccount(Long id) {
        try {
            Account account = accountRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));
            accountRepository.delete(account);
        } catch (Exception e) {
            throw new RuntimeException("Error: " + e.getMessage());
        }
    }
}
