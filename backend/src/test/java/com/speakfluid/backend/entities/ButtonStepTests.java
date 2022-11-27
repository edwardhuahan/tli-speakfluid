package com.speakfluid.backend.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test suite for the ButtonStep entity. Only tests CardStep's own implemented methods, using
 * mock values for its dependencies to ensure transparency in that we are only testing this entity.
 *
 * @author Aurora Zhang
 * @version 1.0
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

    private final List<Map<String, Double>> chatbotKeywordsScoreMap = Arrays.asList(
            Map.ofEntries(entry("would you", ScoreStandards.highMatch), entry("would it", ScoreStandards.highMatch),
                    entry("what type", ScoreStandards.mediumMatch), entry("what kind", ScoreStandards.mediumMatch),
                    entry("are you", ScoreStandards.mediumMatch), entry("do you", ScoreStandards.lowMatch),
                    entry("here is", ScoreStandards.lowMatch)),
            Map.ofEntries(entry("here are", ScoreStandards.highMatch), entry("choose", ScoreStandards.highMatch),
                    entry("select", ScoreStandards.highMatch)),
            Map.ofEntries(entry("destination", ScoreStandards.mediumMatch), entry("date", ScoreStandards.mediumMatch),
                    entry("departure", ScoreStandards.mediumMatch),
                    entry("arriving", ScoreStandards.mediumMatch)));
    private final List<Map<String, Double>> userKeywordsScoreMap = Arrays.asList(
            Map.ofEntries(entry("booking", ScoreStandards.lowMatch),
                    entry("train", ScoreStandards.lowMatch), entry("go to", ScoreStandards.lowMatch),
                    entry("arrive", ScoreStandards.lowMatch)),
            Map.ofEntries(entry("hotel", ScoreStandards.highMatch), entry("cheap", ScoreStandards.highMatch),
                    entry("hospital", ScoreStandards.highMatch)));

    // initialize array to an empty ArrayList otherwise default value is null.
    ArrayList<WozMessage> userMsgs = new ArrayList<>();
    ArrayList<WozMessage> chatbotMsgs = new ArrayList<>();

    Dialogue dialogue1;

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

        userMsgs.addAll(Arrays.asList(user1, user2, user3));
        chatbotMsgs.addAll(Arrays.asList(chat1, chat2, chat3));

        dialogue1 = new Dialogue(chatbotMsgs, userMsgs);

    }

    @AfterEach
    public void reset() {
        // because each message is added before each test, we must clear them to prevent it from repeating.
        userMsgs.clear();
        chatbotMsgs.clear();
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
        double expected = 21.0;
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
    void testRunAnalysisWithEntireDialogue(){
        TalkStep talkstep = mock(TalkStep.class);
        Mockito.when(talkstep.calculateMsgLength(user1)).thenReturn(10);
        Mockito.when(talkstep.calculateMsgLength(user2)).thenReturn(18);
        Mockito.when(talkstep.calculateMsgLength(user3)).thenReturn(9);

        Mockito.when(talkstep.calculateMsgLength(chat1)).thenReturn(18);
        Mockito.when(talkstep.calculateMsgLength(chat2)).thenReturn(15);
        Mockito.when(talkstep.calculateMsgLength(chat3)).thenReturn(6);

        Mockito.when(talkstep.countMatchKeywords(user1, userKeywordsScoreMap)).thenReturn(0);
        Mockito.when(talkstep.countMatchKeywords(user2, userKeywordsScoreMap)).thenReturn(6);
        Mockito.when(talkstep.countMatchKeywords(user3, userKeywordsScoreMap)).thenReturn(1);

        Mockito.when(talkstep.countMatchKeywords(chat1, chatbotKeywordsScoreMap)).thenReturn(10);
        Mockito.when(talkstep.countMatchKeywords(chat2, chatbotKeywordsScoreMap)).thenReturn(10);
        Mockito.when(talkstep.countMatchKeywords(chat3, chatbotKeywordsScoreMap)).thenReturn(0);

        button.runAnalysis(dialogue1);
        double expected = 29.0;
        double actual = button.getScoreAccumulator();
        assertEquals(expected, actual);
    }

}
