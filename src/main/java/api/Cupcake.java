package api;

import domain.items.Cake;

import java.util.HashMap;
import java.util.Map;

public class Cupcake {
    
    private HashMap<Cake, Integer> cakes = new HashMap<>();
    
    
    public Cupcake() {
    
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
}
