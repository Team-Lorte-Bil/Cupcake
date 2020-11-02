package domain.items;

public class Cake {
    private static int cid = 1;
    private final int id;
    private final String bottom;
    private final String topping;
    private final int price;
    
    public Cake(String bottom, String topping, int price) {
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
    
    public int getPrice() {
        return price;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        
        Cake cake = (Cake) o;
        
        if (price != cake.price) return false;
        if (! bottom.equals(cake.bottom)) return false;
        return topping.equals(cake.topping);
    }
    
    @Override
    public int hashCode() {
        int result = bottom.hashCode();
        result = 31 * result + topping.hashCode();
        result = 31 * result + price;
        return result;
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
