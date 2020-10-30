package infrastructure;

import api.Utils;
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
    
    public ArrayList<Order> getAllOrders() {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Orders;");
            ResultSet rs = s.executeQuery();
            ArrayList<Order> tmpList = new ArrayList<>();
            
            while (rs.next()) {
                int id = rs.getInt(1);
                int userId = rs.getInt(2);
                String comment = rs.getString(3);
                Timestamp timestamp = rs.getTimestamp(4);
                boolean paid = rs.getBoolean(5);
                boolean completed = rs.getBoolean(6);
                
                User tmpUsr = new DBUser(db).getUserFromId(userId);
                Order tmpOrder = new Order(id, tmpUsr, comment, timestamp, paid, completed, getCakesOnOrder(id));
    
                System.out.println("Cakes added: " + tmpOrder.getCakes());
                
                tmpList.add(tmpOrder);
            }
            return tmpList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    public HashMap<Cake, Integer> getCakesOnOrder(int orderId) {
        HashMap<Cake, Integer> cakeList = new HashMap<>();
        
        try (Connection conn = Database.getConnection()) {
            String sqlQuery = "SELECT CakesOnOrder.orderId, Cupcake.CakesOnOrder.quantity, \n" +
                    "CakeBottoms.`name` as \"bottomName\", CakeBottoms.price as \"bottomPrice\",\n" +
                    "CakeToppings.`name` as \"toppingName\", CakeToppings.price as \"toppingPrice\"\n" +
                    "FROM CakesOnOrder\n" +
                    " INNER JOIN CakeBottoms ON CakesOnOrder.bottomId = CakeBottoms.id\n" +
                    " INNER JOIN CakeToppings ON CakesOnOrder.toppingId = CakeToppings.id";
            
            PreparedStatement s = conn.prepareStatement(sqlQuery);
            ResultSet rs = s.executeQuery();
            
            while (rs.next()) {
                if (orderId == rs.getInt("orderId")) {
                    
                    int quantity = rs.getInt("quantity");
                    String bottomName = rs.getString("bottomName");
                    String toppingName = rs.getString("toppingName");
                    double cakePrice = rs.getDouble("toppingPrice") + rs.getDouble("bottomPrice");
                    
                    
                    Cake tmpCake = new Cake(bottomName, toppingName, (int) cakePrice);
                    
                    cakeList.put(tmpCake, quantity);
                }
            }
    
            System.out.println("Cakes added to map: " + cakeList);
            
            return cakeList;
            
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    public Order createOrder(User user, HashMap<Cake, Integer> cakes, String comment) {
        Order tmpOrder;
        
        int orderId = 0;
        String orderComment = Utils.encodeHtml(comment);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        boolean paid = false; //TODO: Implement user account balance usage.
        boolean completed = false;
        
        
        try (Connection conn = Database.getConnection()) {
            
            PreparedStatement ps =
                    conn.prepareStatement(
                            "INSERT INTO Orders (userId, comment, createdAt, paid, completed) " +
                                    "VALUE (?,?,?,?,?);",
                            Statement.RETURN_GENERATED_KEYS);
            
            ps.setInt(1, user.getId());
            ps.setString(2, orderComment);
            ps.setTimestamp(3, timestamp);
            ps.setBoolean(4, paid);
            ps.setBoolean(5, completed);
            
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new RuntimeException(e);
            }
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
            
            createCakesOnOrder(orderId, cakes);
            System.out.println("Creating cakes on order: " + orderId);
            
            tmpOrder = new Order(orderId, user, comment, timestamp, paid, completed);
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        
        return tmpOrder;
    }
    
    public HashMap<Cake, Integer> createCakesOnOrder(int orderId, HashMap<Cake, Integer> cakes) {
        int cakeno = cakes.size();
        System.out.println("Antal kager som skal indsættes: " + cakeno);
        for (Map.Entry<Cake, Integer> c : cakes.entrySet()) {
            System.out.println("cakeno: " + cakeno);
            try (Connection conn = Database.getConnection()) {
            
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
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            cakeno--;
        }
        return cakes;
    }
    
    private HashMap<String, Integer> getAllCakeToppings() {
        try (Connection conn = Database.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM CakeToppings;");
            ResultSet rs = s.executeQuery();
            HashMap<String, Integer> tmpList = new HashMap<>();
            
            while (rs.next()) {
                String name = rs.getString("name");
                int price = (int) rs.getDouble("price");
                
                tmpList.put(name, price);
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
        return new Option(id, option.getName(), option.getType(), option.getPrice());
    }
    
    public boolean deleteOrder(int orderId) {
        try (Connection conn = Database.getConnection()) {
        
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Orders WHERE id=?;");
        
        
            ps.setInt(1,orderId);
        
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
    
    public void markDone(int orderId) {
        try (Connection conn = Database.getConnection()) {
            
            PreparedStatement ps = conn.prepareStatement(
                    "UPDATE Orders SET completed=1, paid = 1 WHERE id=?;");
            
            
            ps.setInt(1,orderId);
            
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new RuntimeException(e);
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public double getTotalSales(LinkedHashMap<Order, Double> orders){
        double sum = 0.0;
        
        for(Map.Entry<Order, Double> entry: orders.entrySet()){
            if(entry.getKey().isCompleted()){
                sum += entry.getValue();
            }
        }
        
        return sum;
    }
    
    public LinkedHashMap<Order, Double> getAllOrdersMap() {
        LinkedHashMap<Order, Double> tmpMap = new LinkedHashMap<>();
        try (Connection conn = Database.getConnection()) {
            PreparedStatement s = conn.prepareStatement("SELECT * FROM Orders ORDER BY id DESC;");
            ResultSet rs = s.executeQuery();
        
            while (rs.next()) {
                int id = rs.getInt(1);
                int userId = rs.getInt(2);
                String comment = rs.getString(3);
                comment = Utils.encodeHtml(comment);
                Timestamp timestamp = rs.getTimestamp(4);
                boolean paid = rs.getBoolean(5);
                boolean completed = rs.getBoolean(6);
            
                User tmpUsr = new DBUser(db).getUserFromId(userId);
                Order tmpOrder = new Order(id, tmpUsr, comment, timestamp, paid, completed, getCakesOnOrder(id));
            
                String sql = "SELECT\n" +
                        "\tOrders.id,\n" +
                        "\t((CakeBottoms.price + CakeToppings.price) * CakesOnOrder.quantity) AS \"price\"\n" +
                        "FROM\n" +
                        "\tOrders\n" +
                        "\tINNER JOIN\n" +
                        "\tCakesOnOrder\n" +
                        "\tON \n" +
                        "\t\tOrders.id = CakesOnOrder.orderId\n" +
                        "\tINNER JOIN\n" +
                        "\tCakeBottoms\n" +
                        "\tON \n" +
                        "\t\tCakesOnOrder.bottomId = CakeBottoms.id\n" +
                        "\tINNER JOIN\n" +
                        "\tCakeToppings\n" +
                        "\tON\n" +
                        "\t\tCakesOnOrder.toppingId = CakeToppings.id\n" +
                        "WHERE Orders.id = ?\n" +
                        "GROUP BY\n" +
                        "\tOrders.id,\n" +
                        "\tCakesOnOrder.quantity,\n" +
                        "\tCakeBottoms.id,\n" +
                        "\tCakeToppings.id";
            
                PreparedStatement ps = conn.prepareStatement(sql);
                ps.setInt(1, id);
                ResultSet rss = ps.executeQuery();
            
                double price = 0.0;
            
                while (rss.next()) {
                    price += rss.getDouble(2);
                }
            
                tmpMap.put(tmpOrder, price);
            }
            return tmpMap;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}