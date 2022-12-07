package com.speakfluid.backend.entities;

import com.speakfluid.backend.entities.message.Dialogue;
import com.speakfluid.backend.entities.message.WozMessage;
import com.speakfluid.backend.entities.steps.ImageStep;
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
class ImageStepTests {
    // Class to be tested
    static ImageStep imageStep;

    // Test Data
    static Dialogue<WozMessage> d1;
    static Dialogue<WozMessage> d2;
    static Dialogue<WozMessage> d3;

    static ArrayList<WozMessage> botMsg1;
    static ArrayList<WozMessage> userMsg1;
    static ArrayList<WozMessage> botMsg2;
    static ArrayList<WozMessage> userMsg2;
    static ArrayList<WozMessage> userMsg3;

    static WozMessage m1;
    static WozMessage m2;
    static WozMessage m3;
    static WozMessage m4;
    static WozMessage m5;


    @BeforeAll
    public static void setUp(){
        imageStep = new ImageStep();

        m1 = new WozMessage("response", "Hi, we have following locations.");
        m2 = new WozMessage("response", "Which store would you like to go?");
        m3 = new WozMessage("request", "Hi,");
        m4 = new WozMessage("request", "Can I have the direction to the nearest store?");
        m5 = new WozMessage("request", "no");

        botMsg1 = new ArrayList<>(Arrays.asList(m1,m2));
        userMsg1 = new ArrayList<>(Arrays.asList(m3, m4));
        botMsg2 = new ArrayList<>(Arrays.asList(m1,m2));
        userMsg2 = new ArrayList<>();
        userMsg3 = new ArrayList<>(Arrays.asList(m5));

        d1 = new Dialogue<>(botMsg1, userMsg1);
        d2 = new Dialogue<>(botMsg2, userMsg2);
        d3 = new Dialogue<>(botMsg1, userMsg3);

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
        assertEquals(0.0, imageStep.getScoreAccumulator());
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
        imageStep.setZeroScoreAccumulator();
        assertEquals(12.0, actualScore);
    }
    @Test
    void testRunAnalysisWithBotMsgMatch() {
        imageStep.runAnalysis(d3);
        double actualScore = imageStep.getScoreAccumulator();
        imageStep.setZeroScoreAccumulator();
        assertEquals(4.0, actualScore);
    }
    @Test
    void testRunAnalysisWithUserMsgEmpty() {
        imageStep.runAnalysis(d2);
        double actualScore = imageStep.getScoreAccumulator();
        imageStep.setZeroScoreAccumulator();
        assertEquals(4.0, actualScore);
    }
}