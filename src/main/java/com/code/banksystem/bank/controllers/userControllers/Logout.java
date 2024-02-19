package com.code.banksystem.bank.controllers.userControllers;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "Logout", value = "/logout-user")
public class Logout extends HttpServlet {
    public void init(){}

    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException{
        Cookie cookie[] = request.getCookies();
        for( Cookie c: cookie){
            if(c.getName().equals("customerId"))
                c.setMaxAge(0);
        }
        HttpSession session = request.getSession();
        session.removeAttribute("username");
        session.removeAttribute("user");
        session.invalidate();

        response.sendRedirect("loginUser.jsp");
    }
}
