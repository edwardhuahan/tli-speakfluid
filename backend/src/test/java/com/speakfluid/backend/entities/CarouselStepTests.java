package com.speakfluid.backend.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Tests for the CarouselStep class.
 *
 * @author  Zoey Zhang
 * @version 1.0
 * @since   2022-11-26
 */
class CarouselStepTests extends TalkStepTest {
    static CarouselStep carouselStep = new CarouselStep();
    static Dialogue<WozMessage> d1;
    static ArrayList<WozMessage> botMsg1;
    static ArrayList<WozMessage> userMsg1;
    static WozMessage m1 = new WozMessage("response", "Hi, here are the recommended songs.");
    static WozMessage m2 = new WozMessage("response", "Album covers are included.");
    static WozMessage m3 = new WozMessage("request", "Great!");
    static WozMessage m4 = new WozMessage("request", "Thank you for the recommendation!");

    static Dialogue<WozMessage> d2;
    static ArrayList<WozMessage> botMsg2;
    static ArrayList<WozMessage> userMsg2;
    static WozMessage m5 = new WozMessage("response", "Hi, here is a list of matching products.");
    static WozMessage m6 = new WozMessage("response", "Pictures of them are shown.");

    @BeforeAll
    public static void setUp(){
        botMsg1 = new ArrayList<>(Arrays.asList(m1,m2));
        userMsg1 = new ArrayList<>(Arrays.asList(m3, m4));
        d1 = new Dialogue<>(botMsg1, userMsg1);
        botMsg2 = new ArrayList<>(Arrays.asList(m5,m6));
        userMsg2 = new ArrayList<>();
        d2 = new Dialogue<>(botMsg2, userMsg2);
    }

    @Test
    void testGetStepName() {
        assertEquals("Carousel", carouselStep.getStepName());
    }

    @Test
    void testGetMaxScore() {
        assertEquals(15.0, carouselStep.getMaxScore());
    }

    @Test
    void testGetScoreAccumulator() {
        assertEquals(0.0, carouselStep.getScoreAccumulator());
    }

    @Test
    void testSetZeroScoreAccumulator() {
        carouselStep.runAnalysis(d1);
        carouselStep.setZeroScoreAccumulator();
        assertEquals(0.0, carouselStep.getScoreAccumulator());
    }

    @Test
    void testRunAnalysisWithBothMessages() {
        carouselStep.runAnalysis(d1);
        double actualScore = carouselStep.getScoreAccumulator();
        assertEquals(13.0, actualScore);
    }

    @Test
    void testRunAnalysisWithBothMessagesWithOnlyBotMsg() {
        carouselStep.runAnalysis(d2);
        double actualScore = carouselStep.getScoreAccumulator();
        assertEquals(16.0, actualScore);
    }
}