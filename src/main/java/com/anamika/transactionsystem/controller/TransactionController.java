package com.anamika.transactionsystem.controller;

import com.anamika.transactionsystem.dto.TransactionRequestDto;
import com.anamika.transactionsystem.dto.TransactionResponseDto;
import com.anamika.transactionsystem.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transaction")
public class TransactionController {
    @Autowired
    private TransactionService transactionService;
    @PostMapping("/send")
    public ResponseEntity<TransactionResponseDto> sendMoney(
            @RequestBody TransactionRequestDto request) {
        return ResponseEntity.ok(transactionService.sendMoney(request));
    }
}
