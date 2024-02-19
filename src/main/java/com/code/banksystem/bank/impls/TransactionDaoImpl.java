package com.code.banksystem.bank.impls;

import com.code.banksystem.bank.config.Dbutil;
import com.code.banksystem.bank.config.HandleExceptions;
import com.code.banksystem.bank.daos.TransactionDao;
import com.code.banksystem.bank.models.Transaction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TransactionDaoImpl implements TransactionDao {

    private final Dbutil dbutil;

    private static final String GET_TRANSACTIONS_BY_ACCOUNT = "select transaction_id, transaction_type, amount, transaction_date from transaction where account_id = ?";
    private static final String GET_TRANSACTIONS_BY_USER  = "select transaction_type, amount, transaction_date, account_type from transaction where customer_id = ?";

    public TransactionDaoImpl(){
        this.dbutil = new Dbutil();
    }


    @Override
    public List<Transaction> getTransactionsByAccountId(UUID transactionId) {
        List<Transaction> transactions = new ArrayList<>();
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(GET_TRANSACTIONS_BY_ACCOUNT)){
            statement.setObject(1, transactionId);
            ResultSet rs =  statement.executeQuery();
            while(rs.next()){
                int transaction_Id = rs.getInt(1);
                String transaction_type = rs.getString(2);
                Double transaction_amount = rs.getDouble(3);
                Timestamp transaction_date = rs.getTimestamp(4);
                transactions.add(new Transaction(transaction_Id, transaction_type, transaction_amount, transaction_date));
            }
        } catch (SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
        }
            return transactions;
    }

    @Override
    public List<Transaction> getUserTransactions(int customer_id) {
        List<Transaction> userTransactions = new ArrayList<>();
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(GET_TRANSACTIONS_BY_USER)){
          statement.setInt(1, customer_id);
            ResultSet rs =  statement.executeQuery();
            while(rs.next()){
                String transaction_type = rs.getString(1);
                Double transaction_amount = rs.getDouble(2);
                Timestamp transaction_date = rs.getTimestamp(3);
                String account_type = rs.getString(4);
                userTransactions.add(new Transaction(transaction_type, transaction_amount, transaction_date, account_type));
            }
        } catch (SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
        }
        return userTransactions;
    }

}
