package domain.order;

import domain.items.Cake;
import domain.user.User;

import java.sql.Timestamp;
import java.util.*;

public class Order {
    private final int orderId;
    private final User user;
    private final List<Item> cakes;
    private final String comment;
    private final Timestamp timestamp;
    private final boolean paid;
    private final boolean completed;
    
    public Order(int orderId, User user, String comment, Timestamp timestamp, boolean paid, boolean completed) {
        this.orderId = orderId;
        this.user = user;
        this.cakes = new ArrayList<>();
        this.comment = comment;
        this.timestamp = timestamp;
        this.paid = paid;
        this.completed = completed;
    }
    
    public Order(int orderId, User user, String comment, Timestamp timestamp, boolean paid, boolean completed, List<Item> cakes) {
        this.orderId = orderId;
        this.user = user;
        this.comment = comment;
        this.timestamp = timestamp;
        this.paid = paid;
        this.completed = completed;
        this.cakes = cakes;
    }
    
    /**
     * @return Total price of order as double
     */
    public double getPrice(){
        double price = 0.0;
        
        for(var c: cakes){
            price += c.cake.getPrice() * c.amount;
        }
        
        return price;
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
    
    public List<Item> getCakes() {
        return Collections.unmodifiableList(cakes);
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
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void addCake(Cake cake, int amount){
        cakes.add(new Item(cake, amount));
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Order order = (Order) o;
    
        return orderId == order.orderId;
    }
    
    @Override
    public int hashCode() {
        return orderId;
    }
    
    public static class Item {
        private final Cake cake;
        private int amount;
    
        public Item(Cake cake, int amount) {
            this.cake = cake;
            this.amount = amount;
        }
    
        public Cake getCake() {
            return cake;
        }
    
        public int getAmount() {
            return amount;
        }
    
        public void setAmount(int amount) {
            this.amount = amount;
        }
    
        @Override
        public String toString() {
            return "Item{" +
                    "cake=" + cake +
                    ", amount=" + amount +
                    '}';
        }
    }
}
