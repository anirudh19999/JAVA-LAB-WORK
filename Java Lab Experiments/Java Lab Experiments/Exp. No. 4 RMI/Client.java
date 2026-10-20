import java.rmi.Naming;

/** Looks up the remote calculator and calls its methods. */
public class Client {
    public static void main(String[] args) {
        try {
            Calculator calculator = (Calculator) Naming.lookup(
                    "rmi://localhost:1099/Calculator");

            System.out.println("Addition (10 + 20) = " + calculator.add(10, 20));
            System.out.println("Subtraction (20 - 10) = " + calculator.subtract(20, 10));
        } catch (Exception e) {
            System.err.println("Could not call the remote calculator: " + e.getMessage());
            System.err.println("Start Server.java first and make sure both programs use port 1099.");
        }
    }
}
