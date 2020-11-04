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
    
    
    public Cupcake() {
        var db = new Database();
        dbOptions = new DBCakeOptions(db);
        dbUser = new DBUser(db);
        dbOrder = new DBOrder(db, dbOptions, dbUser);
        
        cart = new Cart();
        cakeOptions = dbOptions.findAllCakeOptions();
        
    }
    
    public Cupcake(Database db){
        dbOptions = new DBCakeOptions(db);
        dbUser = new DBUser(db);
        dbOrder = new DBOrder(db, dbOptions, dbUser);
    
        cart = new Cart();
        cakeOptions = dbOptions.findAllCakeOptions();
    }
    
    /**
     * @return Front-end version to be used.
     */
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
    
    /**
     * @return List of cakes in Cart
     * @see Cart
     */
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
    
    /**
     * @return Cart object
     */
    public Cart getCart() {
        return cart;
    }
    
    /**
     * @param id Item by ID to be removed from the cart
     * @see Cart
     */
    public void removeFromCart(int id){
    cart.removeItemFromCart(id);
    }
    
    
    /**
     * Resets the Cart
     * @see Cart
     */
    public void clearCart(){
        cart.clearCart();
    }
    
    
    /**
     * Calculates total value of cart.
     */
    public int getCartValue(){
        return cart.getCartValue();
    }
    
    
    /**
     * @return List of Orders from Database sorted by ID
     * @see Order
     */
    public List<Order> getAllOrdersSorted(){
        return (List<Order>) dbOrder.findAll();
    }
    
    /**
     * @param id Order ID
     * @return Order with requested ID
     */
    public Order getOrderById(int id){
        for(Order o: getAllOrdersSorted()){
            if(o.getOrderId() == id){
                return o;
            }
        }
        return null;
    }
    
    /**
     * @return List of Customers from Database
     * @see User
     */
    public List<User> getCustomers() {
        return dbUser.getAllUsers();
    }
    
    /**
     * @return List of CakeOptions from Database
     * @see CakeOptions
     */
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
    
    
    /**
     * @param userId ID to be deleted from Database
     */
    public void deleteUser(int userId) {
        dbUser.deleteUser(userId);
    }
    
    /**
     * @param userId ID to be deleted
     * @param newBalance Account balance
     */
    public void changeUserBalance(int userId, double newBalance) {
        dbUser.changeBalance(userId, newBalance);
    }
    
    /**
     * @param usrName Username
     * @param usrPsw Password
     * @param usrMail E-mail
     * @param usrPhone Phone number
     * @param balance Account balance
     * @param role User Role. Options:
     *             Admin
     *             User
     * @return Newly created user
     * @see User
     *
     * @throws UserExists If user already exists.
     */
    public User createNewUser(String usrName, String usrPsw, String usrMail, int usrPhone, double balance, String role) throws UserExists {
        return dbUser.createUser(usrName, usrPsw, usrMail, usrPhone, balance, role);
    }
    
    /**
     * @param itemId Option to be deleted by ID
     * @param type Either:
     *             Bottom
     *             Topping
     * @see Option
     * @see CakeOptions
     */
    public void deleteCakeOption(int itemId, String type) {
        dbOptions.deleteCakeOption(itemId, type);
    }
    
    /**
     * @param orderId Order to be deleted by ID
     * @see Order
     */
    public void deleteOrder(int orderId) {
        dbOrder.deleteOrder(orderId);
    }
    
    /**
     * @param orderId Order to be marked as complete by ID
     * @see Order
     */
    public void markOrderDone(int orderId) {
        dbOrder.markDone(orderId);
    }
    
    /**
     * @param usrEmail E-mail
     * @param usrPassword Password
     * @return User object if valid
     *
     * @throws InvalidPassword If password or email is wrong.
     */
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
    
    /**
     * @return List of customers from database
     * @see User
     */
    public List<User> getAllUsers() {
        return dbUser.getAllUsers();
    }
    
    /**
     * @return Total sales value of completed orders
     * @see Order where completed is set to true.
     */
    public double getTotalSales() {
        double sum = 0.0;
        
        for(Order o: getAllOrdersSorted()){
            if(o.isCompleted()){
                sum += o.getPrice();
            }
        }
        return sum;
    }
}
