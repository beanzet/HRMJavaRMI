package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import hrm.HRMService;

public class ApplyForLeaveFrame extends JFrame {
    private HRMService hrmService;
    private int employeeId;

    public ApplyForLeaveFrame(HRMService hrmService, int employeeId) {
        this.hrmService = hrmService;
        this.employeeId = employeeId;
        
        setTitle("Apply for Leave");
        setSize(400, 200);
        setLocationRelativeTo(null);
        
        // Create components
        JLabel lblEmployeeId = new JLabel("Employee ID:");
        JTextField txtEmployeeId = new JTextField(String.valueOf(employeeId), 20);
        txtEmployeeId.setEditable(false); // Make it read-only since it's passed in
        
        JLabel lblStartDate = new JLabel("Start Date (YYYY-MM-DD):");
        JTextField txtStartDate = new JTextField(20);
        
        JLabel lblEndDate = new JLabel("End Date (YYYY-MM-DD):");
        JTextField txtEndDate = new JTextField(20);
        
        JButton btnApply = new JButton("Apply");
        
        // Add action listener to the apply button
        btnApply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String startDate = txtStartDate.getText();
                String endDate = txtEndDate.getText();
                
                try {
                    // Call the backend function to apply for leave
                    hrmService.applyForLeave(employeeId, startDate, endDate);
                    JOptionPane.showMessageDialog(ApplyForLeaveFrame.this, "Leave application submitted!");
                    dispose(); // Close the frame after submission
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(ApplyForLeaveFrame.this, 
                        "Error submitting leave application: " + ex.getMessage(), 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        // Add components to the frame
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(lblEmployeeId);
        panel.add(txtEmployeeId);
        panel.add(lblStartDate);
        panel.add(txtStartDate);
        panel.add(lblEndDate);
        panel.add(txtEndDate);
        panel.add(new JLabel()); // Empty label for spacing
        panel.add(btnApply);
        
        add(panel);
    }
}