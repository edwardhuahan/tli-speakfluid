package com.speakfluid.backend.entities;
import com.speakfluid.backend.entities.steps.*;
import com.speakfluid.backend.entities.message.*;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test suite for the ConfidenceScoreOptimizer class, which tests all TalkStep entities using a variety of Dialogues
 * suited for different TalkSteps. Tests the methods callConfidenceScoreCalculator and rankTalkSteps.
 *
 * @author Aurora Zhang
 * @version 1.0
 * @since November 28th, 2022
 */

public class ConfidenceScoreOptimizerTests {
    static WozMessage user1;
    static WozMessage chat1;
    static WozMessage user2;
    static WozMessage chat2;
    static WozMessage user3;
    static WozMessage chat3;
    static WozMessage chat4;
    static WozMessage user4;

    static ArrayList<WozMessage> userMsgs1 = new ArrayList<>();
    static ArrayList<WozMessage> chatbotMsgs1 = new ArrayList<>();
    static ArrayList<WozMessage> userMsgs2 = new ArrayList<>();
    static ArrayList<WozMessage> chatbotMsgs2 = new ArrayList<>();
    static ArrayList<WozMessage> userMsgs3 = new ArrayList<>();
    static ArrayList<WozMessage> chatbotMsgs3 = new ArrayList<>();

    static ArrayList<WozMessage> userMsgs4 = new ArrayList<>();
    static ArrayList<WozMessage> chatbotMsgs4 = new ArrayList<>();

    static Dialogue<?> dialogue1;
    static Dialogue<?> dialogue2;
    static Dialogue<?> dialogue3;
    static Dialogue<?> dialogue4;

    static ButtonStep button;
    static CardStep card;
    static TextStep text;
    static CarouselStep carousel;
    static CaptureStep capture;
    static ImageStep image;
    static ChoiceStep choice;

    static ArrayList<TalkStep> talkStepList = new ArrayList<>();

    static ConfidenceScoreOptimizer optimizer;
    static ConfidenceScoreCalculator calculator2;

    @BeforeAll
    static void setUp(){

        user1 = new WozMessage("request", "good evening, i need help with" +
                "my lower back pain.");
        chat1 = new WozMessage("response", "here are some possible remedies for lower" +
                "back pain. would you like me to list them for you?");
        user2 = new WozMessage("request", "no, i think i need to go to the hospital. how " +
                "do i go to the nearest one?");
        chat2 =  new WozMessage("response", "here are a list of your nearest hospitals. " +
                "choose the one that you would like");
        user3 = new WozMessage("request", "i will go to the first one thank you.");
        chat3 = new WozMessage("response", "you're welcome. have a nice day.");
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

    @BeforeEach
    void init(){
        button = new ButtonStep();
        card = new CardStep();
        text = new TextStep();
        carousel = new CarouselStep();
        capture = new CaptureStep();
        image = new ImageStep();
        choice = new ChoiceStep();

        Collections.addAll(talkStepList, button, card, carousel, capture, image, choice, text);
        calculator2 = new ConfidenceScoreCalculator();
        optimizer = new ConfidenceScoreOptimizer(calculator2, talkStepList);

    }

    @AfterEach
    public void reset(){
        talkStepList.clear();
    }
    @Test
    public void testCallConfidenceScoreOptimizer1(){
        optimizer.callConfidenceScoreCalculator(dialogue1);
        HashMap<String, Double> actual = optimizer.talkStepToScoreMapping;
        HashMap<String, Double> expected = new HashMap<>(){{
            put("Button", 66.67);
            put("Carousel", 68.75);
            put("Image", 0.0);
            put("Capture", 0.0);
            put("Card", 20.0);
            put("Choice", 66.67);
            put("Text", 16.95);
        }};
        assertEquals(expected, actual);

    }

    @Test
    public void testCallConfidenceScoreOptimizer2(){
        optimizer.callConfidenceScoreCalculator(dialogue2);
        HashMap<String, Double> actual = optimizer.talkStepToScoreMapping;
        HashMap<String, Double> expected = new HashMap<>(){{
            put("Button", 95.0);
            put("Carousel", 95.0);
            put("Image", 0.0);
            put("Capture", 0.0);
            put("Card", 86.67);
            put("Text", 25.42);
            put("Choice", 33.33);
        }};
        assertEquals(expected, actual);

    }

    @Test
    public void testCallConfidenceScoreOptimizer3(){
        optimizer.callConfidenceScoreCalculator(dialogue3);
        HashMap<String, Double> actual = optimizer.talkStepToScoreMapping;
        HashMap<String, Double> expected = new HashMap<>(){{
            put("Button", 20.0);
            put("Carousel", 31.25);
            put("Image", 0.0);
            put("Capture", 4.76);
            put("Card", 26.67);
            put("Text", 16.95);
            put("Choice", 0.0);
        }};
        assertEquals(expected, actual);

    }

    @Test
    public void testCallConfidenceScoreOptimizer4(){
        optimizer.callConfidenceScoreCalculator(dialogue4);
        HashMap<String, Double> actual = optimizer.talkStepToScoreMapping;
        HashMap<String, Double> expected = new HashMap<>(){{
            put("Button", 0.0);
            put("Carousel", 0.0);
            put("Image", 0.0);
            put("Capture", 0.0);
            put("Card", 0.0);
            put("Choice", 0.0);
            put("Text", 16.95);
        }};
        assertEquals(expected, actual);

    }

    @Test
    public void testRankTalkSteps1(){
        optimizer.callConfidenceScoreCalculator(dialogue1);
        ArrayList<Map<String, Double>> actual = optimizer.rankTalkSteps();
        HashMap<String, Double> step1 = new HashMap<>();
        HashMap<String, Double> step2 = new HashMap<>();
        HashMap<String, Double> step3 = new HashMap<>();
        HashMap<String, Double> step4 = new HashMap<>();
        HashMap<String, Double> step5 = new HashMap<>();
        HashMap<String, Double> step6 = new HashMap<>();
        HashMap<String, Double> step7 = new HashMap<>();

        step1.put("Carousel", 68.75);
        step2.put("Button", 66.67);
        step3.put("Card", 20.0);
        step4.put("Capture", 0.0);
        step5.put("Image", 0.0);
        step6.put("Text", 16.95);
        step7.put("Choice", 66.67);

        ArrayList<Map<String, Double>> expected = new ArrayList<>();
        Collections.addAll(expected, step1, step2, step7, step3, step6, step4, step5);
        assertEquals(expected, actual);

    }

    @Test
    public void testRankTalkSteps2(){
        optimizer.callConfidenceScoreCalculator(dialogue2);
        ArrayList<Map<String, Double>> actual = optimizer.rankTalkSteps();
        HashMap<String, Double> step1 = new HashMap<>();
        HashMap<String, Double> step2 = new HashMap<>();
        HashMap<String, Double> step3 = new HashMap<>();
        HashMap<String, Double> step4 = new HashMap<>();
        HashMap<String, Double> step5 = new HashMap<>();
        HashMap<String, Double> step6 = new HashMap<>();
        HashMap<String, Double> step7 = new HashMap<>();

        step1.put("Carousel", 95.0);
        step2.put("Button", 95.0);
        step3.put("Card", 86.67);
        step4.put("Capture", 0.0);
        step5.put("Image", 0.0);
        step6.put("Text", 25.42);
        step7.put("Choice", 33.33);

        ArrayList<Map<String, Double>> expected = new ArrayList<>();
        Collections.addAll(expected, step1, step2, step3, step7, step6, step4, step5);
        assertEquals(expected, actual);

    }

    @Test
    public void testRankTalkSteps3(){
        optimizer.callConfidenceScoreCalculator(dialogue3);
        ArrayList<Map<String, Double>> actual = optimizer.rankTalkSteps();
        HashMap<String, Double> step1 = new HashMap<>();
        HashMap<String, Double> step2 = new HashMap<>();
        HashMap<String, Double> step3 = new HashMap<>();
        HashMap<String, Double> step4 = new HashMap<>();
        HashMap<String, Double> step5 = new HashMap<>();
        HashMap<String, Double> step6 = new HashMap<>();
        HashMap<String, Double> step7 = new HashMap<>();

        step1.put("Carousel", 31.25);
        step2.put("Button", 20.0);
        step3.put("Card", 26.67);
        step4.put("Capture", 4.76);
        step5.put("Image", 0.0);
        step6.put("Text", 16.95);
        step7.put("Choice", 0.0);

        ArrayList<Map<String, Double>> expected = new ArrayList<>();
        Collections.addAll(expected, step1, step3, step2, step6, step4, step5, step7);
        assertEquals(expected, actual);

    }

    @Test
    public void testRankTalkSteps4(){
        optimizer.callConfidenceScoreCalculator(dialogue4);
        ArrayList<Map<String, Double>> actual = optimizer.rankTalkSteps();
        HashMap<String, Double> step1 = new HashMap<>();
        HashMap<String, Double> step2 = new HashMap<>();
        HashMap<String, Double> step3 = new HashMap<>();
        HashMap<String, Double> step4 = new HashMap<>();
        HashMap<String, Double> step5 = new HashMap<>();
        HashMap<String, Double> step6 = new HashMap<>();
        HashMap<String, Double> step7 = new HashMap<>();

        step1.put("Carousel", 0.0);
        step2.put("Button", 0.0);
        step3.put("Card", 0.0);
        step4.put("Capture", 0.0);
        step5.put("Image", 0.0);
        step6.put("Text", 16.95);
        step7.put("Choice", 0.0);

        ArrayList<Map<String, Double>> expected = new ArrayList<>();
        Collections.addAll(expected, step6, step3, step4, step5, step1, step2, step7);
        assertEquals(expected, actual);

    }


}
