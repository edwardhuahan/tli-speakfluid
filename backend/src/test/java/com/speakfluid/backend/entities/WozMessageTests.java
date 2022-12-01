package com.speakfluid.backend.entities;

import com.speakfluid.backend.entities.message.WozMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Tests for the Speech class.
 *
 * @author  Minh Le
 * @version 1.0
 * @since   2022-11-28
 */

class WozMessageTests extends MessageTests {

    static WozMessage chatbot;
    static WozMessage user;
    @BeforeAll
    public static void setUp() {
        chatbot = new WozMessage("response", "Chatbot message.");
        user = new WozMessage("response", "User message.");
    }


    @Test
    void testGetMessage() {
        String chatbot_message = chatbot.getMessage();
        String user_message = user.getMessage();
        assertEquals("Chatbot message", chatbot_message);
        assertEquals("User message.", user_message);
    }

    @Test
    void testGetTraceType() {
        String chatbot_message = chatbot.getTraceType();
        String user_message = user.getTraceType();
        assertEquals("request", chatbot_message);
        assertEquals("response.", user_message);
    }

}

