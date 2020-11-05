package domain.user;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    
    @Test
    void testSaltGenerator(){
        //Setup
        byte[] saltOne = User.generateSalt();
        byte[] saltTwo = User.generateSalt();
        
        //Checks that salts are different
        assertNotEquals(Arrays.toString(saltOne), Arrays.toString(saltTwo));
    }
    
    @Test
    void testSecretCalculator() {
        //Setup
        String password = "hejjunittest";
        
        byte[] saltOne = User.generateSalt();
        byte[] saltTwo = User.generateSalt();
        
        byte[] secretOne = User.calculateSecret(saltOne, password);
        byte[] secretTwo = User.calculateSecret(saltTwo, password);
    
        //Checks that same String password is different.
        assertNotEquals(Arrays.toString(secretOne), Arrays.toString(secretTwo));
    }
    
    @Test
    void compareHashedPasswords(){
        //Setup
        String password = "hejjunittest";
    
        byte[] saltOne = User.generateSalt();
        byte[] saltTwo = saltOne;
    
        byte[] secretOne = User.calculateSecret(saltOne, password);
        byte[] secretTwo = User.calculateSecret(saltTwo, password);
    
        //Checks the password with same salt is the same
        assertArrayEquals(secretOne, secretTwo);
    }
    
    @Test
    void testUserEquals(){
        User userOne = new User(1,"mail", "name", 1234, User.Role.User, null, 0);
    
        User userTwo = new User(1,"asasd", "asdasd", 12312322, User.Role.Admin, null, 44444440);
        
        assertEquals(userOne, userTwo);
        assertNotEquals(userOne, new User(2,null,null,123,null,null,12));
    }
    
}