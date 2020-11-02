package domain.items;

import java.util.List;

public class CakeOptions {
    
    private final List<Option> bottoms;
    private final List<Option> toppings;
    
    public CakeOptions(List<Option> bottoms, List<Option> toppings) {
        this.bottoms = bottoms;
        this.toppings = toppings;
    }
    
    public List<Option> getBottoms() {
        return bottoms;
    }
    
    public List<Option> getToppings() {
        return toppings;
    }
}
