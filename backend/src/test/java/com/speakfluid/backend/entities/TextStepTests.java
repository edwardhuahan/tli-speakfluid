package com.speakfluid.backend.entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class TextStepTests extends TalkStepTest {
    static CarouselStep carouselStep = new CarouselStep();
    static Dialogue<WozMessage> d1;
    static ArrayList<WozMessage> botMsg;
    static ArrayList<WozMessage> userMsg;
    static WozMessage m1 = new WozMessage("response", "Hi, here are the recommended songs.");
    static WozMessage m2 = new WozMessage("response", "Album covers are included.");
    static WozMessage m3 = new WozMessage("request", "Great!");

    @Test
    void getStepName() {
    }

    @Test
    void getMaxScore() {
    }

    @Test
    void getScoreAccumulator() {
    }

    @Test
    void setZeroScoreAccumulator() {
    }

    @Test
    void calculateMsgLength() {
    }

    @Test
    void countMatchKeywords() {
    }

    @Test
    void testGetStepName() {
    }

    @Test
    void testGetMaxScore() {
    }

    @Test
    void testGetScoreAccumulator() {
    }

    @Test
    void testSetZeroScoreAccumulator() {
    }

    @Test
    void isNotQuestion() {
    }

    @Test
    void calculateConsecutiveBotMsg() {
    }

    @Test
    void runAnalysis() {
    }
}