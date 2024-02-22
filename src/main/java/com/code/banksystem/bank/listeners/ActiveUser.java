package com.code.banksystem.bank.listeners;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import com.code.banksystem.bank.listeners.UserSessionListener;

@WebServlet(name = "ActiveUser", value = "/active-user")
public class ActiveUser extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        AtomicInteger users = UserSessionListener.getActiveSessions();
        System.out.println("The number of active sessions: " + users);
    }
}
