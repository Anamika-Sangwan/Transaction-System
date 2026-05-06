package com.anamika.transactionsystem.service;

import com.anamika.transactionsystem.kafka.KafkaProducerService;
import com.anamika.transactionsystem.repo.AccountRepository;
import com.anamika.transactionsystem.repo.TransactionRepository;
import com.anamika.transactionsystem.entity.Account;
import com.anamika.transactionsystem.entity.Transaction;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.kafka.core.KafkaTemplate;
import com.anamika.transactionsystem.constants.enums.TransactionStatus;
import com.anamika.transactionsystem.dto.TransactionRequestDto;
import com.anamika.transactionsystem.dto.TransactionResponseDto;
import com.anamika.transactionsystem.event.TransactionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.UUID;
import java.time.LocalDateTime;

@Service
public class TransactionService {
    private KafkaProducerService kafkaProducerService;
    private AccountRepository accountRepository;
    private TransactionRepository transactionRepository;
    private KafkaTemplate<String, TransactionEvent> kafkaTemplate;

    @Transactional
    public void processTransaction(TransactionEvent event) {
        Account sender = accountRepository.findById(event.getSenderAccountId())
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        Account receiver = accountRepository.findById(event.getReceiverAccountId())
                .orElseThrow(() -> new RuntimeException("Receiver not found"));
        if (sender.getBalance() == null || sender.getBalance() < event.getAmount()) {
            throw new RuntimeException("Insufficient balance");
        }
        sender.setBalance(sender.getBalance() - event.getAmount());
        receiver.setBalance(receiver.getBalance() + event.getAmount());
        accountRepository.save(sender);
        accountRepository.save(receiver);
        Transaction txn = new Transaction();
        if (sender.getBalance() < event.getAmount()) {
            txn.setStatus(TransactionStatus.FAILED);
            txn.setFailureReason("Insufficient balance");
            transactionRepository.save(txn);
            return;
        }
        txn.setTransactionId(UUID.randomUUID().toString());
        txn.setSenderAccountId(sender.getId());
        txn.setReceiverAccountId(receiver.getId());
        txn.setAmount(event.getAmount());
        txn.setStatus(TransactionStatus.SUCCESS);
        txn.setCreatedAt(LocalDateTime.now());
        transactionRepository.save(txn);
    }

    public TransactionResponseDto sendMoney(TransactionRequestDto request) {
        // Create event
        TransactionEvent event = new TransactionEvent();
        event.setSenderAccountId(request.getSenderAccountId());
        event.setReceiverAccountId(request.getReceiverAccountId());
        event.setAmount(request.getAmount());
        // Send to Kafka
        kafkaProducerService.sendTransaction(event);
        // Immediate response
        TransactionResponseDto response = new TransactionResponseDto();
        response.setStatus("PENDING");
        response.setMessage("Transaction is being processed");
        return response;
    }

    public Transaction findByTransactionId(String transactionId) {
        return transactionRepository.findByTransactionId(transactionId).orElse(new Transaction());

    }
}
