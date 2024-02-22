package com.code.banksystem.bank.impls;

import com.code.banksystem.bank.config.Dbutil;
import com.code.banksystem.bank.config.HandleExceptions;
import com.code.banksystem.bank.daos.AccountDao;
import com.code.banksystem.bank.models.Account;
import com.code.banksystem.bank.models.User;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.UUID;

public class AccountDaoImpl implements AccountDao {

    private final Dbutil dbutil;

    private static final String GET_ACCOUNT_BY_C_ID_SQL = "select account_id, account_type, balance, customer_id, last_withdrawal_date from account where customer_id = ?";
    private static final String UPDATE_AMOUNT_BY_C_ID = "update account set balance = ? , last_withdrawal_date = ? where customer_id = ?";
    private static final String GET_USER_INFO_BY_ACCOUNT_ID = "select first_name, last_name, phonenumber, file_path from get_customer_info(?)";
    private static final String GET_ACCOUNT_BY_USERNAME = "select account_id, customer_id, account_type, balance from get_account_by_username(?)";
    private static final String GET_ACCOUNT_BY_ACCOUNT_ID = "select account_id, customer_id, account_type, balance from get_account_by_id(?)";

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

    @Override
    public User getUserDetailsByAccountId(UUID accountId) {
        User user = null;
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(GET_USER_INFO_BY_ACCOUNT_ID)){
            statement.setObject(1, accountId);
            ResultSet rs  = statement.executeQuery();
            while(rs.next()){
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                int phoneNumber  = rs.getInt("phonenumber");
                String filePath = rs.getString("file_path");
                user = new User(firstName, lastName, phoneNumber, filePath);
            }

        } catch (SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
        }
        return user;
    }

    @Override
    public Account getAccountByUsername(String username) {
        Account account = new Account();
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(GET_ACCOUNT_BY_USERNAME)){
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                UUID account_id = (UUID) rs.getObject("account_id");
                int customer_id = rs.getInt("customer_id");
                String account_type = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                account = new Account(account_id, customer_id, account_type, balance);
            }
        } catch (SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
        }
        return account;
    }

    @Override
    public Account getAccountByAccountId(UUID accountId) {
        Account account  = null;
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(GET_ACCOUNT_BY_ACCOUNT_ID)){
            statement.setObject(1, accountId);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                UUID account_id = (UUID) rs.getObject("account_id");
                int customer_id = rs.getInt("customer_id");
                String account_type = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                account = new Account(account_id, customer_id, account_type, balance);
            }
        } catch (SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
        }
        return account;
    }

}
