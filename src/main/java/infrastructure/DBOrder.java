package infrastructure;

import api.Utils;
import domain.items.Cake;
import domain.order.NoOrderExists;
import domain.order.Order;
import domain.order.OrderRepository;
import domain.user.User;
import domain.user.UserNotFound;

import java.sql.*;
import java.util.*;

public class DBOrder implements OrderRepository {
    
    private final Database db;
    
    public DBOrder(Database db) {
        this.db = db;
    }
    
    public List<Order> getAllOrders() {
        try (Connection conn = Database.getConnection()) {
            try(PreparedStatement s = conn.prepareStatement("SELECT * FROM Orders;")){
            ResultSet rs = s.executeQuery();
            List<Order> tmpList = new ArrayList<>();
            
            while (rs.next()) {
                int id = rs.getInt(1);
                int userId = rs.getInt(2);
                String comment = rs.getString(3);
                Timestamp timestamp = rs.getTimestamp(4);
                boolean paid = rs.getBoolean(5);
                boolean completed = rs.getBoolean(6);
                
                User tmpUsr = new DBUser().findUser(userId);
                Order tmpOrder = new Order(id, tmpUsr, comment, timestamp, paid, completed, getCakesOnOrder(id));
                
                System.out.println("Cakes added: " + tmpOrder.getCakes());
                
                tmpList.add(tmpOrder);
            }
            return tmpList;
        }} catch (SQLException | UserNotFound e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Order.Item> getCakesOnOrder(int orderId) {
        List<Order.Item> cakeList = new ArrayList<>();
        
        try (Connection conn = Database.getConnection()) {
            String sqlQuery = "SELECT CakesOnOrder.orderId, Cupcake.CakesOnOrder.quantity, \n" +
                    "CakeBottoms.`name` as \"bottomName\", CakeBottoms.price as \"bottomPrice\",\n" +
                    "CakeToppings.`name` as \"toppingName\", CakeToppings.price as \"toppingPrice\"\n" +
                    "FROM CakesOnOrder\n" +
                    " INNER JOIN CakeBottoms ON CakesOnOrder.bottomId = CakeBottoms.id\n" +
                    " INNER JOIN CakeToppings ON CakesOnOrder.toppingId = CakeToppings.id";
            
            try(PreparedStatement s = conn.prepareStatement(sqlQuery)) {
            
            
            ResultSet rs = s.executeQuery();
            
            while (rs.next()) {
                if (orderId == rs.getInt("orderId")) {
                    
                    int quantity = rs.getInt("quantity");
                    String bottomName = rs.getString("bottomName");
                    String toppingName = rs.getString("toppingName");
                    double cakePrice = rs.getDouble("toppingPrice") + rs.getDouble("bottomPrice");
                    
                    
                    Cake tmpCake = new Cake(bottomName, toppingName, (int) cakePrice);
                    
                    Order.Item tmpItem = new Order.Item(tmpCake, quantity);
                    cakeList.add(tmpItem);
                }
            }
            
            System.out.println("Cakes added to map: " + cakeList);
            
            return cakeList;
            
        }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    
    public Order createOrder(User user, List<Order.Item> cakes, String comment, boolean paid) {
        Order tmpOrder;
        
        int orderId = 0;
        String orderComment = Utils.encodeHtml(comment);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        boolean completed = false;
        
        
        try (Connection conn = Database.getConnection()) {
            String sql = "INSERT INTO Orders (userId, comment, createdAt, paid, completed) " +
                    "VALUE (?,?,?,?,?);";
            
            try(PreparedStatement ps =
                    conn.prepareStatement(
                            sql,
                            Statement.RETURN_GENERATED_KEYS)){
            
            ps.setInt(1, user.getId());
            ps.setString(2, orderComment);
            ps.setTimestamp(3, timestamp);
            ps.setBoolean(4, paid);
            ps.setBoolean(5, false);
            
            ps.executeUpdate();
            
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                orderId = rs.getInt(1);
            }
            
            createCakesOnOrder(orderId, cakes);
            System.out.println("Creating cakes on order: " + orderId);
            
            tmpOrder = new Order(orderId, user, comment, timestamp, paid, false, cakes);
            
            
            return tmpOrder;
            
        }} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public void createCakesOnOrder(int orderId, List<Order.Item> cakes) {
        int cakeno = cakes.size();
        System.out.println("Antal kager som skal indsættes: " + cakeno);
        for (Order.Item c : cakes) {
            System.out.println("cakeno: " + cakeno);
            try (Connection conn = Database.getConnection()) {
                
                String sql = "INSERT INTO CakesOnOrder (orderId, bottomId, toppingId, quantity) VALUE (?,?,?,?);";
                
                
                try (var ps = conn.prepareStatement(sql)) {
                    
                    int cakeToppingId = new DBCakeOptions(db).getToppingIdFromName(c.getCake().getTopping());
                    int cakeBottomId = new DBCakeOptions(db).getBottomIdFromName(c.getCake().getBottom());
                    
                    System.out.println("Trying to insert cake...");
                    System.out.println("orderID: " + orderId);
                    System.out.println("bottomId: " + cakeBottomId);
                    System.out.println("toppingId: " + cakeToppingId);
                    System.out.println("quantity: " + c.getAmount());
                    
                    ps.setInt(1, orderId);
                    ps.setInt(2, cakeBottomId);
                    ps.setInt(3, cakeToppingId);
                    ps.setInt(4, c.getAmount());
                    ps.executeUpdate();
                    
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            cakeno--;
        }
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
    
    
    public boolean deleteOrder(int orderId) {
        try (Connection conn = Database.getConnection()) {
            
            PreparedStatement ps = conn.prepareStatement(
                    "DELETE FROM Orders WHERE id=?;");
            
            
            ps.setInt(1, orderId);
            
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
            
            
            ps.setInt(1, orderId);
            
            try {
                ps.executeUpdate();
            } catch (SQLIntegrityConstraintViolationException e) {
                throw new RuntimeException(e);
            }
            
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public List<Order> getAllOrdersMap() {
        List<Order> tmpList = new LinkedList<>();
        try (Connection conn = Database.getConnection()) {
            try (PreparedStatement s = conn.prepareStatement("SELECT * FROM Orders ORDER BY id DESC;")){
            ResultSet rs = s.executeQuery();
            
            while (rs.next()) {
                int id = rs.getInt(1);
                int userId = rs.getInt(2);
                String comment = rs.getString(3);
                comment = Utils.encodeHtml(comment);
                Timestamp timestamp = rs.getTimestamp(4);
                boolean paid = rs.getBoolean(5);
                boolean completed = rs.getBoolean(6);
                
                User tmpUsr = new DBUser().findUser(userId);
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
                
                
                tmpList.add(tmpOrder);
            }}
            return tmpList;
        } catch (SQLException | UserNotFound e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    public Iterable<Order> findAll() {
        return getAllOrdersMap();
    }
    
    @Override
    public Order find(int id) throws NoOrderExists {
        for(Order o: findAll()){
            if(o.getOrderId() == id){
                return o;
            }
        }
        throw new NoOrderExists();
    }
    
    @Override
    public Order create(User user, List<Order.Item> cakes, String comment, boolean paid) {
        return createOrder(user, cakes, comment, paid);
    }
}