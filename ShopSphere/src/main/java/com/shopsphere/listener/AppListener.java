package com.shopsphere.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {
    @Override public void contextInitialized(ServletContextEvent event) {
        event.getServletContext().setAttribute("appName", "ShopSphere");
        event.getServletContext().log("ShopSphere web application started");
    }
    @Override public void contextDestroyed(ServletContextEvent event) {
        event.getServletContext().log("ShopSphere web application stopped");
    }
}
