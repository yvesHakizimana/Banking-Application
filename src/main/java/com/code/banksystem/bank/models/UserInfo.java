package com.code.banksystem.bank.models;

public class UserInfo {

    private String username;
    private long loginTime;

    public UserInfo(String username, long loginTime) {
        this.username = username;
        this.loginTime = loginTime;
    }

    public String getUsername() {
        return username;
    }

    public long getLoginTime() {
        return loginTime;
    }

}
