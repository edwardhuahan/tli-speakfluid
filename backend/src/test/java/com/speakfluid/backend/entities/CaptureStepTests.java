package com.speakfluid.backend.entities;

import com.speakfluid.backend.entities.message.Dialogue;
import com.speakfluid.backend.entities.message.WozMessage;
import com.speakfluid.backend.entities.steps.CaptureStep;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for the CaptureStep class.
 *
 * @author  Sarah Xu
 * @version 1.0
 * @since   2022-11-27
 */

class CaptureStepTests {
    CaptureStep captureStep = new CaptureStep();
    static WozMessage m1;
    static WozMessage m2;
    static WozMessage m3;
    static WozMessage m4;
    static WozMessage m5;
    static WozMessage m6;
    static WozMessage m7;
    static WozMessage m8;
    static WozMessage m9;
    static Dialogue<WozMessage> emailDialogue;
    static Dialogue<WozMessage> dateDialogue;
    static Dialogue<WozMessage> locationDialogue;
    // Sets up a dialogues from one conversation about booking a hotel
    @BeforeAll
    static void setUp() {
        // m1 is ignored in our analysis, but it is included for the understanding of the conversation
        m1 = new WozMessage("request", "I need help with canceling my subscription");
        m2 = new WozMessage("response", "Please provide your name and account email");
        m3 = new WozMessage("request", "Jane doe, j.doe@company.com");
        m4 = new WozMessage("response", "When did your last box arrive?");
        m5 = new WozMessage("request", "Last tuesday night");
        m6 = new WozMessage("response", "Where was it shipped to?");
        m7 = new WozMessage("request", "45 St George Street, Toronto, ON, M5S 2E8");
        m8 = new WozMessage("response", "Thank you, Jane. Your subscription has been canceled.");
        m9 = new WozMessage("request", "great thanks");

        ArrayList<WozMessage> a1 = new ArrayList<>(Arrays.asList(m2));
        ArrayList<WozMessage> a2 = new ArrayList<>(Arrays.asList(m3));
        emailDialogue = new Dialogue<>(a1,a2);
        ArrayList<WozMessage> a3 = new ArrayList<>(Arrays.asList(m4));
        ArrayList<WozMessage> a4 = new ArrayList<>(Arrays.asList(m5));
        dateDialogue = new Dialogue<>(a3,a4);
        ArrayList<WozMessage> a5 = new ArrayList<>(Arrays.asList(m6));
        ArrayList<WozMessage> a6 = new ArrayList<>(Arrays.asList(m7));
        locationDialogue = new Dialogue<>(a5,a6);
    }

    /**
     * Tests for getters and setters
     * - getStepName
     * - getMaxScore
     * - getScoreAccumulator
     * - setZeroScoreAccumulator
     */
    @Test
    void testGetStepName() {
        assertEquals("Capture", captureStep.getStepName());
    }

    @Test
    void testGetMaxScore() {
        assertEquals(21.0, captureStep.getMaxScore());
    }

    @Test
    void testGetScoreAccumulator() {
        assertEquals(0.0, captureStep.getScoreAccumulator());
        captureStep.runAnalysis(emailDialogue);
        assertEquals(27.0, captureStep.getScoreAccumulator());
    }

    @Test
    void testSetZeroScoreAccumulator() {
        captureStep.runAnalysis(emailDialogue);
        assertEquals(27.0, captureStep.getScoreAccumulator());
        captureStep.setZeroScoreAccumulator();
        assertEquals(0.0, captureStep.getScoreAccumulator());
    }

    @Test
    void testRunAnalysis(){
        captureStep.runAnalysis(dateDialogue);
        assertEquals(16.0, captureStep.getScoreAccumulator());
    }

    @Test
    void testIsZipCode(){
        captureStep.isZipCode(m7);
        assertEquals(3.0, captureStep.getScoreAccumulator());
    }

    @Test
    void testIsEmail(){
        captureStep.isEmail(m3);
        assertEquals(3.0, captureStep.getScoreAccumulator());
    }

    @Test
    void testHasNumbers(){
        captureStep.hasNumbers(m7);
        assertEquals(1.0, captureStep.getScoreAccumulator());
    }

}