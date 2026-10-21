package com.shopsphere.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public final class InventoryClientMain {
    private InventoryClientMain() { }
    public static void main(String[] args) throws Exception {
        String host = args.length == 0 ? "localhost" : args[0];
        Registry registry = LocateRegistry.getRegistry(host, 1099);
        InventoryService service = (InventoryService) registry.lookup("ShopSphereInventory");
        System.out.println("Stock for product 1: " + service.getStock(1));
    }
}
