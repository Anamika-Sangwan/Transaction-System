package com.anamika.transactionsystem.service;

import com.anamika.transactionsystem.constants.enums.TransactionStatus;
import com.anamika.transactionsystem.dto.TransactionRequestDto;
import com.anamika.transactionsystem.dto.TransactionResponseDto;
import com.anamika.transactionsystem.entity.Account;
import com.anamika.transactionsystem.entity.Transaction;
import com.anamika.transactionsystem.repo.AccountRepository;
import com.anamika.transactionsystem.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class TransactionService {
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @Transactional
    public TransactionResponseDto sendMoney(TransactionRequestDto request) {
        Account sender = accountRepository.findById(request.getSenderAccountId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Account receiver = accountRepository.findById(request.getReceiverAccountId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        if (sender.getBalance() < request.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }
        sender.setBalance(sender.getBalance() - request.getAmount());
        receiver.setBalance(receiver.getBalance() + request.getAmount());
        accountRepository.save(sender);
        accountRepository.save(receiver);
        Transaction txn = new Transaction();
        txn.setTransactionId(UUID.randomUUID().toString());
        txn.setSenderAccountId(sender.getId());
        txn.setReceiverAccountId(receiver.getId());
        txn.setAmount(request.getAmount());
        txn.setStatus(TransactionStatus.SUCCESS);
        txn.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(txn);
        TransactionResponseDto response = new TransactionResponseDto();
        response.setTransactionId(txn.getTransactionId());
        response.setStatus("SUCCESS");
        response.setMessage("Transaction completed");

        return response;
    }
}
