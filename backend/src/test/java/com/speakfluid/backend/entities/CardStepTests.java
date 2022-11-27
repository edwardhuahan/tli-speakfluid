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
 * Test suite for the CardStep entity. Only tests CardStep's own implemented methods, using
 * mock values for its dependencies to ensure transparency in that we are only testing this entity.
 *
 * @author Aurora Zhang
 * @version 1.0
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

    // initialize array to an empty ArrayList otherwise default value is null.
    ArrayList<WozMessage> userMsgs = new ArrayList<>();
    ArrayList<WozMessage> chatbotMsgs = new ArrayList<>();

    Dialogue dialogue2;

    private final List<Map<String, Double>> chatbotKeywordsScoreMap = Arrays.asList(
            Map.ofEntries(entry("can i help", ScoreStandards.highMatch),
                    entry("can i do", ScoreStandards.mediumMatch)),
            Map.ofEntries(entry("here are", ScoreStandards.mediumMatch), entry("there are", ScoreStandards.mediumMatch),
                    entry("what kind", ScoreStandards.mediumMatch), entry("do you", ScoreStandards.lowMatch),
                    entry("here is", ScoreStandards.lowMatch)),
            Map.ofEntries(entry("ticket", ScoreStandards.mediumMatch), entry("area", ScoreStandards.mediumMatch),
                    entry("departure", ScoreStandards.mediumMatch),
                    entry("leaves", ScoreStandards.mediumMatch)),
            Map.ofEntries(entry("image", ScoreStandards.highMatch), entry("picture", ScoreStandards.highMatch)),
            Map.ofEntries(entry("choose", ScoreStandards.highMatch), entry("select", ScoreStandards.highMatch),
                    entry("pick", ScoreStandards.highMatch)));
    private final List<Map<String, Double>> userKeywordsScoreMap = Arrays.asList(
            Map.ofEntries(entry("museum", ScoreStandards.highMatch),
                    entry("gallery", ScoreStandards.highMatch), entry("restaurant", ScoreStandards.highMatch),
                    entry("hospital", ScoreStandards.highMatch), entry("clinic", ScoreStandards.mediumMatch),
                    entry("hotel", ScoreStandards.mediumMatch), entry("attraction", ScoreStandards.mediumMatch),
                    entry("entertainment", ScoreStandards.mediumMatch), entry("food", ScoreStandards.mediumMatch)),
            Map.ofEntries(entry("bus", ScoreStandards.highMatch), entry("taxi", ScoreStandards.highMatch),
                    entry("train", ScoreStandards.highMatch)));

    @BeforeEach
    void init(){
        card = new CardStep();
        user1 = new WozMessage("SNG990", "i am looking for the most famous art gallery in this area." +
                "can you tell me some suggestions?");
        chat1 = new WozMessage("SNG990", "there are many near you. choose the one you would like.");
        user2 = new WozMessage("SNG990", "can you find the ones that have hotels nearby?");
        chat2 = new WozMessage("SNG990", "yes. the regency hotel is near the fine arts museum. would" +
                "you like to choose this?");
        user3 = new WozMessage("SNG990","yes. thank you");
        chat3 = new WozMessage("SNG990", "okay it is booked. thank you and have a good day.");

        userMsgs.addAll(Arrays.asList(user1, user2, user3));
        chatbotMsgs.addAll(Arrays.asList(chat1, chat2, chat3));

        dialogue2 = new Dialogue(chatbotMsgs, userMsgs);
    }

    @AfterEach
    public void reset(){
        card.setZeroScoreAccumulator();
        // because each message is added before each test, we must clear them to prevent it from repeating.
        userMsgs.clear();
        chatbotMsgs.clear();

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
        double expected = 21.0;
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
    void testRunAnalysisWithEntireDialogue(){
        card.runAnalysis(dialogue2);
        double expected = 28.0;
        double actual = card.getScoreAccumulator();
        assertEquals(expected, actual);
    }

    @Test
    void testRunAnalysis(){
        TalkStep talkstep = mock(TalkStep.class);
        Mockito.when(talkstep.calculateMsgLength(user1)).thenReturn(18);
        Mockito.when(talkstep.calculateMsgLength(user2)).thenReturn(9);
        Mockito.when(talkstep.calculateMsgLength(user3)).thenReturn(3);

        Mockito.when(talkstep.calculateMsgLength(chat1)).thenReturn(11);
        Mockito.when(talkstep.calculateMsgLength(chat2)).thenReturn(16);
        Mockito.when(talkstep.calculateMsgLength(chat3)).thenReturn(11);

        Mockito.when(talkstep.countMatchKeywords(user1, userKeywordsScoreMap)).thenReturn(5);
        Mockito.when(talkstep.countMatchKeywords(user2, userKeywordsScoreMap)).thenReturn(3);
        Mockito.when(talkstep.countMatchKeywords(user3, userKeywordsScoreMap)).thenReturn(0);

        Mockito.when(talkstep.countMatchKeywords(chat1, chatbotKeywordsScoreMap)).thenReturn(5);
        Mockito.when(talkstep.countMatchKeywords(chat2, chatbotKeywordsScoreMap)).thenReturn(5);
        Mockito.when(talkstep.countMatchKeywords(chat3, chatbotKeywordsScoreMap)).thenReturn(0);

        card.runAnalysis(dialogue2);
        double expected = 28.0;
        double actual = card.getScoreAccumulator();
        assertEquals(expected, actual);
    }



}
