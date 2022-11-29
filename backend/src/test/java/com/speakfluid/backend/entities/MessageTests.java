package com.speakfluid.backend.entities;

import com.speakfluid.backend.entities.message.Message;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the abstract Message class.
 *
 * @author  Zoey Zhang
 * @version 1.0
 * @since   2022-11-26
 */
class MessageTests {
    static Message message;
    static String m1 = "Hi, what can I help you with today?";

    @BeforeAll
    static void setUp() {
       message = Mockito.mock(Message.class, Mockito.withSettings()
                .useConstructor(m1)
                .defaultAnswer(Mockito.CALLS_REAL_METHODS)
       );
    }

    @Test
    void getMessage() {
        assertEquals(m1, message.getMessage());
    }
}