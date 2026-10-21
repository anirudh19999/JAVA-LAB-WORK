package com.shopsphere.demo;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/** Run inside a Tomcat application configured with java:comp/env/jdbc/ShopSphereDB. */
public final class JndiDemo {
    private JndiDemo() { }
    public static void main(String[] args) throws Exception {
        DataSource source = (DataSource) new InitialContext().lookup("java:comp/env/jdbc/ShopSphereDB");
        try (var connection = source.getConnection()) { System.out.println("JNDI connection open: " + !connection.isClosed()); }
    }
}
