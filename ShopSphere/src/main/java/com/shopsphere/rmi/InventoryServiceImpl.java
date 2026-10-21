package com.shopsphere.rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class InventoryServiceImpl extends UnicastRemoteObject implements InventoryService {
    private final Map<Integer, Integer> stock = new ConcurrentHashMap<>(Map.of(1, 18, 2, 24, 3, 12));
    public InventoryServiceImpl() throws RemoteException { super(); }
    @Override public int getStock(int productId) { return stock.getOrDefault(productId, -1); }
    @Override public boolean updateStock(int productId, int quantity) {
        if (quantity < 0 || !stock.containsKey(productId)) return false;
        stock.put(productId, quantity);
        return true;
    }
}
