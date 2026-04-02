package com.anamika.transactionsystem.dto;

public class TransactionResponseDto {

    private String transactionId;
    private String status;
    private String message;

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
