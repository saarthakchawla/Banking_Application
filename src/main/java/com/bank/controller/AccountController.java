package com.bank.controller;

import com.bank.model.Account;
import com.bank.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public List<Account> getAllAccounts() {
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public Account getAccountById(@PathVariable Long id) {
        return accountService.getAccountById(id);
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
        return accountService.createAccount(account);
    }

    @PutMapping("/{id}")
    public Account updateAccount(@PathVariable Long id,
                                 @RequestBody Account accountDetails) {
        return accountService.updateAccount(id, accountDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id) {
        accountService.deleteAccount(id);
    }

    @PutMapping("/{id}/update-details")
    public Account updateAccountDetails(@PathVariable Long id,
                                        @RequestBody Account accountDetails) {
        return accountService.updateAccount(id, accountDetails);
    }
}
