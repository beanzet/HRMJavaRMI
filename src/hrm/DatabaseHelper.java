package hrm;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

class DatabaseHelper {
    private Connection connection;

    public DatabaseHelper() {
        // Initialize database connection
        try {
            connection = DriverManager.getConnection("jdbc:derby://localhost:1527/hrmtestdb", "HRMAdmin", "adminhrm");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void registerEmployee(String firstName, String lastName, String icPassportNumber) {
        String sql = "INSERT INTO Employees (first_name, last_name, ic_passport_number) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, icPassportNumber);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void createUser(int employeeId, String username, String password, boolean isHR) {
        String sql = "INSERT INTO Users (employee_id, username, password, is_hr) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, employeeId);
            stmt.setString(2, username);
            stmt.setString(3, password);
            stmt.setBoolean(4, isHR);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateEmployeeProfile(int employeeId, String firstName, String lastName, String icPassportNumber) {
        // Update employee profile
    }

    public void applyForLeave(int employeeId, String startDate, String endDate) {
        new Thread(() -> {
            String sql = "INSERT INTO LeaveRecords (employee_id, start_date, end_date, status) VALUES (?, ?, 'PENDING')";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, employeeId);
                stmt.setString(2, startDate);
                stmt.setString(3, endDate);
                stmt.executeUpdate();
                System.out.println("Leave application submitted successfully.");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }).start();
    }


    public String checkLeaveStatus(int leaveId) {
        // Query leave status
        return "PENDING"; // Example
    }

    public int checkLeaveBalance(int employeeId) {
        // Query leave balance
        return 10; // Example
    }

    public List<LeaveRecord> viewLeaveHistory(int employeeId) {
      List<LeaveRecord> leaveRecords = new ArrayList<>();
      String sql = "SELECT leave_id, start_date, end_date, status FROM LeaveRecords " +
                   "WHERE employee_id = ? ORDER BY start_date DESC";

      try (PreparedStatement stmt = connection.prepareStatement(sql)) {
          stmt.setInt(1, employeeId);
          try (ResultSet rs = stmt.executeQuery()) {
              while (rs.next()) {
                  LeaveRecord record = new LeaveRecord();
                  record.setLeaveId(rs.getInt("leave_id"));
                  record.setEmployeeId(employeeId);
                  record.setStartDate(rs.getDate("start_date").toString());
                  record.setEndDate(rs.getDate("end_date").toString());
                  record.setStatus(rs.getString("status"));

                  leaveRecords.add(record);
              }
          }
      } catch (SQLException e) {
          e.printStackTrace();
      }

      return leaveRecords;
  }


    public void approveLeave(int leaveId) {
        // Update leave status to APPROVED
    }

    public void rejectLeave(int leaveId) {
        // Update leave status to REJECTED
    }

    public String generateYearlyReport(int employeeId) {
        // Generate and return a yearly leave report
        return "Yearly Report"; // Example
    }
    
    public int validateUser(String username, String password) {
        String sql = "SELECT user_id, employee_id FROM Users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("employee_id"); // Return employee_id for the logged-in user
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Invalid credentials
    }
    
    public boolean isHRUser(int userId) {
        String sql = "SELECT is_hr FROM Users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("is_hr");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}