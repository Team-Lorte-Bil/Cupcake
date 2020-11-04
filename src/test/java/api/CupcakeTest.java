package api;

import domain.items.CakeOptions;
import domain.items.Option;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CupcakeTest {

    @Test
    void getAllCakeOptionsAndCompareWithError() {
        // Setup
        Cupcake api = new Cupcake();
        
        CakeOptions originalCakeOptions = api.getCakeOptions();
        List<Option> newToppings = originalCakeOptions.getToppings().subList(1,3);
        List<Option> newBottoms = originalCakeOptions.getBottoms().subList(2,4);
        CakeOptions alteredCakeOptions = new CakeOptions(newBottoms,newToppings);
        
    
        // Test
        assertNotEquals(alteredCakeOptions, originalCakeOptions);
    }

}