package com.speakfluid.backend.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;
import static java.util.Map.entry;

/*
 * Test suite for the CardStep entity. Using a variety of Dialogue objects of various suitability for a card choice.
 * Also incorporates testing for the edge case of an empty Dialogue object.
 *
 * @author Aurora Zhang
 * @version 2.0
 * @since November 27th, 2022
 */

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

public class CardStepTests {
    CardStep card;
    WozMessage user1;
    WozMessage chat1;
    WozMessage user2;
    WozMessage chat2;
    WozMessage user3;
    WozMessage chat3;

    WozMessage user4;
    WozMessage chat4;

    // initialize arrays to an empty ArrayList otherwise default value is null.
    ArrayList<WozMessage> userMsgs1 = new ArrayList<>();
    ArrayList<WozMessage> chatbotMsgs1 = new ArrayList<>();
    ArrayList<WozMessage> userMsgs2 = new ArrayList<>();
    ArrayList<WozMessage> chatbotMsgs2 = new ArrayList<>();
    ArrayList<WozMessage> userMsgs3 = new ArrayList<>();
    ArrayList<WozMessage> chatbotMsgs3 = new ArrayList<>();

    ArrayList<WozMessage> chatbotMsgs4 = new ArrayList<>();

    ArrayList<WozMessage> userMsgs4 = new ArrayList<>();

    Dialogue<?> dialogue1;
    Dialogue<?> dialogue2;
    Dialogue<?> dialogue3;

    Dialogue<?> dialogue4;


    @BeforeEach
    void init(){
        card = new CardStep();
        user1 = new WozMessage("request", "i am looking for the most famous art gallery in this area." +
                "can you tell me some suggestions?");
        chat1 = new WozMessage("response", "there are many near you. choose the one you would like.");
        user2 = new WozMessage("request", "can you find the ones that have hotels nearby?");
        chat2 = new WozMessage("response", "yes. the regency hotel is near the fine arts museum. would" +
                "you like to choose this?");
        user3 = new WozMessage("request","yes. thank you");
        chat3 = new WozMessage("response", "okay it is booked. thank you and have a good day.");
        user4 = new WozMessage("request","");
        chat4 = new WozMessage("response","");

        chatbotMsgs1.add(chat1);
        chatbotMsgs2.add(chat2);
        chatbotMsgs3.add(chat3);
        chatbotMsgs4.add(chat4);



        userMsgs2.add(user2);
        userMsgs3.add(user3);
        userMsgs4.add(user4);


        dialogue1 = new Dialogue<>(chatbotMsgs1, userMsgs1);
        dialogue2 = new Dialogue<>(chatbotMsgs2, userMsgs2);
        dialogue3 = new Dialogue<>(chatbotMsgs3, userMsgs3);
        dialogue4 = new Dialogue<>(chatbotMsgs4, userMsgs4);
    }

    @AfterEach
    public void reset(){
        // because each message is added before each test, we must clear them to prevent it from repeating.
        userMsgs1.clear();
        chatbotMsgs1.clear();
        userMsgs2.clear();
        chatbotMsgs2.clear();
        userMsgs3.clear();
        chatbotMsgs3.clear();
        chatbotMsgs4.clear();
        userMsgs4.clear();

    }

    @Test
    void setZeroScoreAccumulator(){
        double expected = 0.0;
        card.runAnalysis(dialogue2);
        card.setZeroScoreAccumulator();
        double actual = card.getScoreAccumulator();
        assertEquals(expected, actual);

    }

    @Test
    void testGetMaxScore(){
        double expected = 15.0;
        double actual = card.getMaxScore();
        assertEquals(expected, actual);
    }

    @Test
    void testGetScoreAccumulator(){
        double expected = 0.0;
        double actual = card.getScoreAccumulator();
        assertEquals(expected, actual);

    }

    @Test
    void testCardRunAnalysisDialogue1(){
        card.runAnalysis(dialogue1);
        double expected = 11.0;
        double actual = card.getScoreAccumulator();
        assertEquals(expected, actual);
    }

    @Test
    void testCardRunAnalysisDialogue2(){
        card.runAnalysis(dialogue2);
        double expected = 10.0;
        double actual = card.getScoreAccumulator();
        assertEquals(expected, actual);

    }

    @Test
    void testCardRunAnalysisDialogue3(){
        card.runAnalysis(dialogue3);
        //this makes sense as dialogue2 is just ending the conversation
        double expected = 0.0;
        double actual = card.getScoreAccumulator();
        assertEquals(expected, actual);

    }

    @Test
    public void testCardRunAnalysisDialogue4Empty(){
        card.runAnalysis(dialogue4);
        double expected = 0.0;
        double actual = card.getScoreAccumulator();
        assertEquals(expected, actual);
    }



}
