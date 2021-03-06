package domain.items;

import java.util.Objects;

public class Option {
    private final int id;
    private final String name;
    private final String type;
    private final int price;
    
    public enum Type {
        topping,
        bottom
    }
    
    public Option(int id, String name, String type, int price) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.price = price;
    }
    
    public int getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public String getType() {
        return type;
    }
    
    public int getPrice() {
        return price;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Option option = (Option) o;
        return id == option.id &&
                price == option.price &&
                Objects.equals(name, option.name) &&
                Objects.equals(type, option.type);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(id, name, type, price);
    }
    
    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", price=" + price +
                '}';
    }
}
