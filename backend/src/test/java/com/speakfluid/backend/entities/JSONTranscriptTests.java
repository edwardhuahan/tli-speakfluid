package com.speakfluid.backend.entities;

import com.speakfluid.backend.entities.message.WozMessage;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Tests for the JSONTranscript class.
 *
 * @author  Minh Le
 * @version 1.0
 * @since   2022-12-06
 */

class JSONTranscriptTests {

    static WozMessage chatbot;
    static WozMessage user;
    @BeforeAll
    public static void setUp() {
        
        chatbot = new WozMessage("response", "Chatbot message.");
        user = new WozMessage("request", "User message.");
    }


    @Test
    void testGetMessage() {
        String chatbot_message = chatbot.getMessage();
        String user_message = user.getMessage();
        assertEquals("Chatbot message.", chatbot_message);
        assertEquals("User message.", user_message);
    }

    @Test
    void testGetTraceType() {
        String chatbot_message = chatbot.getTraceType();
        String user_message = user.getTraceType();
        assertEquals("response", chatbot_message);
        assertEquals("request", user_message);
    }

}

