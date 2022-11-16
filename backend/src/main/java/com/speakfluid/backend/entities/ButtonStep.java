package com.speakfluid.backend.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

import static java.util.Map.entry;

/**
 * The ButtonStep class runs the inputted Dialogue object through all metrics for calculating the probability of
 * the usefulness of a button for this step in the conversational flow. It implements the TalkStep abstract class
 * and inherits all of its attributes and methods.
 * @author Aurora Zhang
 * @version 1.0
 * @since November 16th 2022
 */

public class ButtonStep extends TalkStep{

    private double scoreAccumulator;
    private final double maxScore = 12;
    private final String stepName = "button step";
    int chatbotMsgLength;
    int userMsgLength;
    private final ArrayList<Map<String, Double>> chatbotKeywordsScoreMap = (ArrayList<Map<String, Double>>) Arrays.asList(
            Map.ofEntries(entry("would you", 2.0),
                    entry("what type", 2.0), entry("are you", 2.0), entry("would it", 2.0),
                    entry("what kind", 2.0), entry("here are", 2.0), entry("do you", 2.0),
                    entry("here is", 2.0)),
            Map.ofEntries(entry("here are", 4.0), entry("choose", 4.0), entry("select", 4.0)),
            Map.ofEntries(entry("destination", 3.0), entry("date", 3.0), entry("departure", 3.0),
                    entry("arriving", 3.0)));
    private final ArrayList<Map<String, Double>> userKeywordsScoreMap = (ArrayList<Map<String, Double>>) Arrays.asList(
            Map.ofEntries(entry("booking", 2.0),
                    entry("train", 2.0), entry("go to", 2.0), entry("arrive", 2.0)),
            Map.ofEntries(entry("hotel", 4.0), entry("cheap", 4.0), entry("hospital", 4.0)));

    /**
     * runs each method to analyze both the user and chatbot output to see whether this dialogue is
     * best suited for the use of a button. Updates the accumulated score attribute accordingly.
     * @param dialogue a single back and forth between the user and the chatbot.
     */

    @Override
    public void runAnalysis(Dialogue dialogue) {
        for(Speech speech: dialogue.getChatBotMessage()){
            countMatchKeywords(speech, chatbotKeywordsScoreMap);
            chatbotMsgLength = calculateMsgLength(speech);

        }
        for(Speech speech: dialogue.getUserMessage()){
            countMatchKeywords(speech, userKeywordsScoreMap);
            userMsgLength = calculateMsgLength(speech);

        }
        // if the chatbot outputs more information than the user, buttons are not suitable
        if((chatbotMsgLength/userMsgLength) > 1){
            this.scoreAccumulator -= 2;
        }
        else{
            this.scoreAccumulator += 2;
        }
        // if the user response is short, then this suggests buttons are suitable
        if(userMsgLength <= 5){
            this.scoreAccumulator += 2;
        }
    }
}