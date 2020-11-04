package intergration;

import api.Cupcake;
import domain.items.Cake;
import domain.items.Option;
import domain.order.NoOrderExists;
import domain.order.Order;
import domain.user.InvalidPassword;
import domain.user.User;
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
import java.sql.Timestamp;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@Tag("Integration-test")
public class MainTest {

    Cupcake api;
    
    /*
     * Before you run this script create a user 'cupcaketest' and grant access to the database:
     *
     * <pre>
     * DROP USER IF EXISTS cupcaketest@localhost;
     * CREATE USER cupcaketest@localhost;
     * GRANT ALL PRIVILEGES ON cupcaketest.* TO cupcaketest@localhost;
     * </pre>
     */
    
    static void resetTestDatabase() {
        String URL = "jdbc:mysql://localhost:3306/?serverTimezone=CET";
        String USER = "cupcaketest";
        
        InputStream stream = MainTest.class.getClassLoader().getResourceAsStream("init.sql");
        if (stream == null) throw new RuntimeException("init.sql");
        try (Connection conn = DriverManager.getConnection(URL, USER, null)) {
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
//        resetTestDatabase();
//
//        String url = "jdbc:mysql://localhost:3306/cupcaketest?serverTimezone=CET";
//        String user = "cupcaketest";
//        String pass = null;
//
//        Database db = new Database(url, user, pass);
//        db.runMigrations();
        
        api = new Cupcake();
    }
    
    /**
     * <b>Som</b> kunde kan jeg bestille og betale cupcakes med en valgfri bund og top
     * <b>sådan at</b> jeg senere kan køre forbi butikken i Olsker og hente min ordre.
     */
    @Test
    
    void userStory1() throws ValidationException, NoOrderExists, InvalidPassword {
        
        User currentUser = api.checkLogin("user@user.dk", "admin");
        
        Option bottom = api.getCakeOptions().getBottoms().get(2);
        Option topping = api.getCakeOptions().getToppings().get(1);
        Cake cakeOne = new Cake(bottom.getName(), topping.getName(), bottom.getPrice() + topping.getPrice());
        
        bottom = api.getCakeOptions().getBottoms().get(3);
        topping = api.getCakeOptions().getToppings().get(1);
        Cake cakeTwo = new Cake(bottom.getName(), topping.getName(), bottom.getPrice() + topping.getPrice());
        
        api.addCake(cakeOne, 5); // 50kr
        api.addCake(cakeTwo, 3); // 30kr
    
        assertEquals(83, api.getCartValue());
        
        Order expectedOrder = api.createNewOrder(currentUser, api.getCart().getCakes(), "Integration test order");
        Order actualOrder = api.getOrderById(expectedOrder.getOrderId());
        
        assertEquals(expectedOrder.getUser().getId(), actualOrder.getUser().getId());
        assertEquals(expectedOrder.getOrderId(), actualOrder.getOrderId());
        
        api.clearCart();
        assertEquals(0, api.getCartValue());
        
    }


}
