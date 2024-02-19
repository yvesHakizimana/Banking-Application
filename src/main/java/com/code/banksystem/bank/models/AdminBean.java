package com.code.banksystem.bank.models;

import javax.persistence.*;
import java.io.Serializable;


public class AdminBean {

    private long id;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
