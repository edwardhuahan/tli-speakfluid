package com.speakfluid.backend.entities;
import com.speakfluid.backend.entities.steps.*;
import com.speakfluid.backend.entities.message.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Test suite for the ButtonStep entity. Using a variety of Dialogue objects of various suitability for a button choice.
 * Also incorporates testing for the edge case of an empty Dialogue object.
 *
 * @author Aurora Zhang
 * @version 2.0
 * @since November 25th, 2022
 */



public class ButtonStepTests {
    ButtonStep button;
    WozMessage user1;
    WozMessage chat1;
    WozMessage user2;
    WozMessage chat2;
    WozMessage user3;
    WozMessage chat3;

    WozMessage chat4;

    WozMessage user4;

    // initialize array to an empty ArrayList otherwise default value is null.
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
    void init() {
        //initialize each object with a value before each test.
        button = new ButtonStep();
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

    @AfterEach
    public void reset() {
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
    void testGetStepName(){
        String expected = "Button";
        String actual = button.getStepName();
        assertEquals(expected, actual);
    }

    @Test
    void setZeroScoreAccumulator(){
        double expected = 0.0;
        button.runAnalysis(dialogue1);
        button.setZeroScoreAccumulator();
        double actual = button.getScoreAccumulator();
        assertEquals(expected, actual);

    }

    @Test
    void testGetMaxScore(){
        double expected = 15.0;
        double actual = button.getMaxScore();
        assertEquals(expected, actual);
    }

    @Test
    void testGetScoreAccumulator(){
        double expected = 0.0;
        double actual = button.getScoreAccumulator();
        assertEquals(expected, actual);

    }

    /*the scoreAccumulator is allowed to exceed the maxscore in our program, in which case
    the capping mechanism in ConfidenceScoreCalculator will revert it to a rate of 95%
     */

    @Test
    void testButtonRunAnalysisDialogue1(){
        button.runAnalysis(dialogue1);
        double expected = 10.0;
        double actual = button.getScoreAccumulator();
        assertEquals(expected, actual);
    }

    @Test
    public void testButtonRunAnalysisDialogue2(){
        button.runAnalysis(dialogue2);
        double expected = 16.0;
        double actual = button.getScoreAccumulator();
        assertEquals(expected, actual);
    }

    @Test
    public void testButtonRunAnalysisDialogue3(){
        button.runAnalysis(dialogue3);
        double expected = 3.0;
        double actual = button.getScoreAccumulator();
        assertEquals(expected, actual);
    }

    @Test
    public void testButtonRunAnalysisDialogue4Empty(){
        button.runAnalysis(dialogue4);
        double expected = 0.0;
        double actual = button.getScoreAccumulator();
        assertEquals(expected, actual);
    }

}
