package hrm;

import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.List;

class HRMServiceImpl extends UnicastRemoteObject implements HRMService {
    private DatabaseHelper dbHelper; // Helper class to interact with the database

    public HRMServiceImpl() throws RemoteException {
        super();
        dbHelper = new DatabaseHelper(); // Initialize database connection
    }

    @Override
    public void registerEmployee(String firstName, String lastName, String icPassportNumber) throws RemoteException {
        dbHelper.registerEmployee(firstName, lastName, icPassportNumber);
    }

    @Override
    public void updateEmployeeProfile(int employeeId, String firstName, String lastName, String icPassportNumber) throws RemoteException {
        dbHelper.updateEmployeeProfile(employeeId, firstName, lastName, icPassportNumber);
    }

    @Override
    public void applyForLeave(int employeeId, String startDate, String endDate) throws RemoteException {
        dbHelper.applyForLeave(employeeId, startDate, endDate);
    }

    @Override
    public String checkLeaveStatus(int leaveId) throws RemoteException {
        return dbHelper.checkLeaveStatus(leaveId);
    }

    @Override
    public int checkLeaveBalance(int employeeId) throws RemoteException {
        return dbHelper.checkLeaveBalance(employeeId);
    }

    @Override
    public List<LeaveRecord> viewLeaveHistory(int employeeId) throws RemoteException {
        return dbHelper.viewLeaveHistory(employeeId);
    }

    @Override
    public void approveLeave(int leaveId) throws RemoteException {
        dbHelper.approveLeave(leaveId);
    }

    @Override
    public void rejectLeave(int leaveId) throws RemoteException {
        dbHelper.rejectLeave(leaveId);
    }

    @Override
    public String generateYearlyReport(int employeeId) throws RemoteException {
        return dbHelper.generateYearlyReport(employeeId);
    }
    
        @Override
    public int validateUser(String username, String password) throws RemoteException {
        return dbHelper.validateUser(username, password);
    }

    @Override
    public boolean isHRUser(int userId) throws RemoteException {
        return dbHelper.isHRUser(userId);
    }
}