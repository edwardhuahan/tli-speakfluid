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
    static ArrayList<WozMessage> botMsg;
    static ArrayList<WozMessage> userMsg;
    static WozMessage m1 = new WozMessage("text", "Hi, I am your personal chatbot!");
    static WozMessage m2 = new WozMessage("text", "What can I help you today?");
    static WozMessage m3 = new WozMessage("text", "Hi,");
    static WozMessage m4 = new WozMessage("text", "Can I have the direction to the store?");



    @BeforeAll
    public static void setUp(){
        botMsg = new ArrayList<>(Arrays.asList(m1,m2));
        userMsg = new ArrayList<>(Arrays.asList(m3, m4));
        d1 = new Dialogue<>(botMsg, userMsg);

    }

    @Test
    void testRunAnalysis() {
        imageStep.runAnalysis(d1);
        double actualScore = imageStep.getScoreAccumulator();
        assertEquals(10.0, actualScore);
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
    }

    @Test
    void testSetZeroScoreAccumulator() {
        imageStep.runAnalysis(d1);
        imageStep.setZeroScoreAccumulator();
        assertEquals(0.0, imageStep.getScoreAccumulator());
    }
}