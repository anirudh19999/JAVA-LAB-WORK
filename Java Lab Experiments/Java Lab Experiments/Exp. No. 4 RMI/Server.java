import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

/** Starts the RMI registry and registers the remote calculator service. */
public class Server {
    public static void main(String[] args) {
        try {
            // Start an RMI registry on the default port (1099).
            LocateRegistry.createRegistry(1099);

            Calculator calculator = new CalculatorImpl();
            Naming.rebind("rmi://localhost:1099/Calculator", calculator);

            System.out.println("RMI Server started.");
            System.out.println("Calculator service registered at rmi://localhost:1099/Calculator");
            System.out.println("Keep this terminal open while running the client.");
        } catch (Exception e) {
            System.err.println("Could not start the RMI server: " + e.getMessage());
            System.err.println("Check whether port 1099 is already in use.");
        }
    }
}
