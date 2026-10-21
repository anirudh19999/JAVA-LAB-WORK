package com.shopsphere.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InventoryService extends Remote {
    int getStock(int productId) throws RemoteException;
    boolean updateStock(int productId, int quantity) throws RemoteException;
}
