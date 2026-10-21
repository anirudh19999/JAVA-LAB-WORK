package com.shopsphere.model;

import java.io.Serializable;

public record CartLine(long productId, int quantity) implements Serializable {
    private static final long serialVersionUID = 1L;
}
