package com.code.banksystem.bank.daos;

import com.code.banksystem.bank.models.Account;

public interface AccountDao {
    Account getAccountDetails(Integer customer_id);

    boolean updateAccountBalance(Account account);
}
