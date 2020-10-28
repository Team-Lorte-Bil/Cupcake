package infrastructure;

import domain.items.Option;
import domain.user.User;
import domain.user.UserExists;

import java.sql.*;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class DBUser {
    
    private final Database db;
    
    public DBUser(Database db) {
        this.db = db;
    }
    
    public ArrayList<User> getAllUsers(){
        try (Connection conn = db.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Users;");
            ResultSet rs = s.executeQuery();
            ArrayList<User> tmpList = new ArrayList<>();
            
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
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public User createUser(String name, String password, String email, int phoneno, double accountBalance){
        int id;
        byte[] userSalt = User.generateSalt();
        byte[] userSecret = User.calculateSecret(userSalt, password);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        User tmpUser;
    
        try (Connection conn = db.getConnection()) {
        
            
                PreparedStatement ps =
                        conn.prepareStatement(
                                "INSERT INTO Users (email, name, phoneno, salt, secret, role, accountBalance, createdAt) " +
                                        "VALUE (?,?,?,?,?,?,?,?);",
                                Statement.RETURN_GENERATED_KEYS);
        
            ps.setString(1,email);
            ps.setString(2,name);
            ps.setInt(3,phoneno);
            ps.setBytes(4,userSalt);
            ps.setBytes(5,userSecret);
            ps.setString(6, String.valueOf(User.Role.User));
            ps.setDouble(7,accountBalance);
            ps.setTimestamp(8,timestamp);
        
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println(e);
            }
        
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
                System.out.println(timestamp);
                tmpUser = new User(id, email,name,  phoneno,
                        userSalt, userSecret, User.Role.User,
                        timestamp,
                        accountBalance);
            } else {
                tmpUser = null;
                throw new UserExists(name);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return tmpUser;
    }
    
    private HashMap<String, Integer> getAllCakeToppings(){
        try (Connection conn = db.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM CakeToppings;");
            ResultSet rs = s.executeQuery();
            HashMap<String, Integer> tmpList = new HashMap<>();
            
            while(rs.next()) {
                String name = rs.getString("name");
                int price = (int) rs.getDouble("price");
                
                tmpList.put(name,price);
            }
            return tmpList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    
    public Option createCakeOption(Option option) {
        int id;
        PreparedStatement ps;
        
        try (Connection conn = db.getConnection()) {
            
            if(option.getType().equalsIgnoreCase("bottom")){
                ps =
                        conn.prepareStatement(
                                "INSERT INTO CakeBottoms (name, price) " +
                                        "VALUE (?,?);",
                                Statement.RETURN_GENERATED_KEYS);
            } else {
                ps =
                        conn.prepareStatement(
                                "INSERT INTO CakeToppings (name, price) " +
                                        "VALUE (?,?);",
                                Statement.RETURN_GENERATED_KEYS);
            }
            
            ps.setString(1,option.getName());
            ps.setDouble(2, option.getPrice());
            
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println(e);
            }
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new Exception("Eksisterer allerde");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new Option(id,option.getName(),option.getType(),option.getPrice());
    }
    
    public boolean deleteCakeOption(Option option) {
        int id = option.getId();
        PreparedStatement ps;
        
        try (Connection conn = db.getConnection()) {
            if(option.getType().equalsIgnoreCase("bottom")){
                ps = conn.prepareStatement(
                        "DELETE FROM CakeBottoms WHERE id = ?;");
            } else {
                ps = conn.prepareStatement(
                        "DELETE FROM CakeToppings WHERE id = ?;");
            }
            
            ps.setInt(1, id);
            
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println(e);
            }
            
            if (ps.getUpdateCount() == 1) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    
    
    
}