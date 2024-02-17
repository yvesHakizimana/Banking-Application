package com.code.banksystem.bank.config;

import java.sql.SQLException;

public class HandleExceptions {
    public static void handleSqlExceptions(SQLException ex){
        System.out.println("SQLState : " + ex.getSQLState());
        System.out.println("ErrorCode: " + ex.getErrorCode());
        System.out.println("Message: " + ex.getMessage());
    }
}
