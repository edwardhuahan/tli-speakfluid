package com.speakfluid.backend.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * CarouselStep class stores and reflects the features of the Image Talk Step functionality of Voiceflow.
 *
 * Carousel talk step is mainly used when the bot presents a list of cards which are formed by buttons and images.
 *
 * scoreAccumulator is defined to keep track of how much the dialogue that we are analysing fits each feature of
 * Text talk step.
 * maxScore is setted as the score if the dialogue is compilable perfectly with Carousel talk step features.
 * A confidence score will be calculated as the quotient of scoreAccumulator and maxScore
 * to reflect how well the dialogue matches with the Carousel Talk Step's features.
 *
 * @author  Zoey Zhang
 * @version 2.0
 * @since   2022-11-16
 */
public class CarouselStep extends TalkStep {
    private double scoreAccumulator;
    private final double maxScore = 10.0;
    private final String stepName = "Carousel";
    private final List<Map<String, Double>> imageKeyWords = Arrays.asList(
            Map.ofEntries(entry("here are", ScoreStandards.mediumMatch),entry("map", ScoreStandards.lowMatch),
                    entry("location", ScoreStandards.lowMatch),
                    entry("direction", ScoreStandards.lowMatch)),
            Map.ofEntries(entry("here is a picture", ScoreStandards.mediumMatch),
                    entry("here is an image", ScoreStandards.mediumMatch),
                    entry("picture", ScoreStandards.mediumMatch),entry("illustration", ScoreStandards.mediumMatch),
                    entry("image", ScoreStandards.mediumMatch)
                    ),
            Map.ofEntries(entry("show", ScoreStandards.lowMatch), entry("illustrate", ScoreStandards.lowMatch)),
            Map.ofEntries(entry("list of", ScoreStandards.highMatch), entry("a list of", ScoreStandards.highMatch),
                    entry("lists of", ScoreStandards.highMatch),entry("here are a list of", ScoreStandards.highMatch),
                    entry("list", ScoreStandards.mediumMatch)),
            Map.ofEntries(entry("choose from the following", ScoreStandards.mediumMatch))
            );
    private final List<Map<String, Double>> buttonKeyWords = Arrays.asList(
            Map.ofEntries(entry("The following are", 5.0),
                    entry("These are", 4.0),entry("here are", ScoreStandards.mediumMatch),
                    entry("do you", ScoreStandards.lowMatch),
                    entry("here is", ScoreStandards.lowMatch)),
            Map.ofEntries(entry("here are", ScoreStandards.highMatch), entry("choose", ScoreStandards.highMatch),
                    entry("select", ScoreStandards.highMatch), entry("options", ScoreStandards.highMatch)));

    public CarouselStep(){
        this.scoreAccumulator = 0;
    }

    /**
     * runAnalysis for ImageStep determines:
     * 1. How many carousel keywords the message has?
     *
     * @param dialogue one back-and-forth conversation between the chatbot and the user.
     */
    @Override
    public void runAnalysis(Dialogue<?> dialogue) {
        for (Object message : dialogue.getChatBotMessage()) {
            scoreAccumulator += countMatchKeywords((Message) message, imageKeyWords);
            scoreAccumulator += countMatchKeywords((Message) message, buttonKeyWords);
            if (scoreAccumulator != 0.0 &&
                    calculateMsgLength((Message) message) <= 6){
                scoreAccumulator += ScoreStandards.mediumMatch;
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
