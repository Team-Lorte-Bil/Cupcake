package domain.order;

import domain.items.Cake;
import domain.user.User;

import java.sql.Timestamp;
import java.util.HashMap;

public interface OrderRepository {
    Iterable<Order> findAll();
    
    Order find(int id) throws NoOrderExists;
    
    Order create(int id, User user, HashMap<Cake, Integer> cakes, String comment, Timestamp timestamp, boolean paid);
}
