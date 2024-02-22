package com.code.banksystem.bank.controllers.accountController;

import com.code.banksystem.bank.daos.AccountDao;
import com.code.banksystem.bank.impls.AccountDaoImpl;
import com.code.banksystem.bank.models.Account;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

// ...
import java.sql.Timestamp;
import java.util.Date;

@WebServlet(name = "WithdrawFund", value = "/withdraw-fund")
public class WithdrawFund extends HttpServlet {

    AccountDao accountDao;

    public void init() {
        accountDao = new AccountDaoImpl();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        int customerId = 0;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("customerId"))
                    customerId = Integer.parseInt(c.getValue());
            }
        }
        Account account = accountDao.getAccountDetails(customerId);
        System.out.println("Customer ID: " + customerId);

        double amount = Double.parseDouble(request.getParameter("withdraw"));
        if(amount < 0) {
            String message = "The amount can not be less than 0";
            request.setAttribute("withdrawError", message);
            request.getRequestDispatcher("withdrawFund.jsp").forward(request, response);
            return; 
        }

        // Check if account type is "saving" and last withdrawal was more than a month ago
        boolean canWithdraw = userCanWithdraw(account);

        if (!canWithdraw) {
            String message = "You can only withdraw once a month for saving account";
            request.setAttribute("message", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher("withdrawFund.jsp");
            dispatcher.forward(request, response);
        }
        else if (amount > account.getBalance()) {
            String message = "You don't have enough money to withdraw. Please deposit.";
            request.setAttribute("message", message);
            RequestDispatcher dispatcher = request.getRequestDispatcher("withdrawFund.jsp");
            dispatcher.forward(request, response);
        }
        else {
            double newAmount = account.getBalance() - amount;
            account.setBalance(newAmount);
            account.setLastWithdrawalDate(new Timestamp(System.currentTimeMillis())); // Update last withdrawal date
            boolean result = accountDao.updateAccountBalance(account);
            if (result)
                response.sendRedirect("fetch-user");
            else
                response.getWriter().println("There was an error processing the request.");
        }
    }

    private static boolean userCanWithdraw(Account account) {
        boolean isSavingAccount = account.getAccountType().equals("saving");
        boolean canWithdraw = true;
        if (isSavingAccount) {
            Date lastWithdrawalDate = account.getLastWithdrawalDate();
            Date currentDate = new Date();
            // Assuming lastWithdrawalDate is properly set by the AccountDao
            if (lastWithdrawalDate != null) {
                long timeDifference = currentDate.getTime() - lastWithdrawalDate.getTime();
                long oneMonthInMillis = 30L * 24 * 60 * 60 * 1000; // Assuming a month is 30 days
                if (timeDifference < oneMonthInMillis) {
                    canWithdraw = false; // Cannot withdraw more than once a month
                }
            }
        }
        return canWithdraw;
    }
}
