package com.shopsphere.model;

import java.io.Serializable;
import java.math.BigDecimal;

public record Product(long id, String name, String brand, String description,
                      BigDecimal price, BigDecimal discountPercent, int stock,
                      String imageUrl) implements Serializable {
    private static final long serialVersionUID = 1L;

    public BigDecimal salePrice() {
        return price.multiply(BigDecimal.ONE.subtract(discountPercent.divide(new BigDecimal("100"))))
                .setScale(2, java.math.RoundingMode.HALF_UP);
    }
    public long getId() { return id; }
    public String getName() { return name; }
    public String getBrand() { return brand; }
    public String getDescription() { return description; }
    public BigDecimal getPrice() { return price; }
    public BigDecimal getDiscountPercent() { return discountPercent; }
    public int getStock() { return stock; }
    public String getImageUrl() { return imageUrl; }
    public BigDecimal getSalePrice() { return salePrice(); }
}
