package com.shopsphere.controller;

import com.shopsphere.model.User;
import com.shopsphere.util.Database;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/orders")
public class OrdersServlet extends HttpServlet {
    public record OrderRow(long id, java.math.BigDecimal total, String status, Timestamp createdAt) {
        public long getId() { return id; }
        public java.math.BigDecimal getTotal() { return total; }
        public String getStatus() { return status; }
        public Timestamp getCreatedAt() { return createdAt; }
    }
    @Override protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        List<OrderRow> orders = new ArrayList<>();
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement("SELECT order_id,total_amount,order_status,created_at FROM orders WHERE user_id=? ORDER BY created_at DESC")) {
            ps.setLong(1, user.id());
            try (ResultSet rs = ps.executeQuery()) { while (rs.next()) orders.add(new OrderRow(rs.getLong(1), rs.getBigDecimal(2), rs.getString(3), rs.getTimestamp(4))); }
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(request, response);
        } catch (SQLException ex) { throw new ServletException("Unable to load orders", ex); }
    }
}
