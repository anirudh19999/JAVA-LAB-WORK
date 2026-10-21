package com.shopsphere.dao;

import com.shopsphere.model.Product;
import com.shopsphere.util.Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductDAO {
    private static final String COLUMNS = "product_id,name,brand,description,price,discount_percent,stock,image_url";

    public List<Product> findActive(String search) throws SQLException {
        String sql = "SELECT " + COLUMNS + " FROM products WHERE active=TRUE AND (name LIKE ? OR brand LIKE ?) ORDER BY product_id DESC";
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(sql)) {
            String term = "%" + (search == null ? "" : search.trim()) + "%";
            ps.setString(1, term); ps.setString(2, term);
            try (ResultSet rs = ps.executeQuery()) {
                List<Product> products = new ArrayList<>();
                while (rs.next()) products.add(map(rs));
                return products;
            }
        }
    }

    public Optional<Product> findById(long id) throws SQLException {
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(
                "SELECT " + COLUMNS + " FROM products WHERE product_id=? AND active=TRUE")) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) { return rs.next() ? Optional.of(map(rs)) : Optional.empty(); }
        }
    }

    private Product map(ResultSet rs) throws SQLException {
        return new Product(rs.getLong("product_id"), rs.getString("name"), rs.getString("brand"),
                rs.getString("description"), rs.getBigDecimal("price"), rs.getBigDecimal("discount_percent"),
                rs.getInt("stock"), rs.getString("image_url"));
    }
}
