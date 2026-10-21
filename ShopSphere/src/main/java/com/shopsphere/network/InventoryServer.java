package com.shopsphere.network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/** Educational line protocol: GET_STOCK <id> and QUIT. */
public final class InventoryServer {
    private static final Map<Integer, Integer> STOCK = new ConcurrentHashMap<>(Map.of(1, 18, 2, 24, 3, 12));
    private InventoryServer() { }
    public static void main(String[] args) throws IOException {
        int port = args.length == 0 ? 5050 : Integer.parseInt(args[0]);
        try (ServerSocket server = new ServerSocket(port)) {
            System.out.println("Inventory demo listening on " + port);
            while (true) {
                try (Socket client = server.accept(); BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                     PrintWriter out = new PrintWriter(client.getOutputStream(), true)) {
                    String line;
                    while ((line = in.readLine()) != null) {
                        String[] parts = line.trim().split("\\s+");
                        if ("QUIT".equalsIgnoreCase(parts[0])) { out.println("BYE"); break; }
                        if (parts.length == 2 && "GET_STOCK".equalsIgnoreCase(parts[0])) {
                            try { Integer stock = STOCK.get(Integer.parseInt(parts[1])); out.println(stock == null ? "NOT_FOUND" : "STOCK " + stock); }
                            catch (NumberFormatException ex) { out.println("ERROR invalid product id"); }
                        } else out.println("ERROR use GET_STOCK <id> or QUIT");
                    }
                }
            }
        }
    }
}
