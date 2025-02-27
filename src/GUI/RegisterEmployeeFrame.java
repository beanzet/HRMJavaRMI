package GUI;

import hrm.HRMService;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class RegisterEmployeeFrame extends JFrame {
    private HRMService hrmService;
    
    public RegisterEmployeeFrame(HRMService hrmService) {
            // Connect to the RMI registry
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            this.hrmService = (HRMService) registry.lookup("HRMService");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to the server.");
            dispose();
        }
        
        setTitle("Register Employee");
        setSize(400, 200);
        setLocationRelativeTo(null);

        // Create components
        JLabel lblFirstName = new JLabel("First Name:");
        JTextField txtFirstName = new JTextField(20);

        JLabel lblLastName = new JLabel("Last Name:");
        JTextField txtLastName = new JTextField(20);

        JLabel lblICPassport = new JLabel("IC/Passport Number:");
        JTextField txtICPassport = new JTextField(20);

        JButton btnRegister = new JButton("Register");

        // Add action listener to the register button
        btnRegister.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String icPassport = txtICPassport.getText();

                try {
                hrmService.registerEmployee(firstName, lastName, icPassport);
                JOptionPane.showMessageDialog(RegisterEmployeeFrame.this, 
                    "Employee registered successfully!");
                dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(RegisterEmployeeFrame.this,
                        "Error: " + ex.getMessage(), "Registration Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add components to the frame
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(lblFirstName);
        panel.add(txtFirstName);
        panel.add(lblLastName);
        panel.add(txtLastName);
        panel.add(lblICPassport);
        panel.add(txtICPassport);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(btnRegister);

        add(panel);
    }
}