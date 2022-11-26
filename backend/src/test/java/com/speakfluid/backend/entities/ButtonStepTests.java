package com.speakfluid.backend.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.*;
import static java.util.Map.entry;

import static org.junit.jupiter.api.Assertions.assertEquals;

//chatbotMsgLength, userMsgLength correct
//score accumulator correctly adding up the scores based on keyword dictionaries
//match keywords method matches correct keywrods.

public class ButtonStepTests {
    ButtonStep button;
    WozMessage user1;
    WozMessage chat1;
    WozMessage user2;
    WozMessage chat2;
    WozMessage user3;
    WozMessage chat3;

    // initialize array to an empty ArrayList otherwise default value is null.
    ArrayList<WozMessage> userMsgs = new ArrayList<>();
    ArrayList<WozMessage> chatbotMsgs = new ArrayList<>();

    Dialogue dialogue1;

    private final List<Map<String, Double>> chatbotKeywordsScoreMap = Arrays.asList(
            Map.ofEntries(entry("would you", 5.0),
    entry("what type", 4.0), entry("what kind", 2.0), entry("are you", 4.0),
    entry("would it", 4.0), entry("do you", 2.0),
    entry("here is", 2.0)),
            Map.ofEntries(entry("here are", 4.0), entry("choose", 4.0), entry("select", 4.0)),
            Map.ofEntries(entry("destination", 3.0), entry("date", 3.0), entry("departure", 3.0),
    entry("arriving", 3.0)));

    private final List<Map<String, Double>> userKeywordsScoreMap = Arrays.asList(
            Map.ofEntries(entry("booking", 2.0),
                    entry("train", 2.0), entry("go to", 2.0), entry("arrive", 2.0)),
            Map.ofEntries(entry("hotel", 4.0), entry("cheap", 4.0), entry("hospital", 4.0)));

    @BeforeEach
    void init() { //9+2++4+4+2 = 21+2
        //initialize each object with a value before each test.
        button = new ButtonStep();
        user1 = new WozMessage("PNG123", "good evening, i need help with" +
                " my lower back pain.");
        chat1 = new WozMessage("PNG123", "here are some possible remedies for lower" +
                "back pain. would you like me to list them for you?");
        user2 = new WozMessage("PNG123", "no, i think i need to go to the hospital. how " +
                "do i go to the nearest one?");
        chat2 =  new WozMessage("PNG123", "here are a list of your nearest hospitals. " +
                "choose the one that you would like");
        user3 = new WozMessage("PNG123", "i will go to the first one thank you.");
        chat3 = new WozMessage("PNG123", "you're welcome. have a nice day.");

        userMsgs.addAll(Arrays.asList(user1, user2, user3));
        chatbotMsgs.addAll(Arrays.asList(chat1, chat2, chat3));

        dialogue1 = new Dialogue(chatbotMsgs, userMsgs);

    }

    @AfterEach
    public void resetScoreAccumulator() {
        button.setZeroScoreAccumulator();
        // because each message is added before each test, we must clear them to prevent it from repeating.
        userMsgs.clear();
        chatbotMsgs.clear();
    }
    @Test
    void testScoreAccumulator() {
        double expectedScore = 17 + 6;
        button.runAnalysis(dialogue1);
        double actualScore = button.getScoreAccumulator();
        assertEquals(expectedScore, actualScore);
    }

    @Test
    void testUserMsgLength(){
        double actual = button.calculateMsgLength(user1);
        double expected = 10;
        assertEquals(expected, actual);
    }

    @Test
    void testChatbotMsgLength(){
        double actual = button.calculateMsgLength(chat1);
        double expected = 18;
        assertEquals(expected, actual);

    }

    @Test
    void testCountMatchKeywords1(){
        button.countMatchKeywords(chat1,chatbotKeywordsScoreMap);
        double actual = button.getScoreAccumulator();
        double expected = 9.0;
        assertEquals(expected, actual);
    }

    @Test
    void testCountMatchKeywords2(){
        button.countMatchKeywords(chat2, chatbotKeywordsScoreMap);
        double actual = button.getScoreAccumulator();
        double expected = 8.0;
        assertEquals(expected, actual);
    }
    @Test
    void testCountMatchKeywords3(){

    }


}
