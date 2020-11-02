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
    
    public List<Order.Item> getCakes() {
        return cakes;
    }
    
    protected void addItemToCart(Cake cake, int amount){
        List<Order.Item> tmpList = new ArrayList<>(List.copyOf(cakes));
        Order.Item newItem = new Order.Item(cake, amount);
    
        System.out.println("newItem: " + newItem);
        
        for(Order.Item item: cakes){
            System.out.println("FOR curItem: " + item);
            if(item.getCake().equals(newItem.getCake())){
                System.out.println("Same object reached");
                item.setAmount(item.getAmount() + newItem.getAmount());
            } else {
                System.out.println("New object added");
                lastAddedCake = newItem.getCake();
                tmpList.add(newItem);
            }
        }
        
        if(cakes.isEmpty()){
            System.out.println("List is empty");
            tmpList.add(newItem);
            lastAddedCake = newItem.getCake();
        }
        
        cakes = tmpList;
        updateCartValue();
        System.out.println("Add complete: " + this);
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
