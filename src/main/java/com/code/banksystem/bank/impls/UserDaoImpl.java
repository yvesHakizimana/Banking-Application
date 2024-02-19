package com.code.banksystem.bank.impls;

import com.code.banksystem.bank.config.Dbutil;
import com.code.banksystem.bank.config.HandleExceptions;
import com.code.banksystem.bank.daos.UserDao;
import com.code.banksystem.bank.models.LoginBean;
import com.code.banksystem.bank.models.User;
import com.code.banksystem.bank.utils.PasswordHarsher;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {

    private final Dbutil dbutil;

    private static final String INSERT_USER_SQL = "call register_user_with_image(?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_USER_SQL = "SELECT customer_id, age, username, first_name, last_name, password, phonenumber, accounttype FROM customer where username = ?";
    private static final String SELECT_USER_PROFILE_PIC_SQL = "SELECT file_path FROM customer_images WHERE customer_id = ?";
    private static final String SELECT_USER_SQL_UP = "SELECT customer_id, age, username, first_name, last_name, password, phonenumber, accounttype FROM customer where username = ? and password = ?";
    private static  final String UPDATE_USER_INFO = "call update_customer_info(?, ?, ?, ?, ?, ?, ?)";

    public UserDaoImpl(){
        this.dbutil = new Dbutil();
    }

    @Override
    public boolean insertUser(User user) {
        boolean isSuccess = true;
        try(CallableStatement callableStatement = dbutil.connectToDB().prepareCall(INSERT_USER_SQL)){
            callableStatement.setInt(1, user.getAge());
            callableStatement.setString(2, user.getEmail());
            callableStatement.setString(3, user.getFirst_name());
            callableStatement.setString(4, user.getLast_name());
            callableStatement.setString(5, user.getPassword());
            callableStatement.setInt(6, user.getPhoneNumber());
            callableStatement.setString(7, user.getAccountType());
            callableStatement.setString(8, user.getProfilePictureUrl());
            callableStatement.executeUpdate();
        } catch(SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User getUser(String username) {
        User user = null;
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(SELECT_USER_SQL)){
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                Integer customerId = rs.getInt("customer_id");
                Integer age = rs.getInt("age");
                String email = rs.getString("username");
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String password = rs.getString("password");
                Integer phoneNumber = rs.getInt("phonenumber");
                String accountType = rs.getString("accounttype");
                user = new User(customerId, age, email, firstName, lastName, password, phoneNumber, accountType);
            }
            if (user != null) {
                PreparedStatement statement2 = dbutil.connectToDB().prepareStatement(SELECT_USER_PROFILE_PIC_SQL);
                statement2.setInt(1, user.getCustomer_id());
                ResultSet rs1 = statement2.executeQuery();
                if (rs1.next()) {
                    String picPath = rs1.getString("file_path");
                    user.setProfilePictureUrl(picPath);
                }
            }
        } catch (SQLException e) {
            HandleExceptions.handleSqlExceptions(e);
        }
        return user;
    }

    @Override
    public boolean updateUser(User user) {
        boolean isSuccess = true;
        try(CallableStatement callableStatement = dbutil.connectToDB().prepareCall(UPDATE_USER_INFO)){
            callableStatement.setInt(1, user.getCustomer_id());
            callableStatement.setString(2, user.getFirst_name());
            callableStatement.setString(3, user.getLast_name());
            callableStatement.setInt(4, user.getAge());
            callableStatement.setInt(5, user.getPhoneNumber());
            callableStatement.setString(6, user.getAccountType());
            callableStatement.setString(7, user.getProfilePictureUrl());
            callableStatement.executeUpdate();
        }
        catch(SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
            isSuccess = false;
        }
        return isSuccess;
    }

    @Override
    public boolean validate(LoginBean loginBean) {
        boolean isValid = false;
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(SELECT_USER_SQL_UP)){
            statement.setString(1, loginBean.getUsername());
            statement.setString(2, PasswordHarsher.hashPassword(loginBean.getPassword()));
            ResultSet rs = statement.executeQuery();
            isValid = rs.next();
        } catch (SQLException ex){
           HandleExceptions.handleSqlExceptions(ex);
        }
        return isValid;
    }

    @Override
    public boolean checkUserExists(String username) {
        boolean result = false;
        try(PreparedStatement statement = dbutil.connectToDB().prepareStatement(SELECT_USER_SQL)){
            statement.setString(1, username);
            ResultSet rs = statement.executeQuery();
            result = rs.next();
        } catch(SQLException ex){
            HandleExceptions.handleSqlExceptions(ex);
        }
        return result;
    }
}
