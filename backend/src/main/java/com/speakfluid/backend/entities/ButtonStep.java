package main.java.com.speakfluid.backend.entities;
import java.util.*;

import static java.util.Map.entry;
import java.util.*;

public class ButtonStep extends TalkStep{
    String stepName;
    Map<String, Double> keywordWeights;
    public static double scoreAccumulator = 0.0;
    public static double maxScore = 10.0; //arbitrary, subject to change
    double confidenceScore;
    double chatbotMsgLength;
    double userMsgLength;
    double chatbotToUserMsgRatio;

    public ButtonStep(String nameOfStep){
        this.stepName = nameOfStep;
        this.keywordWeights = Map.ofEntries(entry("What Kind", 2.0), entry("What Type", 2.0),
                entry("Choose", 1.5), entry("Select", 1.5), entry("Would You", 1.5));

    }

    /**
     * Adds up all the chatbot message lengths in the dialogue.
     * @param dialogue the single back and forth between the user and the chatbot.
     */
    @Override
    public void calculateChatbotMsgLength(Dialogue dialogue) {
        for(Speech speech: dialogue.dialogue){
            if(Objects.equals(speech.speaker, "chatbot") && !Objects.equals(speech.message, "")){
                chatbotMsgLength += speech.message.length();
            }
        }

    }

    /**
     * Adds up all the user message lengths in the dialogue.
     * @param dialogue the single back and forth between the user and the chatbot.
     */
    @Override
    public void calculateUserMsgLength(Dialogue dialogue) {
        for(Speech speech: dialogue.dialogue){
            if(Objects.equals(speech.speaker, "user") && !Objects.equals(speech.message, "")){
                userMsgLength += speech.message.length();
            }
        }
    }

    /**
     * Adds up all the weighted scores of the keywords that are contained in the dialogue. Each weight
     * is added to the scoreAccumulator attribute.
     * @param dialogue the single back and forth between the user and the chatbot.
     */

    //we should also have a keyword dictionary for what the user says in response.
    @Override
    public void countMatchKeywords(Dialogue dialogue) {
        for(Speech speech: dialogue.dialogue) {
            if(Objects.equals(speech.speaker, "chatbot")){
                for(Map.Entry<String, Double> entry: keywordWeights.entrySet()) {
                    if(speech.message.contains(entry.getKey())){
                        scoreAccumulator += entry.getValue();
                    }

                }
            }
        }

    }

    /**
     * determines the ratio of the chatbot message length to user message length and adds
     * a predetermined score to the scoreAccumulator.
     * if the ratio is low, the chatbot outputs less data and buttons are helpful so the score is increased.
     * if the ratio is high, the chatbot outputs more data and buttons are not helpful so the score is subtracted.
     *
     */
    @Override
    public void calculateChatbotToUserMsgRatio() {
        chatbotToUserMsgRatio = chatbotMsgLength / userMsgLength;
        if(chatbotToUserMsgRatio > 1.0){
            scoreAccumulator -= -0.5;
        }
        scoreAccumulator += 1.0;
    }


}