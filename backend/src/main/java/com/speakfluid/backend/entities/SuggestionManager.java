package com.speakfluid.backend.entities;

import java.util.*;

/**
 * SuggestionManager calls the StepManager to store each talk step's confidence score. It then returns a Hashmap mapping
 * the talk step with the highest confidence score to its confidence score. This class is called by the use case interactor.
 * @author Aurora Zhang
 * @version 1.0
 * @since November 16th, 2022
 */
public class SuggestionManager {

    ArrayList<TalkStep> steps;
    HashMap<String, Double> talkStepToScoreMapping = new HashMap<>();
    HashMap<String, Double> suggestedTalkStep = new HashMap<>();
    StepManager stepManager;
    Double stepConfidenceScore;
    Double highestConfidenceScore;

    public SuggestionManager(StepManager manager, ArrayList<TalkStep> stepList) {
        this.stepManager = manager;
        this.steps = stepList;
    }

    /**
     * Calls the StepManager object to implement all methods for each TalkStep and store the
     * returned confidence score in the talkStepToScoreMapping HashMap to keep track of the
     * talk step to its confidence score
     * @param dialogue the dialogue ArrayList containing the Speeches of the user and chatbot
     */
    public void callStepManager(Dialogue dialogue){
        for(TalkStep talkStep: steps){
            stepManager.passDialogueToTalkStep(dialogue, talkStep);
            stepConfidenceScore = stepManager.calculateConfidenceScore();
            talkStepToScoreMapping.put(talkStep.getStepName(), stepConfidenceScore);
        }
    }

    /**
     * Returns the talk step with the highest confidence score from the talkStepToScoreMapping.
     *
     * @return String returns the suggested talk step as a String.
     */
    public HashMap<String, Double> findSuggestedTalkStep() {
        highestConfidenceScore = Collections.max(talkStepToScoreMapping.values());
        for(Map.Entry<String, Double> entry: talkStepToScoreMapping.entrySet()) {
            if(Objects.equals(entry.getValue(), highestConfidenceScore)){
                suggestedTalkStep.put(entry.getKey(), entry.getValue());
                return suggestedTalkStep;
            }
        }
        return suggestedTalkStep;
    }
}