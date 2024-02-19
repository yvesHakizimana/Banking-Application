package com.code.banksystem.bank.controllers.userControllers;

import com.code.banksystem.bank.daos.AccountDao;
import com.code.banksystem.bank.daos.TransactionDao;
import com.code.banksystem.bank.daos.UserDao;
import com.code.banksystem.bank.impls.AccountDaoImpl;
import com.code.banksystem.bank.impls.TransactionDaoImpl;
import com.code.banksystem.bank.impls.UserDaoImpl;
import com.code.banksystem.bank.models.Account;
import com.code.banksystem.bank.models.LoginBean;
import com.code.banksystem.bank.models.Transaction;
import com.code.banksystem.bank.models.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet(name = "LoginUser", urlPatterns = {"/login-user"})
public class LoginUser extends HttpServlet {

    private UserDao userDao;


    public void init() {
        this.userDao = new UserDaoImpl();
    }

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if(username == null && password == null){
            response.sendRedirect("loginUser.jsp");
        }
        else {
            LoginBean loginBean = new LoginBean();
            loginBean.setUsername(username);
            loginBean.setPassword(password);
            HttpSession session = request.getSession();

            boolean result = userDao.validate(loginBean);
            if (result) {
                session.setAttribute("username", loginBean.getUsername());
                System.out.println("The user has logged in successfully");
                response.sendRedirect("fetch-user");

            } else {
                String message = "Invalid username or password";
                request.setAttribute("message", message);
                request.getRequestDispatcher("loginUser.jsp").forward(request, response);
            }
        }


    }
}
