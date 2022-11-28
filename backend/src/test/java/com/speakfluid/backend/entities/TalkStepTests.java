package com.speakfluid.backend.entities;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

/**
 * Tests for the Dialogue class.
 *
 * @author  Sarah Xu
 * @version 1.0
 * @since   2022-11-25
 */

class TalkStepTests {
    static TalkStep talkStep;
    static TalkStep choiceTalkStep;
    static WozMessage m1;
    static WozMessage m2;
    static WozMessage m3;
    static WozMessage m4;
    static WozMessage m5;
    static WozMessage m6;
    static WozMessage m7;
    static WozMessage m8;
    static WozMessage m9;
    static Dialogue<WozMessage> d1;
    static final List<Map<String, Double>> choiceKeyWordsChatbot =
            Arrays.asList(
                    Map.ofEntries(entry("would you like", ScoreStandards.highMatch), entry("would you", ScoreStandards.highMatch)),
                    Map.ofEntries(entry("how can i", ScoreStandards.highMatch), entry("what can i", ScoreStandards.highMatch),
                            entry("help", ScoreStandards.mediumMatch),entry("should i", ScoreStandards.mediumMatch),
                            entry("could", ScoreStandards.lowMatch), entry("can", ScoreStandards.lowMatch)),
                    Map.ofEntries(entry("return to main", ScoreStandards.highMatch), entry("end conversation", ScoreStandards.highMatch)),
                    Map.ofEntries(entry("is this", ScoreStandards.mediumMatch), entry("is that", ScoreStandards.mediumMatch),
                            entry("may", ScoreStandards.lowMatch))
            );

    static final List<Map<String, Double>> choiceKeyWordsUser =
            Arrays.asList(
                    Map.ofEntries(entry("yes", ScoreStandards.highMatch), entry("yeah", ScoreStandards.highMatch),
                            entry("okay", ScoreStandards.highMatch), entry("ok", ScoreStandards.highMatch),
                            entry("sure", ScoreStandards.highMatch), entry("yeh", ScoreStandards.mediumMatch)
                            , entry("kk", ScoreStandards.lowMatch)),
                    Map.ofEntries(entry("no", ScoreStandards.highMatch), entry("nope", ScoreStandards.highMatch),
                            entry("nah", ScoreStandards.mediumMatch), entry("not", ScoreStandards.lowMatch))
            );
    /**
     * Sets up a dialogues from one conversation about booking a hotel
     */
    @BeforeAll
    static void setUp() {
        // Since TalkStep is an abstract class, so we instantiate or use it in the tests: So we use Mockito to mock it
        talkStep = Mockito.mock(
                TalkStep.class,
                Mockito.CALLS_REAL_METHODS);
        choiceTalkStep = new ChoiceStep();
        // m1 is ignored in our analysis, but it is included for the understanding of the conversation
        m1 = new WozMessage("request", "I need help with booking a hotel.");
        m2 = new WozMessage("response", "How can I help you?");
        m3 = new WozMessage("request", "Toronto downtown for 2 people");
        m4 = new WozMessage("response", "When should I book your room for?");
        m5 = new WozMessage("request", "Tomorrow 2PM i need to check in");
        m6 = new WozMessage("response", "No problem. What would you price range be?");
        m7 = new WozMessage("request", "Around 200 per person");
        m8 = new WozMessage("response", "Okay! May I proceed with Chelsea Hotel, with 180CAD  per person?");
        m9 = new WozMessage("request", "for 2PM yes");

        ArrayList<WozMessage> a1 = new ArrayList<>(Arrays.asList(m2));
        ArrayList<WozMessage> a2 = new ArrayList<>(Arrays.asList(m3));
        d1 = new Dialogue<>(a1,a2);
//        System.out.println(talkStep.calculateMsgLength(m7));
//        Dialogue<WozMessage> d2 = new Dialogue<>((ArrayList<WozMessage>)List.of(m4),(ArrayList<WozMessage>)List.of(m5));
//        Dialogue<WozMessage> d3 = new Dialogue<>((ArrayList<WozMessage>)List.of(m6),(ArrayList<WozMessage>)List.of(m7));
//        Dialogue<WozMessage> d4 = new Dialogue<>((ArrayList<WozMessage>)List.of(m8),(ArrayList<WozMessage>)List.of(m9));
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
        assertEquals("Choice", choiceTalkStep.getStepName());
    }

    @Test
    void testGetMaxScore() {
        assertEquals(15.0, choiceTalkStep.getMaxScore());
    }

    @Test
    void testGetScoreAccumulator() {
        choiceTalkStep.runAnalysis(d1);
        assertEquals(9, choiceTalkStep.getScoreAccumulator());
    }

    @Test
    void setZeroScoreAccumulator() {
        choiceTalkStep.runAnalysis(d1);
        assertEquals(9, choiceTalkStep.getScoreAccumulator());
        choiceTalkStep.setZeroScoreAccumulator();
        assertEquals(0, choiceTalkStep.getScoreAccumulator());
    }

    /**
     * Below are tests for calculateMessageLength, which returns number of words in message (int)
     * Cases covered:
     * - Short, long, extra spaces (which shouldn't affect length)
     */
    @Test
    void calculateShortMessageLength() {
        assertEquals(4, talkStep.calculateMsgLength(m7));
    }

    @Test
    void calculateLongMessageLength() {
        assertEquals(16, talkStep.calculateMsgLength(m8));
    }


    /**
     * Below are tests for countMatchKeyword, which returns total weighting of keywords matched (double)
     * Edge cases covered:
     * - Keyword is first/last word on message
     * - Many keywords match
     * - No keywords match
     * NOTE: I am using a ChoiceStep to call countMatchKeywords, because TalkStep is an abstract parent class
     * with no keywords to match with and ChoiceStep inherits the method so we can call it through choiceTalkStep
     */
    @Test
    void countMatchFirstKeywords() {
        // 8 = 5 points (highMatch) for "how can i" + 3 points (mediumMatch) for "help"
        assertEquals(9, choiceTalkStep.countMatchKeywords(m2, choiceKeyWordsChatbot));
    }
    @Test
    void countMatchZeroKeywords() {
        assertEquals(0, choiceTalkStep.countMatchKeywords(m5, choiceKeyWordsUser));

    }
    @Test
    void countMatchLastKeywords() {
        assertEquals(5, choiceTalkStep.countMatchKeywords(m9, choiceKeyWordsUser));
    }

}