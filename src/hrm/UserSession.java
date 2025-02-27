package hrm;

import java.io.*;

public class UserSession implements Serializable {
    private int userId;
    private String username;
    private boolean rememberMe;

    public UserSession(int userId, String username, boolean rememberMe) {
        this.userId = userId;
        this.username = username;
        this.rememberMe = rememberMe;
    }

    // Serialize the session to a file
    public void saveSession(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Deserialize the session from a file
    public static UserSession loadSession(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (UserSession) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null; // No saved session
        }
    }

    // Getters and setters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public boolean isRememberMe() { return rememberMe; }
}