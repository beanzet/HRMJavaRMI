package GUI;

import javax.swing.*;
import java.awt.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import hrm.HRMService;
import java.io.File;
import java.rmi.RemoteException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainFrame extends JFrame {
    private HRMService hrmService;
    private int employeeId;

    public MainFrame(int employeeId) {
        this.employeeId = employeeId;

        try {
            // Connect to the RMI registry
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            hrmService = (HRMService) registry.lookup("HRMService");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the server.");
            System.exit(1); // Exit if connection fails
        }

        initializeUI(); // Call the method to set up the UI
    }

    private void initializeUI() {
        setTitle("HRM System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        // panel to hold components
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1, 10, 10)); // 5 rows, 1 column, with spacing

        // buttons
        JButton btnRegisterEmployee = new JButton("Register Employee");
        JButton btnApplyForLeave = new JButton("Apply for Leave");
        JButton btnCheckLeaveBalance = new JButton("Check Leave Balance");
        JButton btnViewLeaveHistory = new JButton("View Leave History");
        JButton btnLogout = new JButton("Logout");

        // action listeners
        btnRegisterEmployee.addActionListener(e -> {
            // Open the RegisterEmployeeFrame
            new RegisterEmployeeFrame(hrmService).setVisible(true);
        });

        btnApplyForLeave.addActionListener(e -> {
            // Open the ApplyForLeaveFrame
            new ApplyForLeaveFrame(hrmService, employeeId).setVisible(true);
        });

        btnCheckLeaveBalance.addActionListener(e -> {
            try {
                int balance = hrmService.checkLeaveBalance(employeeId);
                JOptionPane.showMessageDialog(this, "Your leave balance: " + balance);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        btnViewLeaveHistory.addActionListener(e -> {
            // Open the ViewLeaveHistoryFrame
            new ViewLeaveHistoryFrame(hrmService, employeeId).setVisible(true);
        });

        btnLogout.addActionListener(e -> {
            // Delete the session file when logging out
            File sessionFile = new File("session.ser");
            if (sessionFile.exists()) {
                boolean deleted = sessionFile.delete();
                if (!deleted) {
                    System.out.println("Warning: Could not delete session file");
                }
            }
            setVisible(false);
            // Close the MainFrame and open the LoginFrame
            dispose();
            try {
                new LoginFrame().setVisible(true);
            } catch (RemoteException ex) {
                Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        // Add buttons to the panel
        panel.add(btnRegisterEmployee);
        panel.add(btnApplyForLeave);
        panel.add(btnCheckLeaveBalance);
        panel.add(btnViewLeaveHistory);
        panel.add(btnLogout);

        // Add the panel to the frame
        add(panel);
    }
}