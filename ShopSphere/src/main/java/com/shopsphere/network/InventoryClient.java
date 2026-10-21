package com.shopsphere.network;

import java.io.*;
import java.net.Socket;

public final class InventoryClient {
    private InventoryClient() { }
    public static void main(String[] args) throws IOException {
        String host = args.length > 0 ? args[0] : "localhost";
        int port = args.length > 1 ? Integer.parseInt(args[1]) : 5050;
        try (Socket socket = new Socket(host, port);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {
            out.println("GET_STOCK 1");
            System.out.println("Server replied: " + in.readLine());
            out.println("QUIT");
            System.out.println("Server replied: " + in.readLine());
        }
    }
}
