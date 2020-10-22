package api;

import domain.items.Cake;

import java.util.ArrayList;
import java.util.HashMap;

public class Cupcake {
    
    private HashMap<Cake, Integer> cakes = new HashMap<>();
    
    
    public Cupcake() {
    
    }
    
    public HashMap<Cake, Integer> getCakes() {
        return cakes;
    }
    
    public void setCakes(HashMap<Cake, Integer> cakes) {
        this.cakes = cakes;
    }
}
