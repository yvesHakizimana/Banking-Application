package com.code.banksystem.bank.controllers.accountController;

import com.code.banksystem.bank.daos.AccountDao;
import com.code.banksystem.bank.impls.AccountDaoImpl;
import com.code.banksystem.bank.models.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "DepositFund", value = "/deposit-fund")
public class DepositFund extends HttpServlet {

    AccountDao accountDao;
    public void init(){
        this.accountDao = new AccountDaoImpl();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session  = request.getSession();
        String username = (String) session.getAttribute("username");
        Cookie[] cookies = request.getCookies();
        int customerId = 0;
        for(Cookie c: cookies)
        {
            if(c.getName().equals("customerId"))
                customerId = Integer.parseInt(c.getValue());
        }
        Account account = accountDao.getAccountDetails(customerId);
        double amount = Double.parseDouble(request.getParameter("deposit"));
        if(amount < 0)
        {
            String message = "The amount  must be greater than 0";
            request.setAttribute("message", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/depositFund.jsp");
            dispatcher.forward(request, response);

        }
        else{
            double newAmount = amount + account.getBalance();
            account.setBalance(newAmount);
            boolean result = accountDao.updateAccountBalance(account);
            if(result)
                response.sendRedirect("fetch-user");
            else
                response.getWriter().println("Error in the response");

        }

    }
}
