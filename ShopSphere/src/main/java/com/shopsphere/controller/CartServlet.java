package com.shopsphere.controller;

import com.shopsphere.model.CartLine;
import com.shopsphere.service.StoreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private final StoreService store = new StoreService();
    @SuppressWarnings("unchecked")
    private Map<Long, CartLine> lines(HttpSession session) {
        synchronized (session) {
            Map<Long, CartLine> cart = (Map<Long, CartLine>) session.getAttribute("cart");
            if (cart == null) { cart = new HashMap<>(); session.setAttribute("cart", cart); }
            return cart;
        }
    }
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Map<Long, CartLine> cart = lines(request.getSession());
            request.setAttribute("cart", store.cart(cart));
            request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
        } catch (Exception ex) { throw new ServletException("Unable to load cart", ex); }
    }
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Map<Long, CartLine> cart = lines(request.getSession());
        synchronized (cart) {
            try {
                long id = Long.parseLong(request.getParameter("productId"));
                String action = request.getParameter("action");
                if ("remove".equals(action)) cart.remove(id);
                else {
                    int quantity = Integer.parseInt(request.getParameter("quantity"));
                    if (quantity < 1 || quantity > 99) throw new IllegalArgumentException();
                    if ("add".equals(action) && cart.containsKey(id)) quantity += cart.get(id).quantity();
                    cart.put(id, new CartLine(id, quantity));
                }
            } catch (RuntimeException ignored) { /* Invalid form values do not change the cart. */ }
        }
        response.sendRedirect(request.getContextPath() + "/cart");
    }
}
