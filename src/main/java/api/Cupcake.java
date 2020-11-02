package api;

import domain.order.*;
import domain.items.*;
import domain.user.*;
import infrastructure.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class Cupcake {
    private static final String VERSION = "2";
    private final Database database;
    private List<Order.Item> cakes;
    private CakeOptions cakeOptions;
    
    private final DBCakeOptions dbOptions;
    private final DBUser dbUser;
    private final DBOrder dbOrder;
    
    
    public Cupcake(Database db) {
        this.database = db;
        dbOptions = new DBCakeOptions(database);
        dbUser = new DBUser(database);
        dbOrder = new DBOrder(database);
        
        cakes = new ArrayList<>();
        cakeOptions = dbOptions.findAllCakeOptions();
        
    }
    
    public String getVersion() {
        return VERSION;
    }
    
    public boolean checkAdminRights(HttpServletRequest req){
        if(req.getSession().getAttribute("isAdmin") != null){
            return (boolean) req.getSession().getAttribute("isAdmin");
        } else {
            return false;
        }
    }
    
    
    public CakeOptions getCakeOptions() {
        cakeOptions = dbOptions.findAllCakeOptions();
        return cakeOptions;
    }
    
    public void setCakeOptions(CakeOptions cakeOptions) {
        this.cakeOptions = cakeOptions;
    }
    
    public List<Order.Item> getCakes() {
        return cakes;
    }
    
    public void addCake(Cake cake, int amount){
        cakes.add(new Order.Item(cake, amount));
    }
    
    public void setCakes(List<Order.Item> cakes) {
        this.cakes = cakes;
    }
    

    public void removeFromCart(int id){
        Cake tmpCake = null;
        for(Order.Item c: cakes){
            if(c.getCake().getId() == id){
                tmpCake = c.getCake();
                break;
            }
        }
        cakes.remove(tmpCake);
        
        if(cakes.isEmpty()) cakes = null;
    }
    
 
    public void clearCart(){
        cakes.clear();
    }
    

    public int getCartValue(){
        return calculateTotalPrice(cakes);
    }
    
 
    private int calculateTotalPrice(List<Order.Item> cakes){
        int totalprice = 0;
        for(Order.Item item: cakes){
            
            totalprice += item.getCake().getPrice() * item.getAmount();
        }
        return totalprice;
    }

    
    public List<Order> getOrders() {
        return dbOrder.getAllOrders();
    }
    
    public List<Order> getAllOrders(){
        return dbOrder.getAllOrdersMap();
    }
    
    public List<User> getCustomers() {
        return dbUser.getAllUsers();
    }
    
    public List<Option> getAllCakeOptions() {
        return dbOptions.getAllCakeOptions();
    }

    public void createCakeOption (String name, double price, String type) {
        name = Utils.encodeHtml(name);
        type = Utils.encodeHtml(type);
        dbOptions.createCakeOption(new Option(0, name, type, (int) price));
    
    }
    
    
    public void deleteUser(int userId) {
        dbUser.deleteUser(userId);
    }
    
    public void changeUserBalance(int userId, double newBalance) {
        dbUser.changeBalance(userId, newBalance);
    }
    
    public User createNewUser(String usrName, String usrPsw, String usrMail, int usrPhone, double balance, String role) {
        return dbUser.createUser(usrName, usrPsw, usrMail, usrPhone, balance, role);
    }
    
    public void deleteCakeOption(int itemId, String type) {
        dbOptions.deleteCakeOption(itemId, type);
    }
    
    public void deleteOrder(int orderId) {
        dbOrder.deleteOrder(orderId);
    }
    
    public void markOrderDone(int orderId) {
        dbOrder.markDone(orderId);
    }
    
    public User checkLogin(String usrEmail, String usrPassword) throws InvalidPassword {
        return dbUser.checkLogin(usrEmail, usrPassword);
    }
    
    public Order createNewOrder(User curUser, List<Order.Item> cakes, String comment) {
        double newAccountBalance = curUser.getAccountBalance() - getCartValue();
        boolean paid = false;
    
        System.out.println("Current balance: " + curUser.getAccountBalance());
        System.out.println("Order value: " + getCartValue());
        System.out.println("New balance: " + newAccountBalance);
    
        if(newAccountBalance >= 0){
            changeUserBalance(curUser.getId(), newAccountBalance);
            curUser.setAccountBalance(newAccountBalance);
            paid = true;
        }
        
        return dbOrder.createOrder(curUser,cakes,comment, paid);
    }
    
    public List<User> getAllUsers() {
        return dbUser.getAllUsers();
    }
    
    public double getTotalSales() {
        double sum = 0.0;
        
        for(Order o: getAllOrders()){
            if(o.isCompleted()){
                sum += o.getPrice();
            }
        }
    
        return sum;
    }
}
