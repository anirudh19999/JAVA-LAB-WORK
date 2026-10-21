package com.shopsphere.util;

import com.shopsphere.model.CartLine;
import com.shopsphere.model.Product;
import com.shopsphere.model.User;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/** In-memory data for a one-machine classroom preview. It resets when the JVM stops. */
public final class DemoStore {
    public record Order(long id, BigDecimal total, String status, Timestamp createdAt) { }
    private record Account(User user, String passwordHash) { }
    private static final Map<Long, Product> PRODUCTS = new ConcurrentHashMap<>();
    private static final Map<String, Account> USERS = new ConcurrentHashMap<>();
    private static final Map<Long, List<Order>> ORDERS = new ConcurrentHashMap<>();
    private static long nextUserId = 1;
    private static long nextOrderId = 1001;

    static {
        addSeed(1, "Studio Wireless Headphones", "Northline", "Comfortable wireless headphones with balanced sound.", "4499.00", "10", 18, "https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=900&q=80");
        addSeed(2, "Everyday Laptop Stand", "Forma", "Foldable aluminum stand for a cleaner desk setup.", "1899.00", "0", 24, "https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?auto=format&fit=crop&w=900&q=80");
        addSeed(3, "Arc Table Lamp", "Luma", "Warm dimmable light with a compact metal base.", "2599.00", "15", 12, "https://images.unsplash.com/photo-1507473885765-e6ed057f782c?auto=format&fit=crop&w=900&q=80");
        addSeed(4, "Portable Bluetooth Speaker", "Soundwell", "Pocket-size speaker for work and weekends.", "3299.00", "5", 20, "https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?auto=format&fit=crop&w=900&q=80");
        addSeed(5, "Mechanical Keyboard", "Keycraft", "Tactile compact keyboard with a clean layout.", "5699.00", "12", 9, "https://images.unsplash.com/photo-1587829741301-dc798b83add3?auto=format&fit=crop&w=900&q=80");
        addSeed(6, "Ceramic Pour-over Set", "Sunday Supply", "A simple coffee set made for slow mornings.", "1499.00", "0", 16, "https://images.unsplash.com/photo-1495474472287-4d71bcdd2085?auto=format&fit=crop&w=900&q=80");
        User admin = new User(nextUserId++, "ShopSphere Demo Admin", "admin@shopsphere.local", "ADMIN");
        USERS.put(admin.email(), new Account(admin, PasswordUtil.hash("DemoAdmin123!".toCharArray())));
    }

    private DemoStore() { }
    private static void addSeed(long id, String name, String brand, String description, String price, String discount, int stock, String image) {
        PRODUCTS.put(id, new Product(id, name, brand, description, new BigDecimal(price), new BigDecimal(discount), stock, image));
    }

    public static List<Product> products(String search) {
        String q = search == null ? "" : search.trim().toLowerCase(Locale.ROOT);
        return PRODUCTS.values().stream().filter(p -> (p.name() + " " + p.brand()).toLowerCase(Locale.ROOT).contains(q))
                .sorted(Comparator.comparingLong(Product::id).reversed()).toList();
    }

    public static Optional<Product> product(long id) { return Optional.ofNullable(PRODUCTS.get(id)); }

    public static synchronized User authenticate(String email, char[] password) {
        Account account = USERS.get(email);
        return account != null && PasswordUtil.verify(password, account.passwordHash()) ? account.user() : null;
    }

    public static synchronized void register(String name, String email, char[] password) throws SQLException {
        if (USERS.containsKey(email)) throw new SQLException("Email already exists", "23000", 1062);
        User user = new User(nextUserId++, name, email, "CUSTOMER");
        USERS.put(email, new Account(user, PasswordUtil.hash(password)));
    }

    public static synchronized long placeOrder(long userId, Map<Long, CartLine> lines) {
        if (lines.isEmpty()) throw new IllegalArgumentException("Your cart is empty.");
        BigDecimal total = BigDecimal.ZERO;
        for (CartLine line : lines.values()) {
            Product p = PRODUCTS.get(line.productId());
            if (p == null) throw new IllegalArgumentException("A product in your cart is no longer available.");
            if (line.quantity() < 1 || line.quantity() > p.stock()) throw new IllegalArgumentException("There is not enough stock for " + p.name() + ".");
            total = total.add(p.salePrice().multiply(BigDecimal.valueOf(line.quantity())));
        }
        for (CartLine line : lines.values()) {
            Product p = PRODUCTS.get(line.productId());
            PRODUCTS.put(p.id(), new Product(p.id(), p.name(), p.brand(), p.description(), p.price(), p.discountPercent(), p.stock() - line.quantity(), p.imageUrl()));
        }
        long id = nextOrderId++;
        ORDERS.computeIfAbsent(userId, ignored -> new ArrayList<>()).add(0, new Order(id, total, "PLACED", Timestamp.from(Instant.now())));
        return id;
    }

    public static synchronized List<Order> orders(long userId) { return List.copyOf(ORDERS.getOrDefault(userId, List.of())); }

    public static synchronized List<Product> adminProducts() { return PRODUCTS.values().stream().sorted(Comparator.comparingLong(Product::id).reversed()).toList(); }
    public static synchronized void addProduct(String name, String brand, String description, BigDecimal price, int stock) {
        long id = PRODUCTS.keySet().stream().mapToLong(Long::longValue).max().orElse(0) + 1;
        PRODUCTS.put(id, new Product(id, name, brand, description, price, BigDecimal.ZERO, stock, ""));
    }
    public static synchronized void updateStock(long id, int stock) {
        Product p = PRODUCTS.get(id);
        if (p != null) PRODUCTS.put(id, new Product(p.id(), p.name(), p.brand(), p.description(), p.price(), p.discountPercent(), stock, p.imageUrl()));
    }
}
