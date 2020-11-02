package infrastructure;

import api.Utils;
import domain.items.CakeOptions;
import domain.items.Option;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBCakeOptions {
    
    public DBCakeOptions(Database db) {
    }
    
    private List<Option> getAllCakeBottoms(){
        try (Connection conn = Database.getConnection()) {
            try(PreparedStatement s = conn.prepareStatement("SELECT * FROM CakeBottoms;")){
            ResultSet rs = s.executeQuery();
            List<Option> tmpList = new ArrayList<>();
            
            while(rs.next()) {
                String name = rs.getString("name");
                int price = (int) rs.getDouble("price");
                
                name = Utils.encodeHtml(name);
                
                tmpList.add(new Option(0,name, "bottom", price));
            }
            return tmpList;
        }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    private List<Option> getAllCakeToppings(){
        try (Connection conn = Database.getConnection()) {
            try(PreparedStatement s = conn.prepareStatement("SELECT * FROM CakeToppings;")){
            ResultSet rs = s.executeQuery();
            List<Option> tmpList = new ArrayList<>();
            
            while(rs.next()) {
                String name = rs.getString("name");
                int price = (int) rs.getDouble("price");
                
                name = Utils.encodeHtml(name);
                
                tmpList.add(new Option(0,name, "topping", price));
            }
            return tmpList;
        }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
        
    }
    
    public CakeOptions findAllCakeOptions() {
        return new CakeOptions(getAllCakeBottoms(), getAllCakeToppings());
    }
    
    
    public Option createCakeOption(Option option) {
        int id;
        PreparedStatement ps;
        
        try (Connection conn = Database.getConnection()) {
            
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
                throw new RuntimeException(e);
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
        
        try (Connection conn = Database.getConnection()) {
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
                throw new RuntimeException(e);
            }
    
            return ps.getUpdateCount() == 1;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public int getToppingIdFromName(String topping) {
        topping = Utils.encodeHtml(topping);
        try(Connection conn = Database.getConnection()){
            String sqlQuery = "SELECT id FROM CakeToppings WHERE name=?";
            
            try(PreparedStatement s = conn.prepareStatement(sqlQuery)){
            s.setString(1,topping);
            ResultSet rs = s.executeQuery();
            
            if(rs.next()){
                return rs.getInt(1);
            }
            
        }} catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }
    
    public int getBottomIdFromName(String bottom) {
        bottom = Utils.encodeHtml(bottom);
        try(Connection conn = Database.getConnection()){
            String sqlQuery = "SELECT id FROM CakeBottoms WHERE name=?";
            
            try(PreparedStatement s = conn.prepareStatement(sqlQuery)){
            s.setString(1,bottom);
            ResultSet rs = s.executeQuery();
            
            if(rs.next()) return rs.getInt(1);
            
        }} catch (SQLException e){
            throw new RuntimeException(e);
        }
        return 0;
    }
    
    public List<Option> getAllCakeOptions() {
        List<Option> cakeOptions = new ArrayList<>();
        try(Connection conn = Database.getConnection()){
            String toppingQuery = "SELECT * FROM CakeToppings;";
            String bottomQuery = "SELECT * FROM CakeBottoms;";
        
            try(PreparedStatement s = conn.prepareStatement(toppingQuery)){
            ResultSet toppingRs = s.executeQuery();
        
            while(toppingRs.next()){
                int id = toppingRs.getInt(1);
                String name = toppingRs.getString(2);
                int price = (int) toppingRs.getDouble(3);
                String type = "topping";
                
                Option option = new Option(id, name, type, price);
                cakeOptions.add(option);
            }
    
            try(PreparedStatement ps = conn.prepareStatement(bottomQuery)){
            ResultSet bottomRs = ps.executeQuery();
    
            while(bottomRs.next()){
                int id = bottomRs.getInt(1);
                String name = bottomRs.getString(2);
                int price = (int) bottomRs.getDouble(3);
                String type = "bottom";
        
                Option option = new Option(id, name, type, price);
                cakeOptions.add(option);
            }
            
            return cakeOptions;
        
        }}} catch (SQLException e){
            throw new RuntimeException(e);
        }
        
    }
}
