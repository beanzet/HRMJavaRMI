package hrm;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface HRMService extends Remote {
    // Employee management
    void registerEmployee(String firstName, String lastName, String icPassportNumber) throws RemoteException;
    void updateEmployeeProfile(int employeeId, String firstName, String lastName, String icPassportNumber) throws RemoteException;

    // Leave management
    void applyForLeave(int employeeId, String startDate, String endDate) throws RemoteException;
    String checkLeaveStatus(int leaveId) throws RemoteException;
    int checkLeaveBalance(int employeeId) throws RemoteException;
    List<LeaveRecord> viewLeaveHistory(int employeeId) throws RemoteException;

    // HR-specific
    void approveLeave(int leaveId) throws RemoteException;
    void rejectLeave(int leaveId) throws RemoteException;
    String generateYearlyReport(int employeeId) throws RemoteException;
    
    // System
    int validateUser(String username, String password) throws RemoteException;
    boolean isHRUser(int userId) throws RemoteException;
    
}