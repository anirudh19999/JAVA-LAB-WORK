package com.shopsphere.web;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/** Small JSP Tag Extension API example that emits a safe stock label. */
public class StockStatusTag extends SimpleTagSupport {
    private int stock;
    public void setStock(int stock) { this.stock = stock; }
    @Override public void doTag() throws JspException, IOException {
        getJspContext().getOut().write(stock > 0 ? "In stock" : "Sold out");
    }
}
