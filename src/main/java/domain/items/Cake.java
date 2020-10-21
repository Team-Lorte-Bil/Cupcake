package domain.items;

public class Cake {
    private static int cid = 1;
    private final int id;
    private final String bottom, topping;
    private final double price;
    
    public Cake(String bottom, String topping, double price) {
        this.id = cid;
        this.bottom = bottom;
        this.topping = topping;
        this.price = price;
        cid++;
    }
    
    public int getId() {
        return id;
    }
    
    public String getBottom() {
        return bottom;
    }
    
    public String getTopping() {
        return topping;
    }
    
    public double getPrice() {
        return price;
    }
    
    @Override
    public String toString() {
        return "Cake{" +
                "id=" + id +
                ", bottom='" + bottom + '\'' +
                ", topping='" + topping + '\'' +
                ", price=" + price +
                '}';
    }
}
