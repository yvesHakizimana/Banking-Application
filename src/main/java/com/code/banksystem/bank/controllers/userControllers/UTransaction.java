package com.code.banksystem.bank.controllers.userControllers;

import com.code.banksystem.bank.daos.TransactionDao;
import com.code.banksystem.bank.daos.UserDao;
import com.code.banksystem.bank.impls.TransactionDaoImpl;
import com.code.banksystem.bank.impls.UserDaoImpl;
import com.code.banksystem.bank.models.Transaction;
import com.code.banksystem.bank.models.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "UserTransaction", value = "/user-transaction")
public class UTransaction extends HttpServlet {

    private  TransactionDao transactionDao;
    private UserDao userDao;
    public void init(){
        this.transactionDao = new TransactionDaoImpl();
        this.userDao = new UserDaoImpl();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");
        System.out.println(username);
        if(username == null)
            response.sendRedirect("loginUser.jsp");
        else {
            User user =userDao.getUser(username);
            List<Transaction> userTransactions = transactionDao.getUserTransactions(user.getCustomer_id());
            request.setAttribute("userTransactions", userTransactions);
            request.getRequestDispatcher("userTransaction.jsp").forward(request, response) ;
        }
    }
}
