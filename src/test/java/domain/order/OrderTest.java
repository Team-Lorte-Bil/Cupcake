package domain.order;

import domain.items.Cake;
import domain.user.User;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest extends TestCase {
    
    private final List<Order.Item> cakeList = new ArrayList<>();
    
    private Order orderOne, orderTwo;
    

    @Before
    public void setup(){
        cakeList.add(new Order.Item(new Cake("bund", "top", 10), 10));
        cakeList.add(new Order.Item(new Cake("bund1", "top1", 20), 5));
        
        Timestamp timestampOne = new Timestamp(System.currentTimeMillis());
        User userOne = new User(1,"email","name",112,User.Role.User, timestampOne, 0);
        orderOne = new Order(1, userOne, "ingen kommentar", timestampOne, false, false, cakeList);
    
        Timestamp timestampTwo = new Timestamp(System.currentTimeMillis());
        User userTwo = new User(2,"email","name",112,User.Role.Admin, timestampTwo, 0);
        orderTwo = new Order(2, userTwo, "ingen kommentar", timestampTwo, false, false, cakeList);
        
    }

    @Test
    @org.junit.jupiter.api.Order(1)
    void getPrice() {
        assertEquals(orderOne.getPrice(), orderTwo.getPrice());
    }
    
    @Test
    @org.junit.jupiter.api.Order(2)
    void notSameUsers(){
        assertNotEquals(orderOne.getUser(), orderTwo.getUser());
    }
    
    
}