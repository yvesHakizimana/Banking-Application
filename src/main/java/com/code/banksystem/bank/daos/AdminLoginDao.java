package com.code.banksystem.bank.daos;

import com.code.banksystem.bank.config.Dbutil;
import com.code.banksystem.bank.models.AdminBean;

public interface AdminLoginDao {
    boolean validate(AdminBean adminBean);



}
