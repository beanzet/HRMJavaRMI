package hrm;

import GUI.LoginFrame;
import GUI.MainFrame;
import hrm.HRMService;
import java.rmi.AccessException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class Client {
    public static void main(String[] args) {
        // Launch the MainFrame 
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    // Connect to RMI Registry
                    Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                    HRMService hrmService = (HRMService) registry.lookup("HRMService");
                    
                    new LoginFrame().setVisible(true);
                } catch (RemoteException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "Failed to connect to RMI server", ex);
                } catch (NotBoundException ex) {
                    Logger.getLogger(Client.class.getName()).log(Level.SEVERE, "No RMI Server found", ex);
                }
            }
        });
    }
}