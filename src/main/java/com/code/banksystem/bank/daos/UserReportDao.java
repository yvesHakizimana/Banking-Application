package com.code.banksystem.bank.daos;

import com.code.banksystem.bank.models.Transaction;
import com.code.banksystem.bank.models.TransactionSummary;
import com.code.banksystem.bank.models.UserReportDetails;

import java.util.List;

public interface UserReportDao {
    List<UserReportDetails> getUserReport();

    int getAccountsCreated();
    int getTotalDeposits();
    int getTotalWithdrawals();
    int getTotalBalance();
    int getSavingAccounts();
    int getCurrentAccounts();
    int[] getAccountsPerType();

    List<Transaction> getTransactions();
    List<TransactionSummary> getDetailedTransactions();

}
