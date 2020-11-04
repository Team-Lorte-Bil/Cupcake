package api;

import domain.items.Cake;
import domain.order.Order;

import java.util.ArrayList;
import java.util.List;

public class Cart{
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
        
        for(Order.Item item: cakes){
            if(item.getCake().equals(newItem.getCake())){
                item.setAmount(item.getAmount() + newItem.getAmount());
            } else {
                lastAddedCake = newItem.getCake();
                tmpList.add(newItem);
            }
        }
        
        if(cakes.isEmpty()){
            tmpList.add(newItem);
            lastAddedCake = newItem.getCake();
        }
        
        cakes = tmpList;
        updateCartValue();
    }
    
    /**
     * Resets the Cart
     */
    protected void clearCart(){
        cakes = new ArrayList<>();
        cartValue = 0;
        lastAddedCake = null;
    }
    
    /**
     * @return Total value of cart as Integer
     */
    public int getCartValue() {
        return cartValue;
    }
    
    /**
     * @return List of Cakes on Order
     * @see Order
     */
    public List<Order.Item> getCakes() {
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
