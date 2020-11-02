package api;

import domain.items.Cake;
import domain.order.Order;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Cake lastAddedCake;
    private int cartValue;
    private List<Order.Item> cakes;
    
    public Cart() {
        clearCart();
    }
    
    protected void updateCartValue(){
        int sum = 0;
        for(Order.Item item: cakes){
            sum += item.getCake().getPrice() * item.getAmount();
        }
        cartValue = sum;
    }
    
    protected void removeItemFromCart(int id){
        for(Order.Item c: cakes){
            if(c.getCake().getId() == id){
                cakes.remove(c);
                break;
            }
        }
        updateCartValue();
        if(cakes.isEmpty()) clearCart();
    }
    
    protected void clearCart(){
        cakes = new ArrayList<>();
        cartValue = 0;
        lastAddedCake = null;
    }
    
    public int getCartValue() {
        return cartValue;
    }
    
    public Cake getLastAddedCake() {
        return lastAddedCake;
    }
    
    public List<Order.Item> getCakes() {
        return cakes;
    }
    
    protected void addItemToCart(Cake cake, int amount){
        Order.Item newItem = new Order.Item(cake, amount);
        
        for(Order.Item item: cakes){
            if(item.getCake().equals(newItem.getCake())){
                item.setAmount(newItem.getAmount());
            } else {
                lastAddedCake = newItem.getCake();
                cakes.add(newItem);
            }
        }
        
        if(cakes.isEmpty()){
            cakes.add(newItem);
            lastAddedCake = newItem.getCake();
        }
        
        updateCartValue();
    }
    
    
    @Override
    public String toString() {
        return "Cart{" +
                "lastAddedCake=" + lastAddedCake +
                ", cartValue=" + cartValue +
                ", cakes=" + cakes +
                '}';
    }
}
