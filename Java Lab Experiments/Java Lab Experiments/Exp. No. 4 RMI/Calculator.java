import java.rmi.Remote;
import java.rmi.RemoteException;

/** Remote interface for basic calculator operations. */
public interface Calculator extends Remote {
    int add(int a, int b) throws RemoteException;
    int subtract(int a, int b) throws RemoteException;
}
