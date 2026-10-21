package com.shopsphere.demo;

import com.shopsphere.model.Product;
import java.io.*;
import java.math.BigDecimal;

public final class SerializationDemo {
    private SerializationDemo() { }
    public static void main(String[] args) throws Exception {
        Product original = new Product(7, "Sample lamp", "Luma", "Serializable demo object", new BigDecimal("25.00"), BigDecimal.ZERO, 4, "");
        byte[] bytes;
        try (ByteArrayOutputStream buffer = new ByteArrayOutputStream(); ObjectOutputStream out = new ObjectOutputStream(buffer)) {
            out.writeObject(original); bytes = buffer.toByteArray();
        }
        try (ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes))) {
            Product restored = (Product) in.readObject();
            System.out.println("Restored product: " + restored.name() + " (" + bytes.length + " bytes)");
        }
    }
}
