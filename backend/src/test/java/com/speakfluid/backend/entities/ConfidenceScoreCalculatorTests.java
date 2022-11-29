package com.speakfluid.backend.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test suite for the ConfidenceScoreCalculator entity, using various TalkStep instances and Dialogue objects of
 * various suitability for each TalkStep object to test for a wide range of confidence scores.
 *
 * @author Aurora Zhang
 * @version 1.0
 * @since November 28th, 2022
 */

public class ConfidenceScoreCalculatorTests {

    static ConfidenceScoreCalculator calculator;

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

@BeforeAll
    public static  void setUp(){
    calculator = new ConfidenceScoreCalculator();
    button = new ButtonStep();
    card = new CardStep();

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


    dialogue1 = new Dialogue<WozMessage>(chatbotMsgs1, userMsgs1);
    dialogue2 = new Dialogue<WozMessage>(chatbotMsgs2, userMsgs2);
    dialogue3 = new Dialogue<WozMessage>(chatbotMsgs3, userMsgs3);
    dialogue4 = new Dialogue<WozMessage>(chatbotMsgs4, userMsgs4);
    }

    @Test
    public void testPassDialogue1ToTalkStepButton(){
        calculator.passDialogueToTalkStep(dialogue1, button);
        double actual = calculator.stepScoreAccumulator;
        double expected = 10.0;
        assertEquals(expected, actual);
    }

    @Test
    public void testPassDialogue1ToTalkStepCard(){
        calculator.passDialogueToTalkStep(dialogue1, card);
        double actual = calculator.stepScoreAccumulator;
        //makes sense the accumulated score is correspondingly low when this
        //dialogue was made for button suitability
        double expected = 3.0;
        assertEquals(expected, actual);
    }

    @Test
    public void testPassDialogue2ToTalkStepButton(){
        calculator.passDialogueToTalkStep(dialogue2, button);
        double actual = calculator.stepScoreAccumulator;
        double expected = 16.0;
        assertEquals(expected, actual);
    }

    @Test
    public void testPassDialogue2ToTalkStepCard(){
        calculator.passDialogueToTalkStep(dialogue2, card);
        double actual = calculator.stepScoreAccumulator;
        //the card step here can also be suitable as it is listing the nearby hospitals and images the user
        //can click on may be helpful
        double expected = 13.0;
        assertEquals(expected, actual);
    }

    // both tests have low accumulated scores because niether is supposed to be suitable for dialogue3.
    @Test
    public void testPassDialogue3ToTalkStepButton(){
        calculator.passDialogueToTalkStep(dialogue3, button);
        double actual = calculator.stepScoreAccumulator;
        double expected = 3.0;
        assertEquals(expected, actual);
    }

    @Test
    public void testPassDialogue3ToTalkStepCard(){
        calculator.passDialogueToTalkStep(dialogue3, card);
        double actual = calculator.stepScoreAccumulator;
        double expected = 4.0;
        assertEquals(expected, actual);
    }

    //Dialogue4 is empty so testing this edge case should ensure the score accumulated is 0 as desired.
    @Test
    public void testPassDialogue4ToTalkStepButton(){
        calculator.passDialogueToTalkStep(dialogue4, button);
        double actual = calculator.stepScoreAccumulator;
        double expected = 0.0;
        assertEquals(expected, actual);
    }

    @Test
    public void testPassDialogue4ToTalkStepCard(){
        calculator.passDialogueToTalkStep(dialogue4, card);
        double actual = calculator.stepScoreAccumulator;
        double expected = 0.0;
        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateConfidenceScoreButton1(){
        calculator.passDialogueToTalkStep(dialogue1, button);
        double actual = calculator.calculateConfidenceScore();
        double expected = 66.67;
        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateConfidenceScoreButton2(){
        calculator.passDialogueToTalkStep(dialogue2, button);
        double actual = calculator.calculateConfidenceScore();
        double expected = 95.0;
        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateConfidenceScoreButton3(){
        calculator.passDialogueToTalkStep(dialogue3, button);
        double actual = calculator.calculateConfidenceScore();
        //this makes sense that it is low as dialogue3 is ending the conversation
        double expected = 20.0;
        assertEquals(expected, actual);
    }
    @Test
    public void testCalculateConfidenceScoreButton4(){
        calculator.passDialogueToTalkStep(dialogue4, card);
        double actual = calculator.calculateConfidenceScore();
        double expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    public void testCalculateConfidenceScoreCard1(){
        calculator.passDialogueToTalkStep(dialogue1, card);
        double actual = calculator.calculateConfidenceScore();
        double expected = 20.0;
        assertEquals(expected, actual);
    }
    @Test
    public void testCalculateConfidenceScoreCard2(){
        calculator.passDialogueToTalkStep(dialogue2, card);
        double actual = calculator.calculateConfidenceScore();
        double expected = 86.67;
        assertEquals(expected, actual);
    }
    @Test
    public void testCalculateConfidenceScoreCard3(){
        calculator.passDialogueToTalkStep(dialogue3, card);
        double actual = calculator.calculateConfidenceScore();
        double expected = 26.67;
        assertEquals(expected, actual);
    }
    @Test
    public void testCalculateConfidenceScoreCard4(){
        calculator.passDialogueToTalkStep(dialogue4, card);
        double actual = calculator.calculateConfidenceScore();
        double expected = 0;
        assertEquals(expected, actual);
    }

}
