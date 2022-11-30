package com.speakfluid.backend.entities.steps;
import com.speakfluid.backend.entities.message.*;
import com.speakfluid.backend.entities.steps.*;
import com.speakfluid.backend.entities.*;

import java.util.*;

import static java.util.Map.entry;

/**
 * Card extends the TalkStep abstract class and thus inherits all of its abstract and concrete methods. It uses
 * these calculations to adjust the score accumulator as necessary for a max score of 20.0. This is in adherence to the
 * CRC card designs.
 * @author Aurora Zhang
 * @version 3.0
 * @since November 16th, 2022
 */

public class CardStep extends TalkStep{
    private double scoreAccumulator;
    private final double maxScore = ScoreStandards.standardStepClass;
    private final String stepName = "Card";

    public CardStep(){
        this.scoreAccumulator = 0.0;

    }

    private final List<Map<String, Double>> chatbotKeywordsScoreMap = Arrays.asList(
            Map.ofEntries(entry("can i help", ScoreStandards.highMatch),
                    entry("can i do", ScoreStandards.mediumMatch)),
            Map.ofEntries(entry("here are", ScoreStandards.mediumMatch), entry("there are", ScoreStandards.mediumMatch),
                    entry("what kind", ScoreStandards.mediumMatch), entry("do you", ScoreStandards.lowMatch),
                    entry("here is", ScoreStandards.lowMatch)),
            Map.ofEntries(entry("ticket", ScoreStandards.mediumMatch), entry("area", ScoreStandards.mediumMatch),
                    entry("departure", ScoreStandards.mediumMatch),
                    entry("leaves", ScoreStandards.mediumMatch)),
            Map.ofEntries(entry("image", ScoreStandards.highMatch), entry("picture", ScoreStandards.highMatch)),
            Map.ofEntries(entry("choose", ScoreStandards.highMatch), entry("select", ScoreStandards.highMatch),
                    entry("pick", ScoreStandards.highMatch)));
    private final List<Map<String, Double>> userKeywordsScoreMap = Arrays.asList(
            Map.ofEntries(entry("museum", ScoreStandards.highMatch),
                    entry("gallery", ScoreStandards.highMatch), entry("restaurant", ScoreStandards.highMatch),
                    entry("hospital", ScoreStandards.highMatch), entry("clinic", ScoreStandards.mediumMatch),
                    entry("hotel", ScoreStandards.mediumMatch), entry("attraction", ScoreStandards.mediumMatch),
                    entry("entertainment", ScoreStandards.mediumMatch), entry("food", ScoreStandards.mediumMatch)),
            Map.ofEntries(entry("bus", ScoreStandards.highMatch), entry("taxi", ScoreStandards.highMatch),
                    entry("train", ScoreStandards.highMatch)));

    @Override
    public void runAnalysis(Dialogue<?> dialogue) {
        //match the keywords for both the user and chatbot outputs in the dialogue
        for(Object message: dialogue.getChatBotMessage()){
            this.scoreAccumulator += countMatchKeywords((Message) message, chatbotKeywordsScoreMap);
            int chatbotMsgLength = calculateMsgLength((Message) message);
            // if the chatbot outputs a shorter message, this suggests buttons with images are more suitable
            if(chatbotMsgLength < 10 && chatbotMsgLength > 1){
                this.scoreAccumulator += 2;
            }

        }
        for(Object message: dialogue.getUserMessage()){
            this.scoreAccumulator += countMatchKeywords((Message) message, userKeywordsScoreMap);
            int userMsgLength = calculateMsgLength((Message) message);
            if(userMsgLength < 15 && userMsgLength >= 4){
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
