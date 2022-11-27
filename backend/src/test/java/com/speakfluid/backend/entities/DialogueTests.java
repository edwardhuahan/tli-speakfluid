package com.speakfluid.backend.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

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
    Dialogue<WozMessage> d1;
    ArrayList<WozMessage> botMsg1;
    ArrayList<WozMessage> userMsg1;
    WozMessage m1 = new com.speakfluid.backend.entities.WozMessage("response", "Hi, here are the locations of our stores");
    WozMessage m2 = new com.speakfluid.backend.entities.WozMessage("response", "What else can I help you with today?");
    WozMessage m3 = new WozMessage("request", "Hi,");
    WozMessage m4 = new WozMessage("request", "Can I have the direction to the store?");
    @BeforeEach
    public void init(){
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
    void testAddConfidenceScoreToEmptyArray() {
        double score = 1.0;
        d1.addConfidenceScore(score);
        int confidenceScoreSize = 1;
        assertEquals(confidenceScoreSize, d1.getConfidenceScore().size());

    }
    @Test
    void testAddStepSuggestionToEmptyArray() {
        String suggestion = "Text";
        d1.addStepSuggestion(suggestion);
        int stepSuggestionSize = 1;
        assertEquals(stepSuggestionSize, d1.getStepSuggestion().size());

    }
    @Test
    void testAddConfidenceScoreToNonEmptyArray() {
        double score1 = 1.0;
        double score2 = 2.0;
        d1.addConfidenceScore(score1);
        d1.addConfidenceScore(score2);
        int confidenceScoreSize = 2;
        assertEquals(confidenceScoreSize, d1.getConfidenceScore().size());
    }

    @Test
    void testAddStepSuggestionToNonEmptyArray() {
        String suggestion1 = "Text";
        String suggestion2 = "Image";
        d1.addStepSuggestion(suggestion1);
        d1.addStepSuggestion(suggestion2);
        int stepSuggestionSize = 2;
        assertEquals(stepSuggestionSize, d1.getStepSuggestion().size());

    }
    @Test
    void testGetConfidenceScore() {
        double score1 = 1.0;
        double score2 = 2.0;
        d1.addConfidenceScore(score1);
        d1.addConfidenceScore(score2);
        assertEquals(new ArrayList<>(Arrays.asList(score1, score2)), d1.getConfidenceScore());


    }
    @Test
    void testGetStepSuggestion() {
        String suggestion1 = "Text";
        String suggestion2 = "Image";
        d1.addStepSuggestion(suggestion1);
        d1.addStepSuggestion(suggestion2);
        assertEquals(new ArrayList<>(Arrays.asList(suggestion1, suggestion2)), d1.getStepSuggestion());
    }
}