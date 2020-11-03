package infrastructure;

import api.Utils;
import domain.user.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DBUser implements UserRepository {
    
    public DBUser() {
    }
    
    /**
     * @return List of User objects
     * @see User
     */
    public List<User> getAllUsers(){
        return (List<User>) findAllUsers();
    }
    
    /**
     * Checks if the user exists in the database and then compares the provided password with the stored password.
     *
     * @query SELECT * FROM Users WHERE email=usrEmail
     *
     * @param usrEmail User e-mail
     * @param usrPassword User password
     * @return User object
     *
     * @see User
     *
     * @throws InvalidPassword If password is wrong or user does not exist.
     */
    public User checkLogin(String usrEmail, String usrPassword) throws InvalidPassword {
        User tmpUser;
        
        usrEmail = Utils.encodeHtml(usrEmail);
        usrPassword = Utils.encodeHtml(usrPassword);
    
        
        try (Connection conn = Database.getConnection()) {
            try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE email=?;")) {
            
            
            ps.setString(1,usrEmail);
    
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()) {
                int id = rs.getInt(1);
                String email = rs.getString(2);
                String name = rs.getString(3);
                int phoneno = rs.getInt(4);
                byte[] salt = rs.getBytes(5);
                byte[] secret = rs.getBytes(6);
                Enum<User.Role> role = User.Role.valueOf(rs.getString(7));
                Timestamp createdAt = rs.getTimestamp(8);
                double accountBalance = rs.getDouble(9);
                byte[] providedSecret = User.calculateSecret(salt, usrPassword);
                
                
                if(! Arrays.equals(providedSecret, secret)){
                    throw new InvalidPassword("Forkert kode! Prøv igen.");
                }
    
                tmpUser = new User(id,email,name,phoneno,salt,secret,role,createdAt,accountBalance);
                
                return tmpUser;
            } else {
                throw new InvalidPassword("Brugeren eksisterer ikke!");
            }
        }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * @query DELETE FROM Users WHERE id=userId
     * @param userId User ID
     */
    public void deleteUser(int userId) {
        try (Connection conn = Database.getConnection()) {
            
            try(PreparedStatement ps = conn.prepareStatement("DELETE FROM Users WHERE id = ?;")){
            
            ps.setInt(1, userId);
            ps.executeUpdate();
    
            ps.getUpdateCount();
        }} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * @query UPDATE Users SET Users.accountBalance=newBalance WHERE id=userId
     * @param userId User ID
     * @param newBalance Account balance to be set
     */
    public void changeBalance(int userId, double newBalance) {
        try (Connection conn = Database.getConnection()) {
        
            try(PreparedStatement ps = conn.prepareStatement("UPDATE Users SET Cupcake.Users.accountBalance=? WHERE id=?")){
        
        
            ps.setDouble(1, newBalance);
            ps.setInt(2, userId);
            ps.executeUpdate();
            
        }} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * @query SELECT * FROM Users WHERE id=id
     * @param id User ID
     * @return User object
     * @see User
     *
     * @throws UserNotFound If user is not found in Database
     */
    @Override
    public User findUser(int id) throws UserNotFound {
        User tmpUser;
    
        try (Connection conn = Database.getConnection()) {
            try(PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE id=?;")){
            ps.setInt(1,id);
        
            ResultSet rs = ps.executeQuery();
        
            if(rs.next()) {
                int foundId = rs.getInt(1);
                String email = rs.getString(2);
                String name = rs.getString(3);
                int phoneno = rs.getInt(4);
                byte[] salt = rs.getBytes(5);
                byte[] secret = rs.getBytes(6);
                Enum<User.Role> role = User.Role.valueOf(rs.getString(7));
                Timestamp createdAt = rs.getTimestamp(8);
                double accountBalance = rs.getDouble(9);
            
                tmpUser = new User(foundId,email,name,phoneno,salt,secret,role,createdAt,accountBalance);
            
                return tmpUser;
            }
            throw new UserNotFound(id);
        }} catch (SQLException | UserNotFound e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * @query SELECT * FROM Users
     * @return Iterable of Users
     */
    @Override
    public Iterable<User> findAllUsers() {
        try (Connection conn = Database.getConnection()) {
            try(PreparedStatement s = conn.prepareStatement("SELECT * FROM Users;")){
                ResultSet rs = s.executeQuery();
                List<User> tmpList = new ArrayList<>();
            
                while(rs.next()) {
                    int id = rs.getInt(1);
                    String email = rs.getString(2);
                    String name = rs.getString(3);
                    int phoneno = rs.getInt(4);
                    byte[] salt = rs.getBytes(5);
                    byte[] secret = rs.getBytes(6);
                    Enum<User.Role> role = User.Role.valueOf(rs.getString(7));
                    Timestamp createdAt = rs.getTimestamp(8);
                    double accountBalance = rs.getDouble(9);
                
                    User tmpUser = new User(id,email,name,phoneno,salt,secret,role,createdAt,accountBalance);
                
                    tmpList.add(tmpUser);
                }
                return tmpList;
            }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Creates a new user and inserts it into the database
     *
     * @query INSERT INTO Users (email, name, phoneno, salt, secret, role, accountBalance, createdAt)
     *
     * @param name Users full name
     * @param password Users password
     * @param email Users email
     * @param phoneno Users phone number
     * @param accountBalance Account balance
     * @param role Role:
     *             Admin
     *             User
     * @see User.Role
     *
     * @return User object
     * @see User
     *
     * @throws UserExists if User already exists.
     */
    @Override
    public User createUser(String name, String password, String email, int phoneno, double accountBalance, String role) throws UserExists {
        name = Utils.encodeHtml(name);
        password = Utils.encodeHtml(password);
        email = Utils.encodeHtml(email);
        role = Utils.encodeHtml(role);
        int id;
        byte[] userSalt = User.generateSalt();
        byte[] userSecret = User.calculateSecret(userSalt, password);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO Users (email, name, phoneno, salt, secret, role, accountBalance, createdAt) " +
                    "VALUE (?,?,?,?,?,?,?,?);";
        
            try(PreparedStatement ps = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS)){
            
                ps.setString(1,email);
                ps.setString(2,name);
                ps.setInt(3,phoneno);
                ps.setBytes(4,userSalt);
                ps.setBytes(5,userSecret);
                ps.setString(6, User.Role.valueOf(role).name());
                ps.setDouble(7,accountBalance);
                ps.setTimestamp(8,timestamp);
            
                ps.executeUpdate();
            
                ResultSet rs = ps.getGeneratedKeys();
                if (rs.next()) {
                    id = rs.getInt(1);
                    System.out.println(timestamp);
                    return new User(id, email,name,  phoneno,
                            userSalt, userSecret, User.Role.valueOf(role),
                            timestamp,
                            accountBalance);
                } else {
                    throw new UserExists(name);
                }
            }} catch (UserExists | SQLException e) {
            System.out.println(e);
        }
        return null;
    }
}