package com.speakfluid.backend.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;


/**
 * Tests for the Dialogue class.
 * Since behaviour of generic class works the same for any given generic type, Dialogue<WozMessage> is tested as a representative.
 *
 * @author  Kai Zhuang, Zoey Zhang
 * @version 2.0
 * @since   2022-11-26
 */

class DialogueTests {
    static Dialogue<WozMessage> d1;
    static ArrayList<WozMessage> botMsg1;
    static ArrayList<WozMessage> userMsg1;
    static WozMessage m1 = new com.speakfluid.backend.entities.WozMessage("text", "Hi, here are the locations of our stores");
    static WozMessage m2 = new com.speakfluid.backend.entities.WozMessage("text", "What else can I help you with today?");
    static WozMessage m3 = new WozMessage("text", "Hi,");
    static WozMessage m4 = new WozMessage("text", "Can I have the direction to the store?");
    @BeforeAll
    public static void setUp(){
        botMsg1 = new ArrayList<>(Arrays.asList(m1,m2));
        userMsg1 = new ArrayList<>(Arrays.asList(m3, m4));
        d1 = new Dialogue<>(botMsg1, userMsg1);
    }

    @Test
    void testGetChatBotMessage() {
        assertEquals(botMsg1, d1.getChatBotMessage());

    }
    @Test
    void testGetUserMessage() {
        assertEquals(userMsg1, d1.getUserMessage());

    }
    @Test
    void testGetStepScorePairs() {
        assertEquals(0, d1.getConfidenceScore().size());

    }

    @Test
    void testAddConfidenceScore() {
        double score = 1.0;
        int preSize = d1.getConfidenceScore().size();
        d1.addConfidenceScore(score);
        assertEquals(preSize + 1, d1.getConfidenceScore().size());

    }
}