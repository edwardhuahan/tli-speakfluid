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
 * @version 1.0
 * @since   2022-11-12
 */
public class CarouselStep extends TalkStep {
    private int scoreAccumulator;
    private final int maxScore = 16;
    private final String stepName = "Carousel";
    private final List<Map<String, Double>> imageKeyWords = Arrays.asList(
            Map.ofEntries(entry("map", 1.0), entry("location", 1.0),entry("direction", 1.0),
                    entry("here are", 2.0)),
            Map.ofEntries(entry("picture", 3.0),entry("illustration", 3.0),entry("image", 3.0),
                    entry("here is a picture", 4.0), entry("here is an image", 4.0)),
            Map.ofEntries(entry("show", 1.0), entry("illustrate", 1.0)),
            Map.ofEntries(entry("list of", 5.0), entry("list", 4.0),entry("a list of", 5.0),
                    entry("lists of", 5.0),entry("here are a list of", 2.0)),
            Map.ofEntries(entry("", 3.0),entry("illustration", 3.0),entry("image", 3.0),
                    entry("choose from the following", 4.0))
            );

    /**
     * runAnalysis for ImageStep determines:
     * 1. How many carousel keywords the message has?
     *
     * @param dialogue one back-and-forth conversation between the chatbot and the user.
     */
    @Override
    public void runAnalysis(Dialogue dialogue) {

        for (Speech message : dialogue.getChatBotMessage()) {
            countMatchKeywords(message, imageKeyWords);
        }
    }

}
