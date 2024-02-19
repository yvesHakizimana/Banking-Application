package com.code.banksystem.bank.controllers.AdminControllers;

import com.code.banksystem.bank.daos.UserReportDao;
import com.code.banksystem.bank.impls.UserReportImpl;
import com.code.banksystem.bank.models.Transaction;
import com.code.banksystem.bank.models.TransactionSummary;
import com.code.banksystem.bank.models.UserReportDetails;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "UserReport", value = "/user-report" )
public class UserReport extends HttpServlet {

   private UserReportDao reportDao;
    public void init(){
        reportDao = new UserReportImpl();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("adminUsername");
        if(username == null){
            response.sendRedirect("loginAdmin.jsp");
        }
        else{
            List<UserReportDetails> reportDetails = reportDao.getUserReport();
            int accounts_created = reportDao.getAccountsCreated();
            int total_deposit =  reportDao.getTotalDeposits();
            int total_withdrawal = reportDao.getTotalWithdrawals();
            int balance = reportDao.getTotalBalance();
            List<Transaction> transactions = reportDao.getTransactions();
            int[] accountsPerType = reportDao.getAccountsPerType();

            List<TransactionSummary> transactionSummaryList = reportDao.getDetailedTransactions();

            request.setAttribute("transactionsReport", transactionSummaryList);
            request.setAttribute("userReport", reportDetails);
            request.setAttribute("accountsCreated", accounts_created);
            request.setAttribute("totalDeposits", total_deposit);
            request.setAttribute("totalWithdrawals", total_withdrawal);
            request.setAttribute("totalBalance", balance);
            request.setAttribute("savingAccounts", accountsPerType[0]);
            request.setAttribute("currentAccounts", accountsPerType[1]);
            request.setAttribute("groupedTransactions", transactions);
            request.getRequestDispatcher("reportSummary.jsp").forward(request, response);
        }

    }
}
