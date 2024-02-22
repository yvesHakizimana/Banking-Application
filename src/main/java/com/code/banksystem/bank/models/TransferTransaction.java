package com.code.banksystem.bank.models;

import java.sql.Timestamp;
import java.util.UUID;

public class TransferTransaction {

    private UUID senderAccountId;
    private UUID receiverAccountId;
    private double amount;
    private Timestamp transaction_timestamp;

    public TransferTransaction(UUID senderAccountId, UUID receiverAccountId, double amount, Timestamp transaction_timestamp) {
        this.senderAccountId = senderAccountId;
        this.receiverAccountId = receiverAccountId;
        this.amount = amount;
        this.transaction_timestamp = transaction_timestamp;
    }

    public UUID getSenderAccountId() {
        return senderAccountId;
    }

    public void setSenderAccountId(UUID senderAccountId) {
        this.senderAccountId = senderAccountId;
    }

    public UUID getReceiverAccountId() {
        return receiverAccountId;
    }

    public void setReceiverAccountId(UUID receiverAccountId) {
        this.receiverAccountId = receiverAccountId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Timestamp getTransaction_timestamp() {
        return transaction_timestamp;
    }

    public void setTransaction_timestamp(Timestamp transaction_timestamp) {
        this.transaction_timestamp = transaction_timestamp;
    }
}
