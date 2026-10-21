package com.shopsphere.rmi;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public final class InventoryServerMain {
    private InventoryServerMain() { }
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.createRegistry(1099);
        registry.rebind("ShopSphereInventory", new InventoryServiceImpl());
        System.out.println("RMI inventory service is ready on port 1099.");
    }
}
