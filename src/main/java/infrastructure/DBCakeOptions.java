package infrastructure;

import api.Utils;
import domain.items.CakeOptions;
import domain.items.Option;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBCakeOptions {
    
    private final Database db;
    
    public DBCakeOptions(Database db) {
    this.db = db;
    }
    
    private List<Option> loadOptions(ResultSet rs, String type) throws SQLException {
        List<Option> tmpList = new ArrayList<>();
    
        while(rs.next()) {
            String name = rs.getString("name");
            int price = (int) rs.getDouble("price");
        
            name = Utils.encodeHtml(name);
        
            tmpList.add(new Option(0,name, type, price));
        }
        
        return tmpList;
    }
    
    /**
     * @return List of Cake bottoms
     * @see Option
     */
    private List<Option> getAllCakeBottoms(){
        List<Option> tmpList = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            try(PreparedStatement s = conn.prepareStatement("SELECT * FROM CakeBottoms;")){
            ResultSet rs = s.executeQuery();
            
            return loadOptions(rs, "bottom");
        }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return tmpList;
    }
    
    /**
     * @return List of Cake toppings
     * @see Option
     */
    private List<Option> getAllCakeToppings(){
        List<Option> tmpList = new ArrayList<>();
        try (Connection conn = db.getConnection()) {
            try(PreparedStatement s = conn.prepareStatement("SELECT * FROM CakeToppings;")){
            ResultSet rs = s.executeQuery();
            
            return loadOptions(rs, "topping");
        }} catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return tmpList;
    }
    
    /**
     * @return CakeOptions object
     * @see CakeOptions
     */
    public CakeOptions findAllCakeOptions() {
        return new CakeOptions(getAllCakeBottoms(), getAllCakeToppings());
    }
    
    
    /**
     * @param option Option object for creation in Database
     * @return created Option object
     * @see Option
     */
    public Option createCakeOption(Option option) {
        int id;
        String table;
    
        if(option.getType().equalsIgnoreCase("bottom")) {
            table = "CakeBottoms";
        } else {
            table = "CakeToppings";
        }
        
        
        String sql = "INSERT INTO " + table + " (name, price) VALUE (?,?);";
        
        try (Connection conn = db.getConnection()) {
            
            try(PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)){
            
            ps.setString(1,option.getName());
            ps.setDouble(2, option.getPrice());
            
            ps.executeUpdate();
            
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);
            } else {
                throw new Exception("Eksisterer allerde");
            }
                return new Option(id,option.getName(),option.getType(),option.getPrice());
        }} catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
    
    /**
     * @param id Option ID
     * @param type Option Type:
     *             Bottom
     *             Topping
     * @return true if deleted
     */
    public boolean deleteCakeOption(int id, String type) {
        String table;
        boolean returnVal = false;
    
        if(type.equalsIgnoreCase("bottom")) {
            table = "CakeBottoms";
        } else {
            table = "CakeToppings";
        }
    
    
        String sql = "DELETE FROM " + table + " WHERE id = ?;";
        
        try (Connection conn = db.getConnection()) {
            try(PreparedStatement ps = conn.prepareStatement(sql)){
            
            ps.setInt(1, id);
            ps.executeUpdate();
    
            returnVal = ps.getUpdateCount() == 1;
            
        }} catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return returnVal;
    }
    
    
    /**
     * @param topping Topping name
     * @return Id of topping
     */
    public int getToppingIdFromName(String topping) {
        topping = Utils.encodeHtml(topping);
        try(Connection conn = db.getConnection()){
            String sqlQuery = "SELECT id FROM CakeToppings WHERE name=?";
            
            try(PreparedStatement s = conn.prepareStatement(sqlQuery)){
            s.setString(1,topping);
            ResultSet rs = s.executeQuery();
            
            if(rs.next()) return rs.getInt(1);
            
        }} catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    /**
     * @param bottom Bottom name
     * @return Id of bottom
     */
    public int getBottomIdFromName(String bottom) {
        bottom = Utils.encodeHtml(bottom);
        try(Connection conn = db.getConnection()){
            String sqlQuery = "SELECT id FROM CakeBottoms WHERE name=?";
            
            try(PreparedStatement s = conn.prepareStatement(sqlQuery)){
            s.setString(1,bottom);
            ResultSet rs = s.executeQuery();
            
            if(rs.next()) return rs.getInt(1);
            
        }} catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return 0;
    }
    
    /**
     * @return List of all options
     * @see Option
     */
    public List<Option> getAllCakeOptions() {
        List<Option> cakeOptions = new ArrayList<>();
        try(Connection conn = db.getConnection()){
            String toppingQuery = "SELECT * FROM CakeToppings;";
            String bottomQuery = "SELECT * FROM CakeBottoms;";
        
            try(PreparedStatement s = conn.prepareStatement(toppingQuery)) {
                ResultSet toppingRs = s.executeQuery();
                for (Option o : loadOptions(toppingRs, "topping")) {
                    cakeOptions.add(new Option(o.getId(), o.getName(), o.getType(), o.getPrice()));
                }
            }
    
            try(PreparedStatement ps = conn.prepareStatement(bottomQuery)){
            ResultSet bottomRs = ps.executeQuery();
                for(Option o: loadOptions(bottomRs, "bottom")){
                    cakeOptions.add(new Option(o.getId(), o.getName(), o.getType(), o.getPrice()));
                }
            }
        } catch (SQLException e){
            System.out.println(e.getMessage());
        }
        return cakeOptions;
    }
}
