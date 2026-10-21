package com.shopsphere.model;

import java.io.Serializable;

public record User(long id, String name, String email, String role) implements Serializable {
    private static final long serialVersionUID = 1L;
    public boolean isAdmin() { return "ADMIN".equals(role); }
    public long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
