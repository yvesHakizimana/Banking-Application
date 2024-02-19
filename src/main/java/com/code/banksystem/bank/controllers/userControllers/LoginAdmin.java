package com.code.banksystem.bank.controllers.userControllers;

import com.code.banksystem.bank.daos.AdminLoginDao;
import com.code.banksystem.bank.impls.AdminLoginDaoImpl;
import com.code.banksystem.bank.models.AdminBean;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "LoginAdmin", value = "/login-admin")
public class LoginAdmin extends HttpServlet {

    private AdminLoginDao adminLoginDao;
    public void init() {
        this.adminLoginDao  = new AdminLoginDaoImpl();
    };

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        doPost(request, response);
    }
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        String username = request.getParameter("adminUsername");
        String password = request.getParameter("adminPassword");
        if(username == null && password == null){
            response.sendRedirect("loginAdmin.jsp");
        }
        else {
            AdminBean adminBean = new AdminBean();
            adminBean.setUsername(username);
            adminBean.setPassword(password);

            boolean result = adminLoginDao.validate(adminBean);
            if(result) {
                HttpSession session = request.getSession();
                session.setAttribute("adminUsername", username);
                response.sendRedirect("user-report");
            }
            else {
                String message = "Invalid username or password";
                request.setAttribute("message", message);
                RequestDispatcher dispatcher = request.getRequestDispatcher("loginAdmin.jsp");
                dispatcher.forward(request, response);
            }
        }

    }
}
