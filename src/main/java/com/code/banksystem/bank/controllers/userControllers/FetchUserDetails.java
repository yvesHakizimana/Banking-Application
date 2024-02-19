package com.code.banksystem.bank.controllers.userControllers;

import com.code.banksystem.bank.daos.AccountDao;
import com.code.banksystem.bank.daos.TransactionDao;
import com.code.banksystem.bank.daos.UserDao;
import com.code.banksystem.bank.impls.AccountDaoImpl;
import com.code.banksystem.bank.impls.TransactionDaoImpl;
import com.code.banksystem.bank.impls.UserDaoImpl;
import com.code.banksystem.bank.models.Account;
import com.code.banksystem.bank.models.Transaction;
import com.code.banksystem.bank.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "FetchUserDetails", value = "/fetch-user")
public class FetchUserDetails extends HttpServlet {

    private UserDao userDao;
    private AccountDao accountDao;
    private TransactionDao transactionDao;
    public void init(){
        this.userDao = new UserDaoImpl();
        this.accountDao =  new AccountDaoImpl();
        this.transactionDao = new TransactionDaoImpl();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        System.out.println(request.getServletPath() + "  "  + request.getRequestURI());
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        if(username == null){
            response.sendRedirect("loginUser.jsp");
        }
        else{
            User user = userDao.getUser(username);
            System.out.println(user.getProfilePictureUrl());
            Account account = accountDao.getAccountDetails(user.getCustomer_id());
            List<Transaction> transactions = transactionDao.getTransactionsByAccountId(account.getAccountId());
            Cookie cookie = new Cookie("customerId" , String.valueOf(user.getCustomer_id()));
            response.addCookie(cookie);
            session.setAttribute("user", user);
            session.setAttribute("account", account);
            session.setAttribute("transactions", transactions);
            response.sendRedirect("mainMenu.jsp");
        }

    }
}
