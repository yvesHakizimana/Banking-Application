package com.code.banksystem.bank.daos;

import com.code.banksystem.bank.models.Transaction;

import java.util.List;
import java.util.UUID;

public interface TransactionDao {
    List<Transaction> getTransactionsByAccountId(UUID transactionId);
    List<Transaction> getUserTransactions(int customer_id);

}
