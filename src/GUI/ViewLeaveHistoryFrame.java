package GUI;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import hrm.HRMService;
import hrm.LeaveRecord;

public class ViewLeaveHistoryFrame extends JFrame {
    private HRMService hrmService;
    private int employeeId;
    private JTable table;
    private DefaultTableModel tableModel;
    
    public ViewLeaveHistoryFrame(HRMService hrmService, int employeeId) {
        // Store the arguments
        this.hrmService = hrmService;
        this.employeeId = employeeId;
        
        setTitle("View Leave History");
        setSize(500, 300);
        setLocationRelativeTo(null);
        
        // Create components
        JLabel lblEmployeeId = new JLabel("Employee ID:");
        JTextField txtEmployeeId = new JTextField(String.valueOf(employeeId), 20);
        txtEmployeeId.setEditable(false); // Make it read-only
        
        JButton btnView = new JButton("View History");
        
        // Table to display leave history
        String[] columnNames = {"Leave ID", "Start Date", "End Date", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        
        // Add action listener to the view button
        btnView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshLeaveHistory();
            }
        });
        
        // Layout components
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        topPanel.add(lblEmployeeId);
        topPanel.add(txtEmployeeId);
        topPanel.add(btnView);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        add(panel);
        
        // Load leave history when the frame opens
        refreshLeaveHistory();
    }
    
    private void refreshLeaveHistory() {
        try {
            // Clear existing data
            tableModel.setRowCount(0);
            
            // Call the RMI service to get leave history
            List<LeaveRecord> leaveHistory = hrmService.viewLeaveHistory(employeeId);
            
            // Add new data to the table
            if (leaveHistory != null) {
                for (LeaveRecord record : leaveHistory) {
                    Object[] row = {
                        record.getLeaveId(),
                        record.getStartDate(),
                        record.getEndDate(),
                        record.getStatus()
                    };
                    tableModel.addRow(row);
                }
            }
            
            if (tableModel.getRowCount() == 0) {
                JOptionPane.showMessageDialog(this, 
                    "No leave records found for employee ID: " + employeeId,
                    "Information", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, 
                "Unexpected error: " + e.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}