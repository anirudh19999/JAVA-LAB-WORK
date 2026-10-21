package com.shopsphere.controller;

import com.shopsphere.util.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/products")
public class AdminProductServlet extends HttpServlet {
    public record AdminProduct(long id, String name, BigDecimal price, int stock, boolean active) {
        public long getId() { return id; }
        public String getName() { return name; }
        public BigDecimal getPrice() { return price; }
        public int getStock() { return stock; }
        public boolean isActive() { return active; }
    }
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<AdminProduct> products = new ArrayList<>();
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT product_id,name,price,stock,active FROM products ORDER BY product_id DESC"); ResultSet rs = ps.executeQuery()) {
            while (rs.next()) products.add(new AdminProduct(rs.getLong(1), rs.getString(2), rs.getBigDecimal(3), rs.getInt(4), rs.getBoolean(5)));
        } catch (SQLException ex) { throw new ServletException("Unable to load admin products", ex); }
        request.setAttribute("products", products);
        request.getRequestDispatcher("/WEB-INF/views/admin-products.jsp").forward(request, response);
    }
    @Override protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        try (Connection c = Database.getConnection()) {
            if ("create".equals(action)) {
                String name = clean(request.getParameter("name"));
                String brand = clean(request.getParameter("brand"));
                String description = clean(request.getParameter("description"));
                BigDecimal price = new BigDecimal(request.getParameter("price"));
                int stock = Integer.parseInt(request.getParameter("stock"));
                if (name.isBlank() || name.length() > 160 || price.signum() < 0 || stock < 0 || stock > 1_000_000) throw new IllegalArgumentException();
                try (PreparedStatement ps = c.prepareStatement("INSERT INTO products(name,brand,description,price,stock,active) VALUES(?,?,?,?,?,TRUE)")) {
                    ps.setString(1, name); ps.setString(2, brand); ps.setString(3, description); ps.setBigDecimal(4, price); ps.setInt(5, stock); ps.executeUpdate();
                }
            } else if ("stock".equals(action)) {
                long id = Long.parseLong(request.getParameter("productId"));
                int stock = Integer.parseInt(request.getParameter("stock"));
                if (stock < 0 || stock > 1_000_000) throw new IllegalArgumentException();
                try (PreparedStatement ps = c.prepareStatement("UPDATE products SET stock=? WHERE product_id=?")) { ps.setInt(1, stock); ps.setLong(2, id); ps.executeUpdate(); }
            } else throw new IllegalArgumentException();
            response.sendRedirect(request.getContextPath() + "/admin/products?saved=1");
        } catch (IllegalArgumentException ex) {
            request.setAttribute("error", "Check the product details. Price and stock must be valid non-negative numbers.");
            doGet(request, response);
        } catch (SQLException ex) { throw new ServletException("Unable to save product", ex); }
    }
    private static String clean(String text) { return text == null ? "" : text.trim(); }
}
