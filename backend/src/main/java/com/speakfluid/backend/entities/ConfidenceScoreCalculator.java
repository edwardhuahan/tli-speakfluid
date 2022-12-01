package com.speakfluid.backend.entities;
import com.speakfluid.backend.entities.message.*;
import com.speakfluid.backend.entities.steps.*;

import java.lang.Math.*;

/**
 * ConfidenceScoreCalculator is responsible for calling every method for each child of TalkStep to adjust their
 * corresponding accumulated scores as necessary. It then returns the confidence score of the TalkStep child based on the
 * inputted Dialogue object.
 * @author Aurora Zhang
 * @version 2.0
 * @since November 16th, 2022
 */

public class ConfidenceScoreCalculator implements Scorable{
    double stepScoreAccumulator;
    double stepTotalScore;

    /**
     * calls each method of the TalkStep to calculate how many of our metrics for the optimal
     * talk step it satisfies. Keeps track of the accumulated score and max score in the stepTotalScore
     * and stepStoreAccumulator attributes.
     * @param dialogue the single back and forth between the user and chatbot.
     * @param step the type of the talk step whose methods are called.
     */

    public void passDialogueToTalkStep(Dialogue<?> dialogue, TalkStep step) {
        step.runAnalysis(dialogue);
        stepTotalScore = step.getMaxScore();
        stepScoreAccumulator = step.getScoreAccumulator();
        step.setZeroScoreAccumulator();
    }

    /**
     * Calculates the confidence score of the talk step. If it exceeds 100%, then
     * 95% will be returned as a capping mechanism.
     * @return returns the confidence score of the talk step.
     */
    @Override
    public double calculateConfidenceScore() {

        if(((stepScoreAccumulator / stepTotalScore) * 100) > 100){
            return 95.0;

        }
        else{
            double confidenceScore = (stepScoreAccumulator / stepTotalScore) * 100;
            return Math.round(confidenceScore * 100.0) / 100.0;
        }
    }

}