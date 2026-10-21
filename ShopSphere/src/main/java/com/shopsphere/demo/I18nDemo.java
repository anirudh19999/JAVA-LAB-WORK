package com.shopsphere.demo;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public final class I18nDemo {
    private I18nDemo() { }
    public static void main(String[] args) {
        Locale locale = args.length > 0 && "hi".equalsIgnoreCase(args[0]) ? Locale.forLanguageTag("hi-IN") : Locale.US;
        ResourceBundle messages = ResourceBundle.getBundle("messages", locale);
        System.out.println(messages.getString("welcome"));
        System.out.println(NumberFormat.getCurrencyInstance(locale).format(4499.0));
    }
}
