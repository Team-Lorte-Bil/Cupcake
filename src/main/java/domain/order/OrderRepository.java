package domain.order;

import domain.user.User;
import java.util.List;

public interface OrderRepository {
    Iterable<Order> findAll();
    
    Order find(int id) throws NoOrderExists;
    
    Order create(User user, List<Order.Item> cakes, String comment, boolean paid);
}
