package domain.user;

/**
 * The purpose of User is to...
 * @author kasper
 */
public class User {
    enum Role {
        User,
        Admin
    }
    
    private final int id; // just used to demo retrieval of autogen keys in UserMapper
    private final String email;
    private final String password; // Should be hashed and secured
    private Enum<Role> role;
    
    
    
    public User(int id, String email, String password, Enum<Role> role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = Role.User;
    }
    
    public int getId() {
        return id;
    }
    
    public String getEmail() {
        return email;
    }
    
    public String getPassword() {
        return password;
    }
    
    public Enum<Role> getRole() {
        return role;
    }
    
    public void setRole(Enum<Role> role) {
        this.role = role;
    }
}
