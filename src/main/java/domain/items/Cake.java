package domain.items;

import java.util.Objects;

public class Cake {
    private static int cid = 1;
    private final int id;
    private final String bottom, topping;
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
        return price == cake.price &&
                Objects.equals(bottom, cake.bottom) &&
                Objects.equals(topping, cake.topping);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(bottom, topping, price);
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
