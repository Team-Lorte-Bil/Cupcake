package api;

import domain.order.*;
import domain.items.*;
import domain.user.*;
import infrastructure.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cupcake {
    private static final String VERSION = "2";
    private final Database database;
    private HashMap<Cake, Integer> cakes;
    private CakeOptions cakeOptions;
    
    private final DBCakeOptions dbOptions;
    private final DBUser dbUser;
    private final DBOrder dbOrder;
    
    
    public Cupcake(Database db) {
        this.database = db;
        dbOptions = new DBCakeOptions(database);
        dbUser = new DBUser(database);
        dbOrder = new DBOrder(database);
        
        cakes = new HashMap<>();
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
    
    public HashMap<Cake, Integer> getCakes() {
        return cakes;
    }
    
    public void addCake(Cake cake, int amount){
        if(cakes==null){
            cakes = new HashMap<>();
        }
        cakes.put(cake,amount);
    }
    
    public void setCakes(HashMap<Cake, Integer> cakes) {
        this.cakes = cakes;
    }
    

    public void removeFromCart(int id){
        Cake tmpCake = null;
        for(Cake c: cakes.keySet()){
            if(c.getId() == id){
                tmpCake = c;
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
    
 
    private int calculateTotalPrice(Map<Cake,Integer> cakes){
        int totalprice = 0;
        for (Map.Entry<Cake, Integer> entry : cakes.entrySet()) {
            Cake k = entry.getKey();
            Integer v = entry.getValue();
            totalprice += k.getPrice() * v;
        }
        return totalprice;
    }

    
    public ArrayList<Order> getOrders() {
        return dbOrder.getAllOrders();
    }
    
    public LinkedHashMap<Order, Double> getAllOrders(){
        return dbOrder.getAllOrdersMap();
    }
    
    public ArrayList<User> getCustomers() {
        return dbUser.getAllUsers();
    }
    
    public ArrayList<Option> getAllCakeOptions() {
        return dbOptions.getAllCakeOptions();
    }

    public Option createCakeOption (String name, double price, String type) {
        name = Utils.encodeHtml(name);
        type = Utils.encodeHtml(type);
        return dbOptions.createCakeOption(new Option(0, name, type, (int) price));

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
    
    public Order createNewOrder(User curUser, HashMap<Cake, Integer> cakes, String comment) {
        return dbOrder.createOrder(curUser,cakes,comment);
    }
    
    public ArrayList<User> getAllUsers() {
        return dbUser.getAllUsers();
    }
    
    public double getTotalSales() {
        double sum = 0.0;
    
        for(Map.Entry<Order, Double> entry: getAllOrders().entrySet()){
            if(entry.getKey().isCompleted()){
                sum += entry.getValue();
            }
        }
    
        return sum;
    }
}
