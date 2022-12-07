package com.speakfluid.backend.entities.steps;

import com.speakfluid.backend.entities.ScoreStandards;
import com.speakfluid.backend.entities.message.Dialogue;
import com.speakfluid.backend.entities.message.Message;

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
    private final double maxScore = ScoreStandards.standardStepClass + ScoreStandards.additionalKeywordsMatching;
    private final List<Map<String, Double>> imageKeyWordsBot = Arrays.asList(
            Map.ofEntries(entry("here are the locations", ScoreStandards.highMatch),
                    entry("here are possible the directions", ScoreStandards.highMatch),
                    entry("map", ScoreStandards.mediumMatch), entry("location", 3.0),entry("direction", 3.0)
                    ),
            Map.ofEntries(entry("here is a picture", ScoreStandards.highMatch),
                    entry("here is an image", ScoreStandards.highMatch),
                    entry("picture", ScoreStandards.highMatch),entry("illustration", ScoreStandards.highMatch),
                    entry("image", ScoreStandards.highMatch)),
            Map.ofEntries(entry("show", ScoreStandards.lowMatch), entry("illustrate", ScoreStandards.lowMatch))
    );

    private final List<Map<String, Double>> imageKeyWordsUser = Arrays.asList(
            Map.ofEntries(entry("locations of", ScoreStandards.highMatch),
                    entry("direction to", ScoreStandards.highMatch),
                    entry("map", ScoreStandards.highMatch), entry("location", ScoreStandards.mediumMatch),
                    entry("direction", ScoreStandards.mediumMatch)
            ),
            Map.ofEntries(entry("another picture", ScoreStandards.mediumMatch),
                    entry("here is an image", ScoreStandards.mediumMatch),
                    entry("picture", ScoreStandards.mediumMatch),entry("illustration", ScoreStandards.mediumMatch),
                    entry("image", ScoreStandards.mediumMatch)),
            Map.ofEntries(entry("show", ScoreStandards.lowMatch), entry("illustrate", ScoreStandards.lowMatch))
    );


    public ImageStep(){
        this.scoreAccumulator = 0;
    }
    public String getStepName(){
        String stepName = "Image";
        return stepName;
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
            scoreAccumulator += countMatchKeywords((Message) message, imageKeyWordsBot);
            if (scoreAccumulator != 0.0 &&
                    calculateMsgLength((Message) message) <= 6){
                scoreAccumulator += ScoreStandards.lowMatch;
            }
        }
        if (dialogue.getUserMessage().size() != 0) {
            for (Object message : dialogue.getUserMessage()) {
                scoreAccumulator += countMatchKeywords((Message) message, imageKeyWordsUser);
            }
        }
    }

}
