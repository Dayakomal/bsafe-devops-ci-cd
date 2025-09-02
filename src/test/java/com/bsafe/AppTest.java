package com.bsafe;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    void testHomeMessage() {
        String expected = "Hello from B-Safe CI/CD!";
        String actual = new App().home();
        assertEquals(expected, actual, "Home message should match");
    }
}
