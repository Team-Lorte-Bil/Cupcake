package api;

import domain.items.Cake;
import domain.order.Order;

import java.util.ArrayList;
import java.util.List;

public class Cart{
    private Cake lastAddedCake;
    private int cartValue;
    private List<Order.Item> cakes;
    private final Cupcake api;
    
    /**
     * Initializes a new empty cart.
     */
    public Cart(Cupcake api) {
        this.api = api;
        clearCart();
    }
    
    /**
     * Updates the carts total value
     */
    public void updateCartValue(){
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
    public void removeItemFromCart(int id){
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
    public void addItemToCart(Cake cake, int amount){
        List<Order.Item> tmpList = new ArrayList<>(List.copyOf(cakes));
        Order.Item newItem = new Order.Item(cake, amount);
    
        if(cakes.isEmpty()){
            tmpList.add(newItem);
            lastAddedCake = newItem.getCake();
        
            System.out.println(newItem +  " added");
        } else {
            boolean kagemand = false;
    
            for(Order.Item item: cakes){
                if(item.getCake().equals(newItem.getCake())){
                    System.out.println(item + " equals " + newItem);
                    item.setAmount(item.getAmount() + newItem.getAmount());
                    kagemand = true;
                }
            }
    
            if(!kagemand) {
                tmpList.add(newItem);
                lastAddedCake = newItem.getCake();
            }
        }
        
        
        
        
        cakes = tmpList;
    
        System.out.println("List: " + cakes);
        
        updateCartValue();
    }
    
    /**
     * Resets the Cart
     */
    public void clearCart(){
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
