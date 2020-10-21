package domain.order;

import java.util.ArrayList;

public class OrderRepository {
    private ArrayList<Order> orders;
    
    public OrderRepository() {
        this.orders = new ArrayList<>();
    }
    
    public void addOrderToList(Order o){
        if(!orders.contains(o)){
            orders.add(o);
        } else {
            orders.remove(o);
            orders.add(o);
        }
    }
    
    public ArrayList<Order> getAllOrders(){
        return orders;
    }
    
    public Order getOrderById(int id){
        for(Order o: orders){
            if(o.getOrderId() == id){
                return o;
            }
        }
        return null;
    }
}
