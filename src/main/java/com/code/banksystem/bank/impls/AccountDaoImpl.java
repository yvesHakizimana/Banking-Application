package com.code.banksystem.bank.impls;

import com.code.banksystem.bank.config.Dbutil;
import com.code.banksystem.bank.config.HandleExceptions;
import com.code.banksystem.bank.daos.AccountDao;
import com.code.banksystem.bank.models.Account;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class AccountDaoImpl implements AccountDao {

    private final Dbutil dbutil;

    private static final String GET_ACCOUNT_BY_C_ID_SQL = "select account_id, account_type, balance, customer_id, last_withdrawal_date from account where customer_id = ?";
    private static final String UPDATE_AMOUNT_BY_C_ID = "update account set balance = ? , last_withdrawal_date = ? where customer_id = ?";
    public AccountDaoImpl(){
        this.dbutil = new Dbutil();
    }
    @Override
    public Account getAccountDetails(Integer customer_id) {
        Account accountDetails = new Account();
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(GET_ACCOUNT_BY_C_ID_SQL)){
            statement.setInt(1, customer_id);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                UUID account_id = (UUID) rs.getObject(1);
                String accountType = rs.getString(2);
                double balance = rs.getDouble(3);
                int customerId = rs.getInt(4);
                Timestamp withdrawalDate = rs.getTimestamp(5);
                accountDetails.setAccountId(account_id);
                accountDetails.setBalance(balance);
                accountDetails.setAccountType(accountType);
                accountDetails.setCustomer_id(customerId);
                accountDetails.setLastWithdrawalDate(withdrawalDate);
            }
        }catch (SQLException ex){
           HandleExceptions.handleSqlExceptions(ex);
        }
        return accountDetails;
    }

    @Override
    public boolean updateAccountBalance(Account account) {
        boolean rowsUpdated = false;
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(UPDATE_AMOUNT_BY_C_ID)){
            statement.setDouble(1, account.getBalance());
            if(account.getLastWithdrawalDate() != null)
                statement.setTimestamp(2, account.getLastWithdrawalDate());
            else
                statement.setDate(2, null);
            statement.setInt(3, account.getCustomer_id());
            rowsUpdated = statement.executeUpdate() > 0;
        } catch (SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
        }
        return rowsUpdated;
    }

}
