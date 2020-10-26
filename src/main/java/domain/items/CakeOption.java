package domain.items;

import java.util.HashMap;

public class CakeOption {
    
    private final HashMap<String,Integer> bottoms;
    private final HashMap<String,Integer> toppings;
    
    public CakeOption(HashMap<String, Integer> bottoms, HashMap<String, Integer> toppings) {
        this.bottoms = bottoms;
        this.toppings = toppings;
    }
    
    public void addBottom(Option option){
        if(!bottoms.containsKey(option.getName())){
            bottoms.put(option.getName(),option.getPrice());
        }
    }
    
    public void addTopping(Option option){
        if(!toppings.containsKey(option.getName())){
            toppings.put(option.getName(),option.getPrice());
        }
    }
    
    public HashMap<String, Integer> getBottoms() {
        return bottoms;
    }
    
    public HashMap<String, Integer> getToppings() {
        return toppings;
    }
}
