package com.code.banksystem.bank.models;

import java.math.BigDecimal;
import java.util.UUID;

public class UserReportDetails {


    private String firstName;
    private  String lastName;
    private String username;
    private int age;
    private int phoneNumber;
    private UUID accountId;
    private String accountType;
    private BigDecimal balance;
    public UserReportDetails(String firstName, String lastName, String username, int age, int phoneNumber, UUID accountId, String accountType, BigDecimal balance) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.age = age;
        this.phoneNumber = phoneNumber;
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
    }


    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public int getAge() {
        return age;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public UUID getAccountId() {
        return accountId;
    }

    public String getAccountType() {
        return accountType;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return "UserReportDetails{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", username='" + username + '\'' +
                ", age=" + age +
                ", phoneNumber=" + phoneNumber +
                ", accountId=" + accountId +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                '}';
    }
}
