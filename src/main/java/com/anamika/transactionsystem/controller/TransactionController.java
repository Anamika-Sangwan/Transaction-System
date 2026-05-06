package com.anamika.transactionsystem.controller;

import com.anamika.transactionsystem.dto.TransactionRequestDto;
import com.anamika.transactionsystem.dto.TransactionResponseDto;
import com.anamika.transactionsystem.entity.Transaction;
import com.anamika.transactionsystem.service.TransactionService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    private TransactionService transactionService;

    @PostMapping("/send")
    public TransactionResponseDto send(@RequestBody TransactionRequestDto request) {
        return transactionService.sendMoney(request);
    }

    @GetMapping("/status/{transactionId}")
    public Transaction getStatus(@PathVariable String transactionId) {
        return transactionService.findByTransactionId(transactionId);
    }

}