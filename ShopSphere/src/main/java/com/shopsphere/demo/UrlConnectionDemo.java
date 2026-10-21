package com.shopsphere.demo;

import java.net.URL;
import java.net.URLConnection;

public final class UrlConnectionDemo {
    private UrlConnectionDemo() { }
    public static void main(String[] args) throws Exception {
        URL url = new URL(args.length == 0 ? "https://example.com/" : args[0]);
        URLConnection connection = url.openConnection();
        connection.setConnectTimeout(4000); connection.setReadTimeout(4000);
        System.out.println("Content type: " + connection.getContentType());
        System.out.println("Protocol handler: " + url.getProtocol());
    }
}
