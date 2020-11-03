package domain.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OptionTest {

    @Test
    void testEquals_ShouldEqualWhenParametersMatch() {
        {
            Option option = new Option(1,"chocobot", "topping",6);

            assertEquals(option, new Option(1,"chocobot", "topping",6));
            assertNotEquals(option, new Option(2,"chocobot", "topping",6));


        }
    }
}