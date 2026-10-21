package com.shopsphere.controller;

import com.shopsphere.dao.UserDAO;
import com.shopsphere.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Locale;

@WebServlet("/account")
public class AuthServlet extends HttpServlet {
    private final UserDAO users = new UserDAO();
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/account.jsp").forward(request, response);
    }
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = value(request.getParameter("action"));
        String email = value(request.getParameter("email")).toLowerCase(Locale.ROOT);
        String password = value(request.getParameter("password"));
        try {
            if ("register".equals(action)) {
                String name = value(request.getParameter("name"));
                if (name.length() < 2 || name.length() > 100 || !email.matches("^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$") || password.length() < 8 || password.length() > 128) {
                    request.setAttribute("error", "Enter a name, valid email and password with at least 8 characters.");
                    doGet(request, response); return;
                }
                char[] passwordChars = password.toCharArray();
                try { users.register(name, email, passwordChars); }
                finally { java.util.Arrays.fill(passwordChars, '\0'); }
                request.setAttribute("success", "Account created. You can now sign in.");
                doGet(request, response); return;
            }
            char[] passwordChars = password.toCharArray();
            User user;
            try { user = users.authenticate(email, passwordChars).orElse(null); }
            finally { java.util.Arrays.fill(passwordChars, '\0'); }
            if (user == null) {
                request.setAttribute("error", "Email or password is incorrect.");
                doGet(request, response); return;
            }
            request.getSession();
            request.changeSessionId();
            request.getSession().setAttribute("user", user);
            response.sendRedirect(request.getContextPath() + (user.isAdmin() ? "/admin/products" : "/"));
        } catch (SQLException ex) {
            if ("register".equals(action) && ex.getErrorCode() == 1062) request.setAttribute("error", "An account with this email already exists.");
            else request.setAttribute("error", "Account service is unavailable. Check the database configuration.");
            doGet(request, response);
        }
    }
    private static String value(String value) { return value == null ? "" : value.trim(); }
}
