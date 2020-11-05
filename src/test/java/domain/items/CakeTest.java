package domain.items;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class CakeTest {

    @Test
    void testEquals_ShouldEqualWhenParametersMatch() {
        Cake cake = new Cake("chocobot","testtop",11);

        assertEquals(cake, new Cake("chocobot","testtop", 11));
        assertNotEquals(cake, new Cake("brownie","notnull",11));

    }
}