// User.java - Konsep Enkapsulasi dan Pewarisan (Parent Class)
public class User {
    private String userID;
    private String nama;
    private String role;
    private String password;

    // Constructor
    public User(String userID, String nama, String role, String password) {
        this.userID = userID;
        this.nama = nama;
        this.role = role;
        this.password = password;
    }

    // Getter dan Setter - Enkapsulasi
    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Method untuk Polimorfisme
    public String getInfo() {
        return "User: " + nama + " (" + role + ")";
    }

    public void displayMenu() {
        System.out.println("Menu User");
    }
}