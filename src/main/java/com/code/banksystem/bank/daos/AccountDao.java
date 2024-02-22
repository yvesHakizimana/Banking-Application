package com.code.banksystem.bank.daos;

import com.code.banksystem.bank.models.Account;
import com.code.banksystem.bank.models.User;

import java.util.UUID;

public interface AccountDao {
    Account getAccountDetails(Integer customer_id);
    boolean updateAccountBalance(Account account);
    User getUserDetailsByAccountId(UUID accountId);
    Account getAccountByUsername(String username);
    Account getAccountByAccountId(UUID accountId);
}
