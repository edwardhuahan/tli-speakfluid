package com.speakfluid.backend.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Tests for the Speech class.
 *
 * @author  Kai Zhuang
 * @version 1.0
 * @since   2022-11-19
 */

class WozMessageTests {

    static WozMessage message;
    @BeforeAll
    public static void setUp(){
        message = new WozMessage("Filler type", "Test message.");
    }


    @Test
    void testGetMessage() {
        String msg = message.getMessage();
        assertEquals("Test message.", msg);
    }

}