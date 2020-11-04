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
    
    
    private final DBCakeOptions dbCakeOptions;
    private final DBUser dbUser;
    private final Database db;
    
    public DBOrder(Database db, DBCakeOptions dbCakeOptions, DBUser dbUser) {
        this.db = db;
        this.dbCakeOptions = dbCakeOptions;
        this.dbUser = dbUser;
    }
    
    /**
     * @param orderId Order ID
     * @return List of cakes and amount on Order
     * @see Order.Item
     */
    public List<Order.Item> getCakesOnOrder(int orderId) {
        List<Order.Item> cakeList = new ArrayList<>();
        
        try (Connection conn = db.getConnection()) {
            String sqlQuery = "SELECT CakesOnOrder.orderId, CakesOnOrder.quantity, \n" +
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
            
            return cakeList;
            
        }} catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    
    
    /**
     * @param user User object
     * @param cakes List of Order Items (Cake and amount)
     * @param comment Order comment
     * @param paid Paid for or not
     * @return Order object
     * @see Order
     * @see Order.Item
     */
    public Order createOrder(User user, List<Order.Item> cakes, String comment, boolean paid) {
        Order tmpOrder;
        
        int orderId = 0;
        String orderComment = Utils.encodeHtml(comment);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        
        try (Connection conn = db.getConnection()) {
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
            
            tmpOrder = new Order(orderId, user, comment, timestamp, paid, false, cakes);
            
            
            return tmpOrder;
            
        }} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * @query INSERT INTO CakesOnOrder (orderId, bottomId, toppingId, quantity)
     * @param orderId Order ID
     * @param cakes Item object (Cake, Amount)
     * @see Order.Item
     */
    public void createCakesOnOrder(int orderId, List<Order.Item> cakes) {
        for (Order.Item c : cakes) {
            try (Connection conn = db.getConnection()) {
                
                String sql = "INSERT INTO CakesOnOrder (orderId, bottomId, toppingId, quantity) VALUE (?,?,?,?);";
                
                
                try (var ps = conn.prepareStatement(sql)) {
                    
                    int cakeToppingId = dbCakeOptions.getToppingIdFromName(c.getCake().getTopping());
                    int cakeBottomId = dbCakeOptions.getBottomIdFromName(c.getCake().getBottom());
                    
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
        }
    }
    
    
    /**
     * Deletes the specified order by ID
     * @param orderId Order ID
     * @query DELETE FROM Orders WHERE ID=orderId
     */
    public void deleteOrder(int orderId) {
        try (Connection conn = db.getConnection()) {
            
            try(PreparedStatement ps = conn.prepareStatement("DELETE FROM Orders WHERE id=?;")){
            ps.setInt(1, orderId);
            ps.executeUpdate();
            ps.getUpdateCount();
        }} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * Marks the order as completed and paid
     * @param orderId Order ID
     */
    public void markDone(int orderId) {
        try (Connection conn = db.getConnection()) {
            try(PreparedStatement ps = conn.prepareStatement("UPDATE Orders SET completed=1, paid = 1 WHERE id=?;")){
            
            ps.setInt(1, orderId);
            ps.executeUpdate();
           
            
        }} catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    /**
     * @query SELECT * FROM Orders ORDER BY id DESC
     * @return A sorted list by ID decending.
     * @see Order
     */
    @SuppressWarnings("DuplicatedCode")
    public List<Order> getAllOrdersSorted() {
        List<Order> tmpList = new LinkedList<>();
        try (Connection conn = db.getConnection()) {
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
                
                User tmpUsr = dbUser.findUser(userId);
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
    
    /**
     * @return A Iterable<Order>
     */
    @Override
    public Iterable<Order> findAll() {
        return getAllOrdersSorted();
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