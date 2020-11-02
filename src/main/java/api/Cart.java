package api;

import domain.items.Cake;
import domain.order.Order;

import java.util.ArrayList;
import java.util.List;

public class Cart {
    private Cake lastAddedCake;
    private int cartValue;
    private List<Order.Item> cakes;
    
    /**
     * Initializes a new empty cart.
     */
    public Cart() {
        clearCart();
    }
    
    /**
     * Updates the carts total value
     */
    protected void updateCartValue(){
        int sum = 0;
        for(Order.Item item: cakes){
            sum += item.getCake().getPrice() * item.getAmount();
        }
        cartValue = sum;
    }
    
    /**
     * Removes a cake from the cart by ID.
     * @param id Cake id to be removed from cart
     * @see Order.Item
     * @see Cake
     */
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
    
    /**
     * Adds a non-existing cake to the cart.
     * If already in list, updates the amount of cakes.
     *
     * @param cake Cake to be added to cart
     * @param amount Number of cakes to be added
     * @see Order.Item
     * @see Cake
     */
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
    
    /**
     * Resets the Cart
     */
    protected void clearCart(){
        cakes = new ArrayList<>();
        cartValue = 0;
        lastAddedCake = null;
    }
    
    protected int getCartValue() {
        return cartValue;
    }
    
    protected List<Order.Item> getCakes() {
        return cakes;
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
