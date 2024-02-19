package com.code.banksystem.bank.Filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(urlPatterns = {"/register-user", "/update-profile"})
public class AgeFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String ageParam = req.getParameter("age");
        if(ageParam != null && ageParam.matches("\\d+")){
            int age = Integer.parseInt(ageParam);
            // Check if the age is above 18
            if(age < 18){
                req.setAttribute("ageMessage", "The age must be greater than 18");
                req.getRequestDispatcher("registerUser.jsp").forward(servletRequest, servletResponse);
                return; // Return to prevent further processing
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {}
}
