package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import hrm.HRMService;
import hrm.UserSession;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class LoginFrame extends JFrame {
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JCheckBox chkRememberMe;
    private HRMService hrmService; // RMI service

    public LoginFrame() throws RemoteException {
        
        try {
            Registry registry = LocateRegistry.getRegistry("localhost", 1099);
            hrmService = (HRMService) registry.lookup("HRMService");
        }
        catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to connect to server");
        }

        // Check for existing session
        UserSession session = UserSession.loadSession("session.ser");
        if (session != null && session.isRememberMe()) {
            // Auto-login and proceed to MainFrame
            SwingUtilities.invokeLater(() -> {
                setVisible(false); // Hide the frame to prevent flickering
                dispose(); // Immediately dispose of the LoginFrame
            });
            openMainFrame(session.getUserId());
            return;
        }
        
        setTitle("Login");
        setSize(400, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Login UI components
        JLabel lblUsername = new JLabel("Username:");
        JLabel lblPassword = new JLabel("Password:");
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        chkRememberMe = new JCheckBox("Remember Me");
        JButton btnLogin = new JButton("Login");

        // Action listener for login button
        btnLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = txtUsername.getText();
                String password = new String(txtPassword.getPassword());

                new SwingWorker<Integer, Void>() {
                    @Override
                    protected Integer doInBackground() throws Exception {
                        return hrmService.validateUser(username, password); // RMI call
                    }

                    @Override
                    protected void done() {
                        try {
                            int userId = get();
                            if (userId != -1) {
                                if (chkRememberMe.isSelected()) {
                                    new Thread(() -> {
                                        UserSession session = new UserSession(userId, username, true);
                                        session.saveSession("session.ser");
                                    }).start(); // Save session in a separate thread
                                }
                                openMainFrame(userId);
                                dispose();
                            } else {
                                JOptionPane.showMessageDialog(LoginFrame.this, "Invalid username or password.");
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                }.execute();
            }
        });


        // Layout
        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.add(lblUsername);
        panel.add(txtUsername);
        panel.add(lblPassword);
        panel.add(txtPassword);
        panel.add(new JLabel()); // Spacer
        panel.add(chkRememberMe);
        panel.add(new JLabel()); // Spacer
        panel.add(btnLogin);

        add(panel);
    }

    private void openMainFrame(int userId) throws RemoteException {
        MainFrame mainFrame = new MainFrame(userId);
        mainFrame.setVisible(true);
    }
}