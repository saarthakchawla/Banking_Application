package com.bank.controller;

import com.bank.model.Account;
import com.bank.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));
    }

    @PostMapping
    public Account createAccount(@RequestParam String ownerName,
                                 @RequestParam int age,
                                 @RequestParam String gender,
                                 @RequestParam String city,
                                 @RequestParam(required = false) Double initialAmount) {

        if (initialAmount == null) {
            initialAmount = 5000.0; // Set default balance if not provided
        }

        Account account = new Account(ownerName, age, gender, city, initialAmount);
        return accountRepository.save(account);
    }

    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id, @RequestBody Account accountDetails) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));
        account.setOwnerName(accountDetails.getOwnerName());
        account.setAge(accountDetails.getAge());
        account.setGender(accountDetails.getGender());
        account.setCity(accountDetails.getCity());
        account.setBalance(accountDetails.getBalance());
        return accountRepository.save(account);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));
        accountRepository.delete(account);
        return ResponseEntity.ok("Account deleted successfully.");
    }
}
