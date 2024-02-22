package com.code.banksystem.bank.controllers.userControllers;

import com.code.banksystem.bank.daos.AccountDao;
import com.code.banksystem.bank.impls.AccountDaoImpl;
import com.code.banksystem.bank.models.User;
import com.google.gson.Gson;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/ProfileFetchServlet")
public class ProfileFetchServlet extends HttpServlet {

    private AccountDao accountDao;
    public void init(){
        this.accountDao = new AccountDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Fetch user information based on the provided account ID
        String accountId = (request.getParameter("accountId"));
        User user = accountDao.getUserDetailsByAccountId(UUID.fromString(accountId));
        Gson gson = new Gson();
        String json = gson.toJson(user);
        PrintWriter out = response.getWriter();
        out.write(json);
    }


}
