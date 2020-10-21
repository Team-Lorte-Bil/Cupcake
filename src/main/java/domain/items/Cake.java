package domain.items;

public class Cake {
    private final int id;
    private final String bottom, topping;
    private final double price;
    
    public Cake(int id, String bottom, String topping, double price) {
        this.id = id;
        this.bottom = bottom;
        this.topping = topping;
        this.price = price;
    }
}
