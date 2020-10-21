package domain.order;

import domain.items.Cake;
import domain.user.User;

import java.sql.Timestamp;
import java.util.HashMap;

public class Order {
    private static final int id = 1;
    private final int orderId;
    private final User user;
    private final HashMap<Cake, Integer> cakes;
    private final String comment;
    private final Timestamp timestamp;
    private final boolean paid;
    
    public Order(int orderId, User user, String comment, Timestamp timestamp, boolean paid) {
        this.orderId = orderId;
        this.user = user;
        this.cakes = new HashMap<>();
        this.comment = comment;
        this.timestamp = timestamp;
        this.paid = paid;
    }
    
    
    public Order getOrder() {
        return this;
    }
    
    public int getOrderId(){
        return orderId;
    }
    
    public User getUser() {
        return user;
    }
    
    public HashMap<Cake, Integer> getCakes() {
        return cakes;
    }
    
    public String getComment() {
        return comment;
    }
    
    public Timestamp getTimestamp() {
        return timestamp;
    }
    
    public boolean isPaid() {
        return paid;
    }
    
    public void addCake(Cake cake, int amount){
        cakes.put(cake,amount);
    }
}
