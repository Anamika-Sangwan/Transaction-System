package com.anamika.transactionsystem.kafka;

import com.anamika.transactionsystem.event.TransactionEvent;
import com.anamika.transactionsystem.service.TransactionService;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

@Service
public class KafkaConsumerService {

    private TransactionService transactionService;

    @KafkaListener(topics = "transactions", groupId = "transaction-group")
    public void consume(TransactionEvent event) {

        transactionService.processTransaction(event);

    }
}
