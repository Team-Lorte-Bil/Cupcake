package infrastructure;

import domain.items.CakeOption;
import domain.items.Option;

import java.sql.*;
import java.util.HashMap;

public class DBCakeOptions extends Database {
    
    public CakeOption findAllCakeOptions() {
        try (Connection conn = getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM CakeOptions;");
            ResultSet rs = s.executeQuery();
            HashMap<String, Integer> bottoms = new HashMap<>();
            HashMap<String, Integer> toppings = new HashMap<>();
            
            while(rs.next()) {
                String type = rs.getString("type");
                String name = rs.getString("name");
                int price = (int) rs.getDouble("price");
                
                if(type.equalsIgnoreCase("bottom")){
                    bottoms.put(name,price);
                } else {
                    toppings.put(name,price);
                }
            }
            return new CakeOption(bottoms,toppings);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    

    public Option createCakeOption(Option option) {
        int id;
        try (Connection conn = getConnection()) {
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
    
    
    


    

}
