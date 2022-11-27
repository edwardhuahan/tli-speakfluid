package com.speakfluid.backend.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * ImageStep class stores and reflects the features of the Image Talk Step functionality of Voiceflow.
 *
 * Image talk step is mainly used when the bot presents an image to the user.
 *
 * scoreAccumulator is defined to keep track of how much the dialogue that we are analysing fits each feature of
 * Text talk step.
 * maxScore is setted as the score if the dialogue is compilable perfectly with Image talk step features.
 * A confidence score will be calculated as the quotient of scoreAccumulator and maxScore
 * to reflect how well the dialogue matches with the Image Talk Step's features.
 *
 * @author  Zoey Zhang
 * @version 2.0
 * @since   2022-11-16
 */
public class ImageStep extends TalkStep {
    private double scoreAccumulator;
    private final double maxScore = 13.0;
    private final String stepName = "Image";
    private final List<Map<String, Double>> imageKeyWordsBot = Arrays.asList(
            Map.ofEntries(entry("here are the locations", 5.0),
                    entry("here are possible the directions", 5.0),
                    entry("map", 4.0), entry("location", 3.0),entry("direction", 3.0)
                    ),
            Map.ofEntries(entry("here is a picture", 4.0), entry("here is an image", 4.0),
                    entry("picture", 3.0),entry("illustration", 3.0),entry("image", 3.0)),
            Map.ofEntries(entry("show", 1.0), entry("illustrate", 1.0))
    );

    private final List<Map<String, Double>> imageKeyWordsUser = Arrays.asList(
            Map.ofEntries(entry("locations of the stores", 5.0),
                    entry("direction to the store", 5.0),
                    entry("map", 4.0), entry("location", 3.0),entry("direction", 3.0)
            ),
            Map.ofEntries(entry("another picture", 4.0), entry("here is an image", 4.0),
                    entry("picture", 3.0),entry("illustration", 3.0),entry("image", 3.0)),
            Map.ofEntries(entry("show", 1.0), entry("illustrate", 1.0))
    );


    public ImageStep(){
        this.scoreAccumulator = 0;
    }

    /**
     * runAnalysis for ImageStep determines:
     * 1. If the bot's message is short
     * 2. How many image keywords the message has?
     *
     * @param dialogue one back-and-forth conversation between the chatbot and the user.
     */
    @Override
    public void runAnalysis(Dialogue<?> dialogue) {

        for (Object message : dialogue.getChatBotMessage()) {
            countMatchKeywords((Message) message, imageKeyWordsBot);
            if (calculateMsgLength((Message) message) <= 6) {
                scoreAccumulator += 5;
            }
        }

        for (Object message : dialogue.getUserMessage()) {
            countMatchKeywords((Message) message, imageKeyWordsUser);
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
