package infrastructure;

import api.Utils;
import domain.items.CakeOptions;
import domain.items.Option;

import java.sql.*;
import java.util.ArrayList;
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
                
                name = Utils.encodeHtml(name);
                
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
                name = Utils.encodeHtml(name);
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
    
    public boolean deleteCakeOption(int id, String type) {
        PreparedStatement ps;
        
        try (Connection conn = db.getConnection()) {
            if(type.equalsIgnoreCase("bottom")){
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
    
    
    public int getToppingIdFromName(String topping) {
        topping = Utils.encodeHtml(topping);
        try(Connection conn = db.getConnection()){
            String sqlQuery = "SELECT id FROM CakeToppings WHERE name=?";
            
            PreparedStatement s = conn.prepareStatement(sqlQuery);
            s.setString(1,topping);
            ResultSet rs = s.executeQuery();
            
            while(rs.next()){
                return rs.getInt(1);
            }
            
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }
    
    public int getBottomIdFromName(String bottom) {
        bottom = Utils.encodeHtml(bottom);
        try(Connection conn = db.getConnection()){
            String sqlQuery = "SELECT id FROM CakeBottoms WHERE name=?";
            
            PreparedStatement s = conn.prepareStatement(sqlQuery);
            s.setString(1,bottom);
            ResultSet rs = s.executeQuery();
            
            while(rs.next()){
                return rs.getInt(1);
            }
            
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }
    
    public ArrayList<Option> getAllCakeOptions() {
        ArrayList<Option> cakeOptions = new ArrayList<>();
        try(Connection conn = db.getConnection()){
            String toppingQuery = "SELECT * FROM CakeToppings;";
            String bottomQuery = "SELECT * FROM CakeBottoms;";
        
            PreparedStatement s = conn.prepareStatement(toppingQuery);
            ResultSet toppingRs = s.executeQuery();
        
            while(toppingRs.next()){
                int id = toppingRs.getInt(1);
                String name = toppingRs.getString(2);
                int price = (int) toppingRs.getDouble(3);
                String type = "topping";
                
                Option option = new Option(id, name, type, price);
                cakeOptions.add(option);
            }
    
            s = conn.prepareStatement(bottomQuery);
            ResultSet bottomRs = s.executeQuery();
    
            while(bottomRs.next()){
                int id = bottomRs.getInt(1);
                String name = bottomRs.getString(2);
                int price = (int) bottomRs.getDouble(3);
                String type = "bottom";
        
                Option option = new Option(id, name, type, price);
                cakeOptions.add(option);
            }
            
            return cakeOptions;
        
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
        
    }
}
