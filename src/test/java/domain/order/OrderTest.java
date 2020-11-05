package domain.order;

import domain.items.Cake;
import domain.user.User;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    @Test
    void testEqualsOnOrdreId() {
        User userOne = new User(1,"mail", "name", 1234, User.Role.User, null, 0);
        User userTwo = new User(2,"mail", "name", 1234, User.Role.User, null, 0);

        Order orderOne = new Order(1,userOne,null,null,false,false);
        Order orderTwo = new Order(2,userTwo,null,null,false,false);

       assertEquals(orderOne, new Order(1,userOne,null,null,false,false));
       assertNotEquals(orderOne, orderTwo);

    }
    @Test
    void getPrice() {
        Cake cakeOne = new Cake("chocolade","is",15);
        Cake cakeTwo = new Cake("chocolade1","notnull",20);
        
        assertEquals(15, cakeOne.getPrice());
        assertNotEquals(15,cakeTwo.getPrice());
    }

}