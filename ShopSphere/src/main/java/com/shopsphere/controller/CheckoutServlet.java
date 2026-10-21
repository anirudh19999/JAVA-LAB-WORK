package com.shopsphere.controller;

import com.shopsphere.model.CartLine;
import com.shopsphere.model.User;
import com.shopsphere.service.StoreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.Map;

@WebServlet("/checkout")
public class CheckoutServlet extends HttpServlet {
    private final StoreService store = new StoreService();
    @SuppressWarnings("unchecked")
    private Map<Long, CartLine> cart(HttpSession session) { return (Map<Long, CartLine>) session.getAttribute("cart"); }
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Map<Long, CartLine> cart = cart(request.getSession());
            if (cart == null || cart.isEmpty()) { response.sendRedirect(request.getContextPath() + "/cart"); return; }
            request.setAttribute("items", store.cart(cart));
            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
        } catch (Exception ex) { throw new ServletException("Unable to load checkout", ex); }
    }
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        Map<Long, CartLine> cart = cart(session);
        if (user == null || cart == null) { response.sendRedirect(request.getContextPath() + "/account"); return; }
        try {
            long orderId = store.placeOrder(user.id(), cart);
            cart.clear();
            response.sendRedirect(request.getContextPath() + "/orders?placed=" + orderId);
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", ex.getMessage());
            request.setAttribute("items", store.cart(cart));
            request.getRequestDispatcher("/WEB-INF/views/checkout.jsp").forward(request, response);
        } catch (Exception ex) { throw new ServletException("Unable to place order", ex); }
    }
}
