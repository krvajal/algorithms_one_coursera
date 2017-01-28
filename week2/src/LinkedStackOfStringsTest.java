import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * Created by carvajal on 1/5/17.
 */
class LinkedStackOfStringsTest {


    @BeforeEach
    void setUp() {


    }

    @org.junit.jupiter.api.Test
    void isEmpty() {
        LinkedStackOfStrings stack = new LinkedStackOfStrings();
        assertTrue(stack.isEmpty() == true);

        stack.push("hola");
        assertFalse(stack.isEmpty() == true);


    }


    @org.junit.jupiter.api.Test
    void testPush() {




    }

    @org.junit.jupiter.api.Test
    void testPop() {

    }

}