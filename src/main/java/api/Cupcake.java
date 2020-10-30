package api;

import domain.items.Cake;
import domain.items.CakeOptions;
import domain.items.Option;
import domain.order.Order;
import domain.user.User;
import infrastructure.DBCakeOptions;
import infrastructure.DBOrder;
import infrastructure.DBUser;
import infrastructure.Database;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cupcake {
    private static final String VERSION = "0.1";
    private final Database database;
    private HashMap<Cake, Integer> cakes;
    private CakeOptions cakeOptions;
    
    
    public Cupcake(Database db) {
        this.database = db;
        cakes = new HashMap<>();
        cakeOptions = new DBCakeOptions(database).findAllCakeOptions();
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
        cakeOptions = new DBCakeOptions(database).findAllCakeOptions();
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
    
    /**
     * Tries to remove the request cake from the list.
     * @param id Cake object ID
     * @see Cake
     */
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
    
    
    /**
     * Clears the list.
     */
    public void clearCart(){
        cakes.clear();
    }
    
    /**
     * Calculates the total sum of the current list/basket.
     * @return Total value of cart as int.
     */
    public int getCartValue(){
        return calculateTotalPrice(cakes);
    }
    
    /**
     * @param cakes Map with cakes and amounts.
     * @return Value of provided Map
     */
    private int calculateTotalPrice(Map<Cake,Integer> cakes){
        int totalprice = 0;
        for (Map.Entry<Cake, Integer> entry : cakes.entrySet()) {
            Cake k = entry.getKey();
            Integer v = entry.getValue();
            totalprice += k.getPrice() * v;
        }
        return totalprice;
    }

    
    public Database getDatabase() {
        return database;
    }
    
    public ArrayList<Order> getOrders() {
        return new DBOrder(database).getAllOrders();
    }
    
    public LinkedHashMap<Order, Double> getAllOrders(){
        return new DBOrder(database).getAllOrdersMap();
    }
    
    public ArrayList<User> getCustomers() {
        return new DBUser(database).getAllUsers();
    }
    
    public ArrayList<Option> getAllCakeOptions() {
        return new DBCakeOptions(database).getAllCakeOptions();
    }

    public Option createCakeOption (String name, double price, String type) {
        name = Utils.encodeHtml(name);
        type = Utils.encodeHtml(type);
        return new DBCakeOptions(database).createCakeOption(new Option(0, name, type, (int) price));

    }



}
