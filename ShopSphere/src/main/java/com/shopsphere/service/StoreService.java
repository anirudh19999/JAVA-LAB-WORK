package com.shopsphere.service;

import com.shopsphere.dao.ProductDAO;
import com.shopsphere.model.CartLine;
import com.shopsphere.model.Product;
import com.shopsphere.util.Database;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

public class StoreService {
    private final ProductDAO products = new ProductDAO();

    public List<Product> search(String query) throws SQLException { return products.findActive(query); }

    public Map<Product, Integer> cart(Map<Long, CartLine> lines) throws SQLException {
        Map<Product, Integer> result = new LinkedHashMap<>();
        for (CartLine line : lines.values()) products.findById(line.productId()).ifPresent(p -> result.put(p, line.quantity()));
        return result;
    }

    public long placeOrder(long userId, Map<Long, CartLine> lines) throws SQLException {
        if (lines.isEmpty()) throw new IllegalArgumentException("Your cart is empty.");
        try (Connection c = Database.getConnection()) {
            c.setAutoCommit(false);
            try {
                BigDecimal total = BigDecimal.ZERO;
                record Checked(Product product, int quantity) { }
                List<Checked> checked = new ArrayList<>();
                for (CartLine line : lines.values()) {
                    try (PreparedStatement ps = c.prepareStatement("SELECT product_id,name,brand,description,price,discount_percent,stock,image_url FROM products WHERE product_id=? AND active=TRUE FOR UPDATE")) {
                        ps.setLong(1, line.productId());
                        try (ResultSet rs = ps.executeQuery()) {
                            if (!rs.next()) throw new IllegalArgumentException("A product in your cart is no longer available.");
                            Product p = new Product(rs.getLong("product_id"), rs.getString("name"), rs.getString("brand"), rs.getString("description"), rs.getBigDecimal("price"), rs.getBigDecimal("discount_percent"), rs.getInt("stock"), rs.getString("image_url"));
                            if (line.quantity() < 1 || line.quantity() > p.stock()) throw new IllegalArgumentException("There is not enough stock for " + p.name() + ".");
                            total = total.add(p.salePrice().multiply(BigDecimal.valueOf(line.quantity())));
                            checked.add(new Checked(p, line.quantity()));
                        }
                    }
                }
                long orderId;
                try (PreparedStatement ps = c.prepareStatement("INSERT INTO orders(user_id,total_amount,payment_method,payment_status,order_status) VALUES(?,?,'MOCK','MOCK_PAID','PLACED')", Statement.RETURN_GENERATED_KEYS)) {
                    ps.setLong(1, userId); ps.setBigDecimal(2, total); ps.executeUpdate();
                    try (ResultSet keys = ps.getGeneratedKeys()) { if (!keys.next()) throw new SQLException("Order ID was not returned."); orderId = keys.getLong(1); }
                }
                for (Checked item : checked) {
                    try (PreparedStatement ps = c.prepareStatement("INSERT INTO order_items(order_id,product_id,product_name,quantity,unit_price) VALUES(?,?,?,?,?)")) {
                        ps.setLong(1, orderId); ps.setLong(2, item.product().id()); ps.setString(3, item.product().name()); ps.setInt(4, item.quantity()); ps.setBigDecimal(5, item.product().salePrice()); ps.executeUpdate();
                    }
                    try (PreparedStatement ps = c.prepareStatement("UPDATE products SET stock=stock-? WHERE product_id=? AND stock>=?")) {
                        ps.setInt(1, item.quantity()); ps.setLong(2, item.product().id()); ps.setInt(3, item.quantity());
                        if (ps.executeUpdate() != 1) throw new IllegalArgumentException("Stock changed while placing the order. Please retry.");
                    }
                }
                c.commit();
                return orderId;
            } catch (Exception ex) {
                c.rollback();
                if (ex instanceof SQLException sql) throw sql;
                if (ex instanceof IllegalArgumentException badInput) throw badInput;
                throw new SQLException("Could not place order", ex);
            } finally { c.setAutoCommit(true); }
        }
    }
}
