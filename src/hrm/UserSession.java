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

    // Serialize
    public void saveSession(String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Deserialize
    public static UserSession loadSession(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (UserSession) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }

    // Getters and setters
    public int getUserId() { return userId; }
    public String getUsername() { return username; }
    public boolean isRememberMe() { return rememberMe; }
}