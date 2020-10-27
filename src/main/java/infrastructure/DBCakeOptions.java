package infrastructure;

import domain.items.CakeOptions;
import domain.items.Option;

import java.sql.*;
import java.util.HashMap;

public class DBCakeOptions {
    
    private final Database db;
    
    public DBCakeOptions(Database db) {
        this.db = db;
    }
    
    private HashMap<String, Integer> getAllCakeBottoms(){
        try (Connection conn = db.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM CakeBottoms;");
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
    
    public CakeOptions findAllCakeOptions() {
        return new CakeOptions(getAllCakeBottoms(), getAllCakeToppings());
    }
    

    public Option createCakeOption(Option option) {
        int id;
        try (Connection conn = db.getConnection()) {
            var ps =
                    conn.prepareStatement(
                            "INSERT INTO CakeOptions (type, name, price) " +
                                    "VALUE (?,?,?);",
                            Statement.RETURN_GENERATED_KEYS);
            
            ps.setString(1, option.getType());
            ps.setString(2,option.getName());
            ps.setDouble(3, option.getPrice());
            
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
        try (Connection conn = db.getConnection()) {
            var ps =
                    conn.prepareStatement(
                            "DELETE FROM CakeOptions WHERE id = ?;");
            
            ps.setInt(1, id);
            
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
        return true;
    }
    
    


    

}
