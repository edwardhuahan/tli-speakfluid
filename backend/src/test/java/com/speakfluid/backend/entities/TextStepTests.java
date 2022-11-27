package com.speakfluid.backend.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class TextStepTests extends TalkStepTest {
    static TextStep textStep = new TextStep();
    static Dialogue<WozMessage> d1;
    static Dialogue<WozMessage> d2;
    static ArrayList<WozMessage> botMsg1;
    static ArrayList<WozMessage> userMsg1;
    static ArrayList<WozMessage> userMsg2;
    static WozMessage m1 = new WozMessage("response", "Hi, here are the recommended songs.");
    static WozMessage m2 = new WozMessage("response", "What else can I help you with today?");
    static WozMessage m3 = new WozMessage("request", "Great!");
    static WozMessage m4 = new WozMessage("request", "Thank you for the recommendation!");
    @BeforeAll
    public static void setUp(){
        botMsg1 = new ArrayList<>(Arrays.asList(m1,m2));
        userMsg1 = new ArrayList<>(Arrays.asList(m3, m4));
        userMsg2 = new ArrayList<>(Arrays.asList(m3));
        d1 = new Dialogue<>(botMsg1, userMsg1);
        d2 = new Dialogue<>(botMsg1, userMsg2);

    }
    @Test
    void testGetStepName() {
        assertEquals("Text", textStep.getStepName());
    }

    @Test
    void testGetMaxScore() {
        assertEquals(26.0, textStep.getMaxScore());
    }

    @Test
    void testGetScoreAccumulator() {
        assertEquals(0.0, textStep.getScoreAccumulator());
    }

    @Test
    void setZeroScoreAccumulator() {
        textStep.runAnalysis(d1);
        textStep.setZeroScoreAccumulator();
        assertEquals(0.0, textStep.getScoreAccumulator());
    }

    @Test
    void testIsNotQuestionNoQuestion() {
        assertEquals(5.0, textStep.isNotQuestion(m1));
    }

    @Test
    void testIsNotQuestionWithQuestion() {
        assertEquals(0.0, textStep.isNotQuestion(m2));
    }

    @Test
    void testCalculateConsecutiveBotMsgNoCon() {
        assertEquals(0, textStep.calculateConsecutiveBotMsg(d1));
    }

    @Test
    void testCalculateConsecutiveBotMsgCon() {
        assertEquals(1, textStep.calculateConsecutiveBotMsg(d2));
    }

    @Test
    void testRunAnalysis() {
        textStep.runAnalysis(d1);
        double actualScore = textStep.getScoreAccumulator();
        textStep.setZeroScoreAccumulator();
        assertEquals(6.0, actualScore);
    }
}