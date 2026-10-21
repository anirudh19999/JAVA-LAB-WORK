package com.shopsphere.controller;

import com.shopsphere.service.StoreService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(urlPatterns = {"/", "/products"})
public class ProductServlet extends HttpServlet {
    private final StoreService store = new StoreService();
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String query = request.getParameter("q");
            request.setAttribute("products", store.search(query));
            request.setAttribute("query", query == null ? "" : query);
            request.getRequestDispatcher("/WEB-INF/views/products.jsp").forward(request, response);
        } catch (Exception ex) { throw new ServletException("Unable to load products. Check the database settings and schema.", ex); }
    }
}
