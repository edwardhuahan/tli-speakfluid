package com.speakfluid.backend.entities;

import java.util.*;
import java.util.stream.Collectors;

/**
 * ConfidenceScoreOptimizer calls the ConfidenceScoreOptimizer to store each talk step's confidence score. It then returns a Hashmap mapping
 * the talk step with the highest confidence score to its confidence score. This class is called by the use case interactor.
 * @author Aurora Zhang
 * @version 2.0
 * @since November 16th, 2022
 */
public class ConfidenceScoreOptimizer {

    ArrayList<TalkStep> steps;
    HashMap<String, Double> talkStepToScoreMapping = new HashMap<>();
    HashMap<String, Double> suggestedTalkStep = new HashMap<>();
    ConfidenceScoreCalculator confidenceScoreCalculator;
    Double stepConfidenceScore;
    Double highestConfidenceScore;
    List<String> topThreeSteps;

    public ConfidenceScoreOptimizer(ConfidenceScoreCalculator confidenceScoreCalculator, ArrayList<TalkStep> stepList) {
        this.confidenceScoreCalculator = confidenceScoreCalculator;
        this.steps = stepList;
    }

    /**
     * Calls the ConfidenceScoreCalculator object to implement all methods for each TalkStep and store the
     * returned confidence score in the talkStepToScoreMapping HashMap to keep track of the
     * talk step to its confidence score
     * @param dialogue the dialogue ArrayList containing the Speeches of the user and chatbot
     */
    public void callConfidenceScoreCalculator(Dialogue dialogue){
        for(TalkStep talkStep: steps){
            confidenceScoreCalculator.passDialogueToTalkStep(dialogue, talkStep);
            stepConfidenceScore = confidenceScoreCalculator.calculateConfidenceScore();
            talkStepToScoreMapping.put(talkStep.getStepName(), stepConfidenceScore);
        }
    }

    /**
     * Returns the talk step with the highest confidence score from the talkStepToScoreMapping if the confidence score is
     * above 70%. Otherwise returns the top three choices with the top three confidence scores.
     *
     * @return String returns the suggested talk step as a String.
     */
    public HashMap<String, Double> findSuggestedTalkStep() {
        highestConfidenceScore = Collections.max(talkStepToScoreMapping.values());

        if(highestConfidenceScore >= 70) {
            for(Map.Entry<String, Double> entry: talkStepToScoreMapping.entrySet()) {
                if(Objects.equals(entry.getValue(), highestConfidenceScore)){
                    suggestedTalkStep.put(entry.getKey(), entry.getValue());
                    return suggestedTalkStep;
                }
            }
        }
        else{
            //Get top 3 keys (talk steps) of a map with top 3 highest confidence scores
            topThreeSteps = talkStepToScoreMapping.entrySet().stream()
                    .sorted(Map.Entry.<String, Double>comparingByValue().reversed()).
                    limit(3).map(Map.Entry::getKey).collect(Collectors.toList());
            // iterate through talkStepToScoreMapping to find corresponding values and add them to
            //the returned suggestedTalkStep HashMap
            for (String topThreeStep : topThreeSteps) {
                for (Map.Entry<String, Double> entry : talkStepToScoreMapping.entrySet()) {
                    if (entry.getKey().equals(topThreeStep)) {
                        suggestedTalkStep.put(entry.getKey(), entry.getValue());
                    }
                }
            }
            return suggestedTalkStep;
        }
        return suggestedTalkStep;
    }

}