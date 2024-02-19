package com.code.banksystem.bank.impls;

import com.code.banksystem.bank.config.Dbutil;
import com.code.banksystem.bank.config.HandleExceptions;
import com.code.banksystem.bank.daos.UserReportDao;
import com.code.banksystem.bank.models.Transaction;
import com.code.banksystem.bank.models.TransactionSummary;
import com.code.banksystem.bank.models.UserReportDetails;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserReportImpl implements UserReportDao {

    private Dbutil dbutil;

    private static final String GET_USER_REPORT = "{call get_customer_account_info()}";
    private static final String GET_ACCOUNTS_CREATED = "select get_accounts_created as accounts_created from get_accounts_created();";
    private static final String GET_TOTAL_DEPOSITS = "select get_total_deposits as total_deposits from get_total_deposits()";
    private static final String GET_TOTAL_WITHDRAWALS = "select  get_total_withdrawals as total_withdrawals from get_total_withdrawals()";
    private static final String GROUP_TRANSACTIONS = "select transaction_type, total_amount from group_transactions()";
    private static final String GET_TOTAL_BALANCE = "select get_total_balance as total_balance from get_total_balance()";
    private static final String GET_ACCOUNTS_PER_TYPE = "select savings_count, current_count from count_accounts()";
    private static final String GET_DETAILED_TRANSACTIONS = "select username, phonenumber, account_id, transaction_type, amount, transaction_date, account_type from user_transactions()";

    public UserReportImpl() {
        this.dbutil = new Dbutil();
    }

    @Override
    public List<UserReportDetails> getUserReport() {
        List<UserReportDetails> reportDetails = new ArrayList<>();
        try (CallableStatement cstatement = dbutil.connectToDB().prepareCall(GET_USER_REPORT)) {
            // Execute the stored procedure
            boolean hasResults = cstatement.execute();

            // Process each result set
            if (hasResults) {
                try (ResultSet rs = cstatement.getResultSet()) {
                    // Process each row in the result set
                    while (rs.next()) {
                        // Retrieve data from the result set
                        String firstName = rs.getString("first_name");
                        String lastName = rs.getString("last_name");
                        String username = rs.getString("username");
                        int age = rs.getInt("age");
                        int phoneNumber = rs.getInt("phoneNumber");
                        UUID accountId = (UUID) rs.getObject("account_id");
                        String accountType = rs.getString("account_type");
                        BigDecimal balance = rs.getBigDecimal("balance");

                        // Create UserReportDetails object and add it to the list
                        reportDetails.add(new UserReportDetails(firstName, lastName, username, age, phoneNumber, accountId, accountType, balance));
                    }
                }
            }
        } catch (SQLException ex) {
            HandleExceptions.handleSqlExceptions(ex);
        }
        return reportDetails;
    }

    @Override
    public int getAccountsCreated() {
        return getReportInfo(GET_ACCOUNTS_CREATED);
    }

    @Override
    public int getTotalDeposits() {
        return getReportInfo(GET_TOTAL_DEPOSITS);
    }

    @Override
    public int getTotalWithdrawals() {
        return getReportInfo(GET_TOTAL_WITHDRAWALS);
    }

    @Override
    public int getTotalBalance() {
        return getReportInfo(GET_TOTAL_BALANCE);
    }

    @Override
    public int getSavingAccounts() {
        return 0;
    }

    @Override
    public int getCurrentAccounts() {
        return 0;
    }

    @Override
    public int[] getAccountsPerType() {
        int[] accountsPerType = new int[2];
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(GET_ACCOUNTS_PER_TYPE)){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                accountsPerType[0] = rs.getInt(1);
                accountsPerType[1]  = rs.getInt(2);
            }
        } catch (SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
        }
        return  accountsPerType;
    }

    @Override
    public List<Transaction> getTransactions() {
        List<Transaction> transactionList = new ArrayList<>();
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(GROUP_TRANSACTIONS)){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                String transaction_type = rs.getString(1);
                double amount = rs.getDouble(2);
                transactionList.add(new Transaction(transaction_type, amount));
            }
        } catch (SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
        }
        return transactionList;
    }

    @Override
    public List<TransactionSummary> getDetailedTransactions() {
        List<TransactionSummary> transactionSummaries = new ArrayList<>();
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(GET_DETAILED_TRANSACTIONS)){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                String username = rs.getString("username");
                int phoneNumber = rs.getInt("phonenumber");
                UUID account_id = (UUID)rs.getObject("account_id");
                String transaction_type = rs.getString("transaction_type");
                double amount = rs.getDouble("amount");
                Timestamp transaction_date = rs.getTimestamp("transaction_date");
                String account_type = rs.getString("account_type");
                transactionSummaries.add(new TransactionSummary(username, phoneNumber, account_id, transaction_type, amount, transaction_date, account_type));
            }
        } catch (SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
        }
        return transactionSummaries;
    }


    private int getReportInfo(String sql){
        int total = 0;
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(sql)){
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                total = rs.getInt(1);
            }
        } catch (SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
        }
        return total;
    }


}
