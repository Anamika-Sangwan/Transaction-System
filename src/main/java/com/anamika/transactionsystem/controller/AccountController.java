package com.anamika.transactionsystem.controller;

import com.anamika.transactionsystem.dto.AccountResponseDto;
import com.anamika.transactionsystem.entity.Account;
import com.anamika.transactionsystem.repo.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountRepository accountRepository;

    @GetMapping("/{id}")
    public AccountResponseDto getAccount(@PathVariable Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Account not found"));
        AccountResponseDto dto = new AccountResponseDto();
        dto.setAccountNumber(account.getAccountNumber());
        dto.setBalance(account.getBalance());
        dto.setStatus(account.getStatus().name());
        return dto;
    }
}
