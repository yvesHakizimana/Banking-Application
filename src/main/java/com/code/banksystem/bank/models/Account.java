package com.code.banksystem.bank.models;

import javax.persistence.*;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.UUID;
import java.util.Date;


public class Account {

    private UUID accountId;
    private Integer customer_id;
    private String accountType;
    private double balance;
    private Timestamp lastWithdrawalDate;

    public Account() {}

    public Account(UUID accountId, Integer customer_id, String accountType, double balance, Timestamp lastWithdrawalDate) {
        this.accountId = accountId;
        this.customer_id = customer_id;
        this.accountType = accountType;
        this.balance = balance;
        this.lastWithdrawalDate = lastWithdrawalDate;
    }

    public Account(UUID accountId, Integer customer_id, String accountType, double balance) {
        this.accountId = accountId;
        this.customer_id = customer_id;
        this.accountType = accountType;
        this.balance = balance;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public void setAccountId(UUID accountId) {
        this.accountId = accountId;
    }

    public Integer getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(Integer customer_id) {
        this.customer_id = customer_id;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Timestamp getLastWithdrawalDate() {
        return lastWithdrawalDate;
    }

    public void setLastWithdrawalDate(Timestamp lastWithdrawalDate) {
        this.lastWithdrawalDate = lastWithdrawalDate;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", customer_id=" + customer_id +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", lastWithdrawalDate=" + lastWithdrawalDate +
                '}';
    }
}
