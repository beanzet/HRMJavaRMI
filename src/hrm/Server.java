package hrm;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            // Create an instance of the HRM service
            HRMService hrmService = new HRMServiceImpl();

            // Start the RMI registry on port 1099
            Registry registry = LocateRegistry.createRegistry(1099);

            // Bind the HRM service to the registry
            registry.rebind("HRMService", hrmService);

            System.out.println("HRM Server is running...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}