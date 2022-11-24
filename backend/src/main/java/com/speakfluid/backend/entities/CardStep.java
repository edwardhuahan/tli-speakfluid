package com.speakfluid.backend.entities;
import java.util.*;

import static java.util.Map.entry;

/**
 * Card extends the TalkStep abstract class and thus inherits all of its abstract and concrete methods. It uses
 * these calculations to adjust the score accumulator as necessary for a max score of 20.0. This is in adherence to the
 * CRC card designs.
 * @author Aurora Zhang
 * @version 1.0
 * @since November 16th, 2022
 */

public class CardStep extends TalkStep{
    private double scoreAccumulator;
    private final double maxScore = 25.0;
    private final String stepName = "Card";

    public CardStep(){
        this.scoreAccumulator = 0.0;

    }

    private final List<Map<String, Double>> chatbotKeywordsScoreMap = Arrays.asList(
            Map.ofEntries(entry("can i help", 5.0),
                    entry("can i do", 4.0)),
            Map.ofEntries(entry("here are", 3.0), entry("there are", 3.0),
                    entry("what kind", 3.0), entry("do you", 2.0),
                    entry("here is", 2.0)),
            Map.ofEntries(entry("ticket", 3.0), entry("area", 3.0), entry("departure", 3.0),
                    entry("leaves", 3.0)),
            Map.ofEntries(entry("image", 4.0), entry("picture", 4.0)),
            Map.ofEntries(entry("choose", 4.0), entry("select", 4.0), entry("pick", 4.0)));
    private final List<Map<String, Double>> userKeywordsScoreMap = Arrays.asList(
            Map.ofEntries(entry("museum", 5.0),
                    entry("gallery", 4.0), entry("restaurant", 4.0), entry("hospital", 4.0), entry("clinic", 3.0),
                    entry("hotel", 3.0), entry("attraction", 3.0),
                    entry("entertainment", 3.0), entry("food", 3.0)),
            Map.ofEntries(entry("bus", 5.0), entry("taxi", 5.0),
                    entry("train", 5.0)));

    @Override
    public void runAnalysis(Dialogue dialogue) {
        //match the keywords for both the user and chatbot outputs in the dialogue
        for(Object message: dialogue.getChatBotMessage()){
            countMatchKeywords((Message) message, chatbotKeywordsScoreMap);
            double chatbotMsgLength = calculateMsgLength((Message) message);
            // if the chatbot outputs a shorter message, this suggests buttons with a image are more suitable
            if(chatbotMsgLength < 10){
                this.scoreAccumulator += 2;
            }

        }
        for(Object message: dialogue.getUserMessage()){
            countMatchKeywords((Message) message, userKeywordsScoreMap);
            double userMsgLength = calculateMsgLength((Message) message);
            if(userMsgLength < 15){
                scoreAccumulator += 2;
            }

        }


    }
}
