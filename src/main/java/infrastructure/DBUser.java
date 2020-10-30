package infrastructure;

import api.Utils;
import domain.items.Option;
import domain.user.InvalidPassword;
import domain.user.User;
import domain.user.UserExists;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class DBUser {
    
    private final Database db;
    
    public DBUser(Database db) {
        this.db = db;
    }
    
    public ArrayList<User> getAllUsers(){
        try (Connection conn = Database.getConnection()) {
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
    
    public User checkLogin(String usrEmail, String usrPassword) throws InvalidPassword {
        User tmpUser = null;
        
        usrEmail = Utils.encodeHtml(usrEmail);
        usrPassword = Utils.encodeHtml(usrPassword);
    
        
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE email=?;");
            ps.setString(1,usrEmail);
    
            ResultSet rs = ps.executeQuery();
    
            System.out.println("Provided mail: " + usrEmail);
            
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
    
                byte[] providedSecret = User.calculateSecret(salt, usrPassword);
                
                
                System.out.println("Provided: " + Arrays.toString(providedSecret));
                System.out.println("Correct: " + Arrays.toString(secret));
                
                if(! Arrays.equals(providedSecret, secret)){
                    throw new InvalidPassword();
                }
    
                tmpUser = new User(id,email,name,phoneno,salt,secret,role,createdAt,accountBalance);
                
                return tmpUser;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public User createUser(String name, String password, String email, int phoneno, double accountBalance, String role){
        name = Utils.encodeHtml(name);
        password = Utils.encodeHtml(password);
        email = Utils.encodeHtml(email);
        role = Utils.encodeHtml(role);
        int id;
        byte[] userSalt = User.generateSalt();
        byte[] userSecret = User.calculateSecret(userSalt, password);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        User tmpUser;
    
        try (Connection conn = Database.getConnection()) {
        
            
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
            ps.setString(6, User.Role.valueOf(role).name());
            ps.setDouble(7,accountBalance);
            ps.setTimestamp(8,timestamp);
        
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new RuntimeException(e);
            }
        
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
                System.out.println(timestamp);
                tmpUser = new User(id, email,name,  phoneno,
                        userSalt, userSecret, User.Role.valueOf(role),
                        timestamp,
                        accountBalance);
            } else {
                throw new UserExists(name);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return tmpUser;
    }
    private HashMap<String, Integer> getAllCakeToppings(){
        try (Connection conn = Database.getConnection()) {
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

            if (option.getType().equalsIgnoreCase("bottom")) {
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

            ps.setString(1, option.getName());
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
        return new Option(id, option.getName(), option.getType(), option.getPrice());
    }
    

    
    public boolean deleteUser(int userId) {
        try (Connection conn = Database.getConnection()) {
            
            PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM Users WHERE id = ?;");
            
            
            ps.setInt(1, userId);
            
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new RuntimeException(e);
            }
    
            return ps.getUpdateCount() == 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public User getUserFromId(int userId) {
        User tmpUser;
    
        try (Connection conn = Database.getConnection()) {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Users WHERE id=?;");
            ps.setInt(1,userId);
        
            ResultSet rs = ps.executeQuery();
            
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
            
                tmpUser = new User(id,email,name,phoneno,salt,secret,role,createdAt,accountBalance);
            
                return tmpUser;
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public void changeBalance(int userId, double newBalance) {
        try (Connection conn = Database.getConnection()) {
        
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Users SET Cupcake.Users.accountBalance=? WHERE id=?");
        
        
            ps.setDouble(1, newBalance);
            ps.setInt(2, userId);
        
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}