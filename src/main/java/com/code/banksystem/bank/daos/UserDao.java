package com.code.banksystem.bank.daos;
import java.nio.file.Path;

import com.code.banksystem.bank.models.LoginBean;
import com.code.banksystem.bank.models.User;

import java.util.List;

public interface UserDao {
    boolean insertUser(User user);
    List<User> getAllUsers();
    User getUser(String username);
    boolean updateUser(User user);
    boolean validate(LoginBean loginBean);
    boolean checkUserExists(String username);

}
