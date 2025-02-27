package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CheckLeaveBalanceFrame extends JFrame {
    public CheckLeaveBalanceFrame() {
        setTitle("Check Leave Balance");
        setSize(400, 150);
        setLocationRelativeTo(null);

        // Create components
        JLabel lblEmployeeId = new JLabel("Employee ID:");
        JTextField txtEmployeeId = new JTextField(20);

        JButton btnCheck = new JButton("Check Balance");

        JLabel lblBalance = new JLabel("Leave Balance: ");

        // Add action listener to the check button
        btnCheck.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int employeeId = Integer.parseInt(txtEmployeeId.getText());

                // Call the backend function to check leave balance
                int balance = 10; // Example: Replace with hrmService.checkLeaveBalance(employeeId);

                lblBalance.setText("Leave Balance: " + balance);
            }
        });

        // Add components to the frame
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.add(lblEmployeeId);
        panel.add(txtEmployeeId);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(btnCheck);
        panel.add(lblBalance);

        add(panel);
    }
}