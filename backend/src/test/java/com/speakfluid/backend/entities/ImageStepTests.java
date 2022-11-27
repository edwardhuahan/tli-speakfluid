package com.speakfluid.backend.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the ImageStep class.
 *
 * @author  Zoey Zhang
 * @version 1.0
 * @since   2022-11-26
 */
class ImageStepTests extends TalkStepTest {
    static ImageStep imageStep = new ImageStep();
    static Dialogue<WozMessage> d1;
    static Dialogue<WozMessage> d2;
    static Dialogue<WozMessage> d3;
    static ArrayList<WozMessage> botMsg1;
    static ArrayList<WozMessage> userMsg1;
    static ArrayList<WozMessage> botMsg2;
    static ArrayList<WozMessage> userMsg2;
    static ArrayList<WozMessage> userMsg3;
    static WozMessage m1 = new WozMessage("text", "Hi, we have following locations.");
    static WozMessage m2 = new WozMessage("text", "Which store would you like to go?");
    static WozMessage m3 = new WozMessage("text", "Hi,");
    static WozMessage m4 = new WozMessage("text", "Can I have the direction to the nearest store?");
    static WozMessage m5 = new WozMessage("text", "no");



    @BeforeAll
    public static void setUp(){
        botMsg1 = new ArrayList<>(Arrays.asList(m1,m2));
        userMsg1 = new ArrayList<>(Arrays.asList(m3, m4));
        d1 = new Dialogue<>(botMsg1, userMsg1);
        botMsg2 = new ArrayList<>(Arrays.asList(m1,m2));
        userMsg2 = new ArrayList<>();
        d2 = new Dialogue<>(botMsg2, userMsg2);
        userMsg3 = new ArrayList<>(Arrays.asList(m5));
        d3 = new Dialogue<>(botMsg2, userMsg3);

    }

    @Test
    void testGetStepName() {
        assertEquals("Image", imageStep.getStepName());
    }

    @Test
    void testGetMaxScore() {
        assertEquals(20.0, imageStep.getMaxScore());
    }

    @Test
    void testGetScoreAccumulator() {
        assertEquals(16.0, imageStep.getScoreAccumulator());
    }

    @Test
    void testSetZeroScoreAccumulator() {
        imageStep.runAnalysis(d1);
        imageStep.setZeroScoreAccumulator();
        assertEquals(0.0, imageStep.getScoreAccumulator());
    }
    @Test
    void testRunAnalysisWithBothMatches() {
        imageStep.runAnalysis(d1);
        double actualScore = imageStep.getScoreAccumulator();
        assertEquals(16.0, actualScore);
    }
    @Test
    void testRunAnalysisWithBotMsgMatch() {
        imageStep.runAnalysis(d3);
        double actualScore = imageStep.getScoreAccumulator();
        assertEquals(4.0, actualScore);
    }
    @Test
    void testRunAnalysisWithUserMsgEmpty() {
        imageStep.runAnalysis(d2);
        double actualScore = imageStep.getScoreAccumulator();
        assertEquals(4.0, actualScore);
    }
}