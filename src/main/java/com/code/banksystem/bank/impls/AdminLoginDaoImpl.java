package com.code.banksystem.bank.impls;

import com.code.banksystem.bank.config.Dbutil;
import com.code.banksystem.bank.config.HandleExceptions;
import com.code.banksystem.bank.daos.AdminLoginDao;
import com.code.banksystem.bank.models.AdminBean;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminLoginDaoImpl implements AdminLoginDao {

    private final Dbutil dbutil;
    private static final String GET_ADMIN_UsPw = "SELECT username , password FROM admin_table WHERE username = ? and password = ?";

    public AdminLoginDaoImpl(){
        this.dbutil = new Dbutil();
    }
    @Override
    public boolean validate(AdminBean adminBean) {
        boolean result = false;
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(GET_ADMIN_UsPw);){
            statement.setString(1, adminBean.getUsername());
            statement.setString(2, adminBean.getPassword());
            ResultSet rs = statement.executeQuery();
            result = rs.next();
        } catch (SQLException e) {
            HandleExceptions.handleSqlExceptions(e);
        }
        return result;
    }
}
