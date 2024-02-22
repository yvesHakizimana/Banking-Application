package com.code.banksystem.bank.controllers.accountController;

import com.code.banksystem.bank.daos.AccountDao;
import com.code.banksystem.bank.daos.TransactionDao;
import com.code.banksystem.bank.impls.AccountDaoImpl;
import com.code.banksystem.bank.impls.TransactionDaoImpl;
import com.code.banksystem.bank.models.Account;
import com.code.banksystem.bank.models.TransferTransaction;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.UUID;

@WebServlet(name = "TransferFunds", value = "/transfer-process")
public class TransferFunds extends HttpServlet {

    private AccountDao accountDao;
    private TransactionDao transactionDao;

    public void init() {
        this.accountDao = new AccountDaoImpl();
        this.transactionDao = new TransactionDaoImpl();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        HttpSession session = request.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            String message = "Not allowed until you login";
            request.getRequestDispatcher("loginUser.jsp").forward(request, response);
            return; // stop processing further if user is not logged in
        }

        UUID recipientAccountId = UUID.fromString(request.getParameter("recipientAccountId").trim());
        System.out.println(recipientAccountId);
        Account senderAccount = accountDao.getAccountByUsername(username);
        Account receiverAccount = accountDao.getAccountByAccountId(recipientAccountId);

        double amount = Double.parseDouble(request.getParameter("amount"));

        if (senderAccount == null || receiverAccount == null) {
            response.getWriter().println("Invalid sender or receiver account");
            return;
        }

        if (amount > senderAccount.getBalance()) {
            request.setAttribute("transferError", "You don't have enough money to send");
            request.getRequestDispatcher("transferFunds.jsp").forward(request, response);
            return;
        }

        double senderBalanceAfterTransfer = senderAccount.getBalance() - amount;
        double receiverBalanceAfterTransfer = receiverAccount.getBalance() + amount;

        senderAccount.setBalance(senderBalanceAfterTransfer);
        receiverAccount.setBalance(receiverBalanceAfterTransfer);

        boolean isSent = accountDao.updateAccountBalance(senderAccount);
        boolean isReceived = accountDao.updateAccountBalance(receiverAccount);

        if (isSent && isReceived) {
            response.getWriter().println("The amount transferred successfully");
            boolean result = transactionDao.insertTransactionIntoDatabase(new TransferTransaction(senderAccount.getAccountId(), receiverAccount.getAccountId(), amount, new Timestamp(System.currentTimeMillis())));
            if (result)
                System.out.println("Transaction inserted successfully");
            else
                System.out.println("Transaction not inserted due to some technical errors");
        } else {
            response.getWriter().println("The transfer didn't be processed as intended");
        }
    }
}
