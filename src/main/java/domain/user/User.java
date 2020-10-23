package domain.user;

import java.sql.Timestamp;

public class User {
    private enum Role {
        User,
        Admin
    }
    
    private final int id;
    private final String email;
    private final String name;
    private final String phoneno;
    private final byte[] salt;
    private final byte[] secret;
    private final Enum<Role> role;
    private final Timestamp timestamp;
    private double accountBalance;
    
    
    public User(int id, String email, String name, String phoneno, byte[] salt, byte[] secret, Enum<Role> role, Timestamp timestamp, double accountBalance) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneno = phoneno;
        this.salt = salt;
        this.secret = secret;
        this.role = role;
        this.timestamp = timestamp;
        this.accountBalance = accountBalance;
    }
    
    public int getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getName() {
        return name;
    }
    
    public String getPhoneno() {
        return phoneno;
    }
    
    public Enum<Role> getRole() {
        return role;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    public double getAccountBalance() {
        return accountBalance;
    }
    
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneno='" + phoneno + '\'' +
                ", role=" + role +
                ", timestamp=" + timestamp +
                ", accountBalance=" + accountBalance +
                '}';
    }
}
