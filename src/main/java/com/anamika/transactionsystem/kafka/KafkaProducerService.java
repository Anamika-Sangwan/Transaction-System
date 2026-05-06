package com.anamika.transactionsystem.kafka;

import com.anamika.transactionsystem.event.TransactionEvent;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;

@Service
public class KafkaProducerService {
    private KafkaTemplate<String, Object> kafkaTemplate;

    public void sendTransaction(TransactionEvent event) {
        kafkaTemplate.send("transactions", event);
    }
}
