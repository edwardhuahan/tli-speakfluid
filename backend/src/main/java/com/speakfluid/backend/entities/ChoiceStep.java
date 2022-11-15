package com.speakfluid.backend.entities;

import java.lang.String;
import static java.util.Map.entry;
import java.util.*;

/**
 * ChoiceStep analysizes incoming Dialogues to determine the
 * whether that chatbot output of the Dialogue is suitable for
 * a Choice Listen Step through a confidence score.
 *
 * Choice Listen Step is mainly used for "Yes" and "No" simple responses.
 *
 * @author  Sarah Xu
 * @version 1.0
 * @since   2022-11-15
 */
public class ChoiceStep extends TalkStep {
    private static int scoreAccumulator = 0;
    private final int maxScore = 25;
    private double confidenceScore;
    private final List<Map<String, Double>> choiceKeyWordsChatbot =
            Arrays.asList(
                    Map.ofEntries(entry("would you like", 5.0), entry("would you", 4.5)),
                    Map.ofEntries(entry("can i", 5.0), entry("should i", 5.0), entry("could i", 5.0),
                            entry("could", 1.0), entry("can", 1.0)),
                    Map.ofEntries(entry("return to main", 5.0), entry("end conversation", 5.0)),
                    Map.ofEntries(entry("is this", 4.0), entry("is that", 4.0), entry("may", 2.0))
            );

    private final List<Map<String, Double>> choiceKeyWordsUser =
            Arrays.asList(
                    Map.ofEntries(entry("yes", 5.0), entry("yeah", 4.0), entry("okay", 4.0),
                            entry("ok", 4.0), entry("sure", 3.0), entry("yeh", 2.0), entry("kk", 2.0)),
                    Map.ofEntries(entry("no", 5.0), entry("nope", 4.0), entry("nah", 3.0),
                            entry("not", 2.0))
            );

    /**
     * runAnalysis() takes in a Dialogue object and analyses
     * whether the ChoiceStep is an appropriate ListenStep here.
     * It increases scoreAccumulator once there is a sign that
     * the dialogue is compatible with a Choice Step.
     * @param dialogue one back and forth between chatbot and user
     */
    public void runAnalysis(Dialogue dialogue){
        // Chatbot messages
        for (Speech chatbotMessage : dialogue.getChatBotMessage()){
            countMatchKeywords(chatbotMessage, choiceKeyWordsChatbot);
        }
        // User messages
        for (Speech userMessage : dialogue.getUserMessage()){
            countMatchKeywords(userMessage, choiceKeyWordsUser);
            if(calculateMsgLength(userMessage) <= 3) {
                scoreAccumulator += 7;
            }
        }
    }

}