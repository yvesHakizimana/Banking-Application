package com.code.banksystem.bank.listeners;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.concurrent.atomic.AtomicInteger;

@WebListener
public class UserSessionListener implements HttpSessionListener {

    private static final AtomicInteger activeSessions = new AtomicInteger(0);

    public void sessionCreated(HttpSessionEvent se) {
        activeSessions.incrementAndGet();
    }

    public void sessionDestroyed(HttpSessionEvent se) {
        activeSessions.decrementAndGet();
    }

    public static AtomicInteger getActiveSessions() {
        return activeSessions;
    }
}
