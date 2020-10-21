package domain.items;

import java.util.HashMap;

public class CakeOption {
    
    private final HashMap<String,Integer> bottoms;
    private final HashMap<String,Integer> toppings;
    
    public CakeOption() {
        this.bottoms = populateBottoms();
        this.toppings = populateToppings();
    }
    
    
    private HashMap<String,Integer> populateBottoms() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Chokolade",5);
        map.put("Vanilije",5);
        map.put("Nødder",5);
        map.put("Pistacie",6);
        map.put("Mandel",7);
        
        return map;
    }
    
    private HashMap<String,Integer> populateToppings() {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("Chokolade",5);
        map.put("Blåbær",5);
        map.put("Hindbær",5);
        map.put("Kiks",6);
        map.put("Jordbær",6);
        map.put("Rom/Rossiner",7);
        map.put("Appelsin",8);
        map.put("Citron",8);
        map.put("Blåskimmel",9);
        
        return map;
    }
    
    public HashMap<String, Integer> getBottoms() {
        return bottoms;
    }
    
    public HashMap<String, Integer> getToppings() {
        return toppings;
    }
}
