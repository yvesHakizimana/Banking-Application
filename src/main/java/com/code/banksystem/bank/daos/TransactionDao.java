package com.code.banksystem.bank.daos;

import com.code.banksystem.bank.models.Transaction;
import com.code.banksystem.bank.models.TransferTransaction;

import java.util.List;
import java.util.UUID;

public interface TransactionDao {
    List<Transaction> getTransactionsByAccountId(UUID transactionId);
    List<Transaction> getUserTransactions(int customer_id);
    boolean insertTransactionIntoDatabase(TransferTransaction transaction);

}
