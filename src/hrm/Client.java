package hrm;

import GUI.LoginFrame;
import java.rmi.RemoteException;

public class Client {
    public static void main(String[] args) throws RemoteException {
        new LoginFrame().setVisible(true);
    }
}