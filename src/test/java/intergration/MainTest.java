package intergration;

import api.Cupcake;
import domain.items.Cake;
import domain.items.Option;
import domain.order.NoOrderExists;
import domain.order.Order;
import domain.user.InvalidPassword;
import domain.user.User;
import domain.user.UserExists;
import infrastructure.Database;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import javax.validation.ValidationException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Integration-test")
public class MainTest {

    Cupcake api;
    
    static void resetTestDatabase() {
        String URL = "jdbc:mysql://localhost:3306/?serverTimezone=CET";
        String USER = "cupcaketest";
        String PASS = "test123";
        
        InputStream stream = MainTest.class.getClassLoader().getResourceAsStream("init.sql");
        if (stream == null) throw new RuntimeException("init.sql");
        try (Connection conn = DriverManager.getConnection(URL, USER, PASS)) {
            conn.setAutoCommit(false);
            ScriptRunner runner = new ScriptRunner(conn);
            runner.setStopOnError(true);
            runner.runScript(new BufferedReader(new InputStreamReader(stream)));
            conn.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        System.out.println("Done running migration");
    }
    
    @BeforeEach
    void setupAPI() {
        resetTestDatabase();

        String url = "jdbc:mysql://localhost:3306/cupcaketest?serverTimezone=CET";
        String user = "cupcaketest";
        String pass = "test123";

        Database db = new Database(url, user, pass);
        
        api = new Cupcake(db);
    }
    
    /**
     * <b>Som</b> kunde kan jeg bestille og betale cupcakes med en valgfri bund og top
     * <b>sådan at</b> jeg senere kan køre forbi butikken i Olsker og hente min ordre.
     */
    
    @Test
    void userStory1() throws ValidationException, NoOrderExists, InvalidPassword, UserExists {
        
        //Create new user and login
        User newUser = api.createNewUser("Test user", "test123", "test@user.ru", 12345678, 500, "User");
        User currentUser = api.checkLogin("test@user.ru", "test123");
        
        //Test if user ID is same
        assertEquals(newUser.getId(), currentUser.getId());
        
        //Cakes to put in cart
        Option bottom = api.getCakeOptions().getBottoms().get(2);
        Option topping = api.getCakeOptions().getToppings().get(1);
        Cake cakeOne = new Cake(bottom.getName(), topping.getName(), bottom.getPrice() + topping.getPrice());
        
        bottom = api.getCakeOptions().getBottoms().get(3);
        topping = api.getCakeOptions().getToppings().get(1);
        Cake cakeTwo = new Cake(bottom.getName(), topping.getName(), bottom.getPrice() + topping.getPrice());
        
        //Add cakes to cart
        api.addCake(cakeOne, 5); // 50kr
        api.addCake(cakeTwo, 3); // 30kr
    
        //Test that cart value is same
        assertEquals(83, api.getCartValue());
        
        //Create new order and get it back from DB to test
        Order expectedOrder = api.createNewOrder(currentUser, api.getCart().getCakes(), "Integration test order");
        Order actualOrder = api.getOrderById(expectedOrder.getOrderId());
        
        //Test that created and pulled order has same ID and user ID
        assertEquals(expectedOrder.getUser().getId(), actualOrder.getUser().getId());
        assertEquals(expectedOrder.getOrderId(), actualOrder.getOrderId());
        
        //Clear the cart
        api.clearCart();
        
        //Test that cart value is 0 after clearing
        assertEquals(0, api.getCartValue());
        
    }


}
