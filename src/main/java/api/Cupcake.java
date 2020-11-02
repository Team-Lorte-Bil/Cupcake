package api;

import domain.order.*;
import domain.items.*;
import domain.user.*;
import infrastructure.*;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

public class Cupcake {
    private static final int VERSION = 2; //Either 1 or 2 is valid.
    private final Cart cart;
    private CakeOptions cakeOptions;
    
    private final DBCakeOptions dbOptions;
    private final DBUser dbUser;
    private final DBOrder dbOrder;
    
    
    public Cupcake(Database db) {
        dbOptions = new DBCakeOptions(db);
        dbUser = new DBUser();
        dbOrder = new DBOrder(db);
        
        cart = new Cart();
        cakeOptions = dbOptions.findAllCakeOptions();
        
    }
    
    public int getVersion() {
        return VERSION;
    }
    
    /**
     * Check whether the current session User is admin or not.
     * @param req Current HttpRequest
     * @return boolean
     * @see User
     */
    public boolean checkAdminRights(HttpServletRequest req){
        if(req.getSession().getAttribute("isAdmin") != null) {
            return (boolean) req.getSession().getAttribute("isAdmin");
        }
        return false;
    }
    
    
    /**
     * @return CakeOptions object with List of toppings and bottoms
     * @see CakeOptions
     */
    public CakeOptions getCakeOptions() {
        cakeOptions = dbOptions.findAllCakeOptions();
        return cakeOptions;
    }
    
    public List<Order.Item> getCakes() {
        return cart.getCakes();
    }
    
    
    /**
     * @param cake Cake to be added
     * @param amount Amount of cakes to be added
     */
    public void addCake(Cake cake, int amount){
        cart.addItemToCart(cake, amount);
    }
    
    public Cart getCart() {
        return cart;
    }
    
    public void removeFromCart(int id){
    cart.removeItemFromCart(id);
    }
    
    
    
    public void clearCart(){
        cart.clearCart();
    }
    
    
    /**
     * Calculates total value of cart.
     * @link calculateTotalPrice()
     */
    public int getCartValue(){
        return cart.getCartValue();
    }
    
    
    public List<Order> getOrders() {
        return dbOrder.getAllOrders();
    }
    
    public List<Order> getAllOrders(){
        return (List<Order>) dbOrder.findAll();
    }
    
    public List<User> getCustomers() {
        return dbUser.getAllUsers();
    }
    
    public List<Option> getAllCakeOptions() {
        return dbOptions.getAllCakeOptions();
    }
    
    /**
     * Creates a new option.
     * @param name Options name
     * @param price Options price
     * @param type Type of option; Bottom or Topping
     */
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
    
    public User createNewUser(String usrName, String usrPsw, String usrMail, int usrPhone, double balance, String role) throws UserExists {
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
    
    /**
     * Creates a new order in the database.
     * If user got avaiable balance, it will be marked as paid.
     * @param curUser Current user object
     * @param cakes List of cakes to add to the order
     * @param comment If any comment
     * @return created Order object.
     * @see DBOrder
     * @see Order
     */
    public Order createNewOrder(User curUser, List<Order.Item> cakes, String comment) {
        double newAccountBalance = curUser.getAccountBalance() - getCartValue();
        boolean paid = false;
    
        if(newAccountBalance >= 0){
            changeUserBalance(curUser.getId(), newAccountBalance);
            curUser.setAccountBalance(newAccountBalance);
            paid = true;
        }
        
        return dbOrder.create(curUser,cakes,comment,paid);
    }
    
    public List<User> getAllUsers() {
        return dbUser.getAllUsers();
    }
    
    /**
     * @return Total sales value of completed orders
     * @see Order where completed is set to true.
     */
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
