package com.shopsphere.dao;

import com.shopsphere.model.User;
import com.shopsphere.util.Database;
import com.shopsphere.util.PasswordUtil;

import java.sql.*;
import java.util.Optional;

public class UserDAO {
    public Optional<User> authenticate(String email, char[] password) throws SQLException {
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(
                "SELECT user_id,name,email,password_hash,role FROM users WHERE email=?")) {
            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next() || !PasswordUtil.verify(password, rs.getString("password_hash"))) return Optional.empty();
                return Optional.of(new User(rs.getLong("user_id"), rs.getString("name"), rs.getString("email"), rs.getString("role")));
            }
        }
    }

    public void register(String name, String email, char[] password) throws SQLException {
        try (Connection c = Database.getConnection(); PreparedStatement ps = c.prepareStatement(
                "INSERT INTO users(name,email,password_hash) VALUES(?,?,?)")) {
            ps.setString(1, name); ps.setString(2, email); ps.setString(3, PasswordUtil.hash(password)); ps.executeUpdate();
        }
    }
}
