package com.speakfluid.backend.entities;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ImageStepTest extends TalkStepTest {
    private ImageStep imageStep = new ImageStep();
    private Dialogue<WozMessage> d1;
    private ArrayList<WozMessage> botMsg;
    private ArrayList<WozMessage> userMsg;
    private WozMessage m1 = new WozMessage("text", "Hi, I am your personal chatbot!");
    private WozMessage m2 = new WozMessage("text", "What can I help you today?");
    private WozMessage m3 = new WozMessage("text", "Hi,");
    private WozMessage m4 = new WozMessage("text", "Can I have the direction to the store?");



    @BeforeEach
    void init(){
        botMsg = new ArrayList<>(Arrays.asList(m1,m2));
        userMsg = new ArrayList<>(Arrays.asList(m3, m4));
        d1 = new Dialogue<>(botMsg, userMsg);

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void calculateMsgLength() {
    }

    @Test
    void countMatchKeywords() {
    }

    @Test
    void runAnalysis() {
        imageStep.runAnalysis(d1);
        double actualScore = imageStep.getScoreAccumulator();
        assertEquals(10.0, actualScore);
    }

    @Test
    void getStepName() {
        assertEquals("Image", imageStep.getStepName());
    }

    @Test
    void getMaxScore() {
        assertEquals(20.0, imageStep.getMaxScore());
    }

    @Test
    void getScoreAccumulator() {
    }

    @Test
    void setZeroScoreAccumulator() {
        imageStep.runAnalysis(d1);
        imageStep.setZeroScoreAccumulator();
        assertEquals(0.0, imageStep.getScoreAccumulator());
    }
}