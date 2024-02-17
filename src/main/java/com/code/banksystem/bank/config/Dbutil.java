package com.code.banksystem.bank.config;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Dbutil {
    private static final String jdbcUrl = "jdbc:postgresql://localhost:5432/banksystem";
    private static final String jdbcUsername = "postgres";
    private static final String jdbcPassword = "P0S1tiv@!";

    public Connection connectToDB(){
        Connection conn = null;
        try{
            Class.forName("org.postgresql.Driver");
            conn = DriverManager.getConnection(jdbcUrl, jdbcUsername, jdbcPassword);
            if(conn != null)
                System.out.println("We are connected to the database successfully");
        } catch(SQLException ex){
            throw new RuntimeException("SQL Exception");
        } catch (ClassNotFoundException ex){
            throw  new RuntimeException("Class not Found Exception");
        }
        return conn;
    }
}
