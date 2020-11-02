package domain.user;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.sql.Timestamp;

public class User {
    private static final int PASSWORD_ITTERATIONS = 65536;
    private static final int PASSWORD_LENGTH = 256; //32 bytes
    private static final SecretKeyFactory PASSWORD_FACTORY;

    static {
        SecretKeyFactory factory = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        PASSWORD_FACTORY = factory;
    }

    public enum Role {
        User,
        Admin
    }
    
    private final int id;
    private final String email;
    private final String name;
    private final int phoneno;
    private final byte[] salt;
    private final byte[] secret;
    private final Enum<Role> role;
    private final Timestamp createdAt;
    private double accountBalance;
    
    
    public User(int id, String email, String name, int phoneno, byte[] salt, byte[] secret, Enum<Role> role, Timestamp createdAt, double accountBalance) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.phoneno = phoneno;
        this.salt = salt;
        this.secret = secret;
        this.role = role;
        this.createdAt = createdAt;
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
    
    public int getPhoneno() {
        return phoneno;
    }
    
    public Enum<Role> getRole() {
        return role;
    }
    
    public Timestamp getCreatedAt() {
        return createdAt;
    }
    
    public double getAccountBalance() {
        return accountBalance;
    }
    
    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }
    
    public boolean isAdmin(){
        return this.role.equals(Role.Admin);
    }
    
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", phoneno='" + phoneno + '\'' +
                ", role=" + role +
                ", createdAt=" + createdAt +
                ", accountBalance=" + accountBalance +
                '}';
    }
    public static byte[] generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        return salt;
    }
    public static byte[] calculateSecret(byte[] salt, String password) {
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt,
                PASSWORD_ITTERATIONS,
                PASSWORD_LENGTH);
        try {
            return PASSWORD_FACTORY.generateSecret(spec).getEncoded();
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
    }

}
