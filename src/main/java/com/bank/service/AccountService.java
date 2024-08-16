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

    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Account updateAccount(Account account) {
        Optional<Account> existingAccountOpt = accountRepository.findById(account.getId());
        if (existingAccountOpt.isPresent()) {
            Account existingAccount = existingAccountOpt.get();
            existingAccount.setName(account.getName());
            existingAccount.setAge(account.getAge());
            existingAccount.setGender(account.getGender());
            existingAccount.setCity(account.getCity());
            existingAccount.setBalance(account.getBalance());
            return accountRepository.save(existingAccount);
        } else {
            throw new RuntimeException("Account not found with ID: " + account.getId());
        }
    }

    public Account getAccountById(Long id) {
        return accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found with ID: " + id));
    }

    public void deleteAccount(Long id) {
        accountRepository.deleteById(id);
    }

    public List<Account> getAllAccounts() {
        return accountRepository.findAll();
    }
}
