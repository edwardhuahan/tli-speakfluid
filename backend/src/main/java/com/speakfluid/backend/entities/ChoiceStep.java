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
 * @version 3.0
 * @since   2022-11-28
 */
public class ChoiceStep extends TalkStep {
    private final String stepName = "Choice";
    private double scoreAccumulator = 0;
    private final double maxScore = ScoreStandards.standardStepClass;
    private final List<Map<String, Double>> choiceKeyWordsChatbot =
            Arrays.asList(
                    Map.ofEntries(entry("would you like", ScoreStandards.highMatch), entry("would you", ScoreStandards.highMatch)),
                    Map.ofEntries(entry("how can i", ScoreStandards.highMatch), entry("what can i", ScoreStandards.highMatch),
                            entry("help", ScoreStandards.mediumMatch), entry("should i", ScoreStandards.mediumMatch),
                            entry("could", ScoreStandards.lowMatch), entry("can", ScoreStandards.lowMatch)),
                    Map.ofEntries(entry("return to main", ScoreStandards.highMatch), entry("end conversation", ScoreStandards.highMatch)),
                    Map.ofEntries(entry("is this", ScoreStandards.mediumMatch), entry("is that", ScoreStandards.mediumMatch),
                            entry("may", ScoreStandards.lowMatch))
            );

    private final List<Map<String, Double>> choiceKeyWordsUser =
            Arrays.asList(
                    Map.ofEntries(entry("yes", ScoreStandards.highMatch), entry("yeah", ScoreStandards.highMatch),
                            entry("okay", ScoreStandards.highMatch), entry("ok", ScoreStandards.highMatch),
                            entry("sure", ScoreStandards.highMatch), entry("yeh", ScoreStandards.mediumMatch)),
                    Map.ofEntries(entry("no", ScoreStandards.highMatch), entry("nope", ScoreStandards.highMatch),
                            entry("nah", ScoreStandards.mediumMatch), entry("not", ScoreStandards.lowMatch))
            );

    public ChoiceStep(){
        this.scoreAccumulator = 0;
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

    /**
     * runAnalysis() takes in a Dialogue object and analyses
     * whether the ChoiceStep is an appropriate ListenStep here.
     * It increases scoreAccumulator once there is a sign that
     * the dialogue is compatible with a Choice Step.
     * @param dialogue one back and forth between chatbot and user
     */
    public void runAnalysis(Dialogue dialogue) {
        // Chatbot messages
        for (Object chatbotMessage : dialogue.getChatBotMessage()){
            scoreAccumulator += countMatchKeywords((Message) chatbotMessage, choiceKeyWordsChatbot);
        }
        // User messages
        for (Object userMessage : dialogue.getUserMessage()){
            scoreAccumulator += countMatchKeywords((Message) userMessage, choiceKeyWordsUser);

            if(calculateMsgLength((Message) userMessage) <= 3 && calculateMsgLength((Message) userMessage) > 1 ) {

                scoreAccumulator += 3.0;
            }
        }
    }

}