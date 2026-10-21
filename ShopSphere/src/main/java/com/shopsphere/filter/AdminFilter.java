package com.shopsphere.filter;

import com.shopsphere.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebFilter(urlPatterns = "/admin/*")
public class AdminFilter implements Filter {
    @Override public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest http = (HttpServletRequest) request;
        User user = (User) http.getSession().getAttribute("user");
        if (user == null) {
            ((HttpServletResponse) response).sendRedirect(http.getContextPath() + "/account");
            return;
        }
        if (!user.isAdmin()) {
            ((HttpServletResponse) response).sendError(HttpServletResponse.SC_FORBIDDEN, "Admin access is required.");
            return;
        }
        chain.doFilter(request, response);
    }
}
