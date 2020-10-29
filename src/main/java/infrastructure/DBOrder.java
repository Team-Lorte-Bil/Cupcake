package infrastructure;

import domain.items.Cake;
import domain.items.Option;
import domain.order.Order;
import domain.user.User;

import java.sql.*;
import java.util.*;

public class DBOrder {
    
    private final Database db;
    
    public DBOrder(Database db) {
        this.db = db;
    }
    
    public ArrayList<Order> getAllOrders(){
        try (Connection conn = db.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Orders;");
            ResultSet rs = s.executeQuery();
            ArrayList<Order> tmpList = new ArrayList<>();
            
            while(rs.next()) {
                int id = rs.getInt(1);
                int userId = rs.getInt(2);
                String comment = rs.getString(3);
                Timestamp timestamp = rs.getTimestamp(4);
                boolean paid = rs.getBoolean(5);
                boolean completed = rs.getBoolean(6);
                
                User tmpUsr = new DBUser(db).getUserFromId(userId);
                Order tmpOrder = new Order(id,tmpUsr,comment,timestamp,paid,completed);
                tmpOrder.addCakes(getCakesOnOrder(id));
                
                tmpList.add(tmpOrder);
            }
            return tmpList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public HashMap<Cake, Integer> getCakesOnOrder(int orderId){
        HashMap<Cake, Integer> cakeList = new HashMap<Cake, Integer>();
        
        try(Connection conn = db.getConnection()){
            String sqlQuery = "SELECT CakesOnOrder.orderId, Cupcake.CakesOnOrder.quantity, \n" +
                    "CakeBottoms.`name` as \"bottomName\", CakeBottoms.price as \"bottomPrice\",\n" +
                    "CakeToppings.`name` as \"toppingName\", CakeToppings.price as \"toppingPrice\"\n" +
                    "FROM CakesOnOrder\n" +
                    " INNER JOIN CakeBottoms ON CakesOnOrder.bottomId = CakeBottoms.id\n" +
                    " INNER JOIN CakeToppings ON CakesOnOrder.toppingId = CakeToppings.id";
            
            PreparedStatement s = conn.prepareStatement(sqlQuery);
            ResultSet rs = s.executeQuery();
            
            while(rs.next()){
                if(orderId == rs.getInt("orderId")){
                    
                    int quantity = rs.getInt("quantity");
                    String bottomName = rs.getString("bottomName");
                    String toppingName = rs.getString("toppingName");
                    double cakePrice = rs.getDouble("toppingPrice") + rs.getDouble("bottomPrice");
                    
                    
                    Cake tmpCake = new Cake(bottomName, toppingName, (int) cakePrice);
                    
                    cakeList.put(tmpCake, quantity);
                }
            }
            
            return cakeList;
            
        } catch (SQLException e){
            throw new RuntimeException(e);
        }
    }
    
    
    
    public Order createOrder(User user, HashMap<Cake, Integer> cakes, String comment){
        Order tmpOrder = null;
        
        int orderId = 0;
        User tmpUser = user;
        String orderComment = comment;
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        boolean paid = false; //TODO: Implement user account balance usage.
        boolean completed = false;
        
    
        try (Connection conn = db.getConnection()) {
            
                PreparedStatement ps =
                        conn.prepareStatement(
                                "INSERT INTO Orders (userId, comment, createdAt, paid, completed) " +
                                        "VALUE (?,?,?,?,?);",
                                Statement.RETURN_GENERATED_KEYS);
        
            ps.setInt(1,tmpUser.getId());
            ps.setString(2,orderComment);
            ps.setTimestamp(3,timestamp);
            ps.setBoolean(4,paid);
            ps.setBoolean(5,completed);
        
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new RuntimeException(e);
            }
        
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
            
            System.out.println("Creating cakes on order: " + orderId);
    
            tmpOrder = new Order(orderId, tmpUser, comment, timestamp, paid, completed);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    
        createCakesOnOrder(orderId, cakes);
        return tmpOrder;
    }
    
    public HashMap<Cake, Integer> createCakesOnOrder(int orderId, HashMap<Cake, Integer> cakes){
        int cakeno = cakes.size();
        System.out.println("Antal kager som skal indsættes: " + cakeno);
            for(Map.Entry<Cake, Integer> c: cakes.entrySet()){
                System.out.println("cakeno: " + cakeno);
                try (Connection conn = db.getConnection()) {
                    
                    String sql = "INSERT INTO CakesOnOrder (orderId, bottomId, toppingId, quantity) VALUE (?,?,?,?);";
        
                    PreparedStatement ps =
                            conn.prepareStatement(
                                    sql,
                                    Statement.RETURN_GENERATED_KEYS);
                    
                    int cakeToppingId = new DBCakeOptions(db).getToppingIdFromName(c.getKey().getTopping());
                    int cakeBottomId = new DBCakeOptions(db).getBottomIdFromName(c.getKey().getBottom());
    
                    System.out.println("Trying to insert cake...");
                    System.out.println("orderID: " + orderId);
                    System.out.println("bottomId: " + cakeBottomId);
                    System.out.println("toppingId: " + cakeToppingId);
                    System.out.println("quantity: " + c.getValue());
        
                    ps.setInt(1, orderId);
                    ps.setInt(2, cakeBottomId);
                    ps.setInt(3, cakeToppingId);
                    ps.setInt(4, c.getValue());
    
                    try {
                        ps.executeUpdate();
                    } catch (SQLIntegrityConstraintViolationException e) {
                        throw new RuntimeException(e);
                    }
                } catch (SQLException e){
                    throw new RuntimeException(e);
                }
                cakeno--;
            }
        return cakes;
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
    
    public boolean deleteUser(int userId) {
        try (Connection conn = db.getConnection()) {
            
            PreparedStatement ps = conn.prepareStatement(
                        "DELETE FROM Users WHERE id = ?;");
            
            
            ps.setInt(1, userId);
            
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                System.out.println(e);
            }
    
            return ps.getUpdateCount() == 1;
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}