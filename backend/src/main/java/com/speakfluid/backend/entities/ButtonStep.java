package com.speakfluid.backend.entities;

import java.util.List;
import java.util.Arrays;
import java.util.Map;

import static java.util.Map.entry;

/**
 * The ButtonStep class runs the inputted Dialogue object through all metrics for calculating the probability of
 * the usefulness of a button for this step in the conversational flow. It implements the TalkStep abstract class
 * and inherits all of its attributes and methods.
 * @author Aurora Zhang
 * @version 3.0
 * @since November 16th 2022
 */

public class ButtonStep extends TalkStep{

    private double scoreAccumulator;
    private final double maxScore = ScoreStandards.standardStepClass + ScoreStandards.additionalAnalysis * 2;
    private final String stepName = "Button";


    public ButtonStep(){
        this.scoreAccumulator = 0.0;
    }
    private final List<Map<String, Double>> chatbotKeywordsScoreMap = Arrays.asList(
            Map.ofEntries(entry("would you", ScoreStandards.highMatch), entry("would it", ScoreStandards.highMatch),
                    entry("what type", ScoreStandards.mediumMatch), entry("what kind", ScoreStandards.mediumMatch),
                    entry("are you", ScoreStandards.mediumMatch), entry("do you", ScoreStandards.lowMatch),
                    entry("here is", ScoreStandards.lowMatch)),
            Map.ofEntries(entry("here are", ScoreStandards.highMatch), entry("choose", ScoreStandards.highMatch),
                    entry("select", ScoreStandards.highMatch)),
            Map.ofEntries(entry("destination", ScoreStandards.mediumMatch), entry("date", ScoreStandards.mediumMatch),
                    entry("departure", ScoreStandards.mediumMatch),
                    entry("arriving", ScoreStandards.mediumMatch)));
    private final List<Map<String, Double>> userKeywordsScoreMap = Arrays.asList(
            Map.ofEntries(entry("booking", ScoreStandards.lowMatch),
                    entry("train", ScoreStandards.lowMatch), entry("go to", ScoreStandards.lowMatch),
                    entry("arrive", ScoreStandards.lowMatch)),
            Map.ofEntries(entry("hotel", ScoreStandards.highMatch), entry("cheap", ScoreStandards.highMatch),
                    entry("hospital", ScoreStandards.highMatch)));

    /**
     * runs each method to analyze both the user and chatbot output to see whether this dialogue is
     * best suited for the use of a button. Updates the accumulated score attribute accordingly.
     * @param dialogue a single back and forth between the user and the chatbot.
     */

    @Override
    public void runAnalysis(Dialogue dialogue) {
        for(Object message: dialogue.getChatBotMessage()){
            this.scoreAccumulator += countMatchKeywords((Message) message, chatbotKeywordsScoreMap);
            int chatbotMsgLength = calculateMsgLength((Message) message);
            if(chatbotMsgLength < 10){
                this.scoreAccumulator += 2;
            }

        }
        for(Object message: dialogue.getUserMessage()){
            this.scoreAccumulator += countMatchKeywords((Message) message, userKeywordsScoreMap);
            int userMsgLength = calculateMsgLength((Message) message);
            // if the user response is short, then this suggests buttons are suitable
            if(userMsgLength <= 5){
                this.scoreAccumulator += 2;
            }

        }
    }

    public String getStepName(){
        return this.stepName;
    }

    public double getMaxScore(){
        return this.maxScore;
    }

    public double getScoreAccumulator(){
        return this.scoreAccumulator;
    }

    public void setZeroScoreAccumulator(){
        this.scoreAccumulator = 0;
    }
    
}