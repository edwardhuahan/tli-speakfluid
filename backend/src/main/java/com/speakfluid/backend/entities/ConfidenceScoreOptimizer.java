package com.speakfluid.backend.entities;
import com.speakfluid.backend.entities.message.*;
import com.speakfluid.backend.entities.steps.*;

import java.util.*;

/**
 * ConfidenceScoreOptimizer calls the ConfidenceScoreCalculator to store each talk step's confidence score. It then returns a Hashmap mapping
 * the talk step with the highest confidence score to its confidence score. This class is called by the use case interactor.
 * @author Aurora Zhang, Kai Zhuang
 * @version 4.0
 * @since November 16th, 2022
 */
public class ConfidenceScoreOptimizer {

    ArrayList<TalkStep> steps;
    HashMap<String, Double> talkStepToScoreMapping = new HashMap<>();
    ConfidenceScoreCalculator confidenceScoreCalculator;
    Double stepConfidenceScore;

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
    public void callConfidenceScoreCalculator(Dialogue<?> dialogue){
        for(TalkStep talkStep: steps){
            confidenceScoreCalculator.passDialogueToTalkStep(dialogue, talkStep);
            stepConfidenceScore = confidenceScoreCalculator.calculateConfidenceScore();
            talkStepToScoreMapping.put(talkStep.getStepName(), stepConfidenceScore);
        }
    }

    /**
     * Turns the talkStepToScore Mapping and returns an ArrayList<Map<String, Double>> which is the sorted
     * talkStepToScoreMapping in decreasing order.
     * @return rankedTalkStepList is an ordered arraylist containing a Map of each talkstep to
     * their confidence score in decreasing order of ranking.
     */
    public ArrayList<Map<String, Double>> rankTalkSteps() {
        //Sorting the map in increasing order
        LinkedHashMap<String, Double> rankedTalkStepMap = new LinkedHashMap<>();
        ArrayList<Map<String, Double>> rankedTalkStepList = new ArrayList<>();

        talkStepToScoreMapping.entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .forEachOrdered(x -> rankedTalkStepMap.put(x.getKey(), x.getValue()));

        //adding each entry to the front of the list so it will become decreasing order
        for(Map.Entry<String, Double> entry: rankedTalkStepMap.entrySet()){
            HashMap<String, Double> talkStepPair = new HashMap<>();
            talkStepPair.put(entry.getKey(), entry.getValue());
            rankedTalkStepList.add(0, talkStepPair);
        }

        return rankedTalkStepList;
    }
}