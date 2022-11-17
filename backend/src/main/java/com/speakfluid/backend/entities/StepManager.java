package com.speakfluid.backend.entities;

/**
 * StepManager is responsible for calling every method for each child of TalkStep to increment or decrement their
 * corresponding accumulated scores as necessary. It then returns the confidence score of the TalkStep child based on the
 * inputted Dialogue object.
 * @author Aurora Zhang
 * @version 1.0
 * @since November 16th, 2022
 */

public class StepManager implements Scorable{
    double stepScoreAccumulator;
    double stepTotalScore;

    /**
     * calls each method of the TalkStep to calculate how many of our metrics for the optimal
     * talk step it satisfies. Keeps track of the accumulated score and max score in the stepTotalScore
     * and stepStoreAccumulator attributes.
     * @param dialogue the single back and forth between the user and chatbot.
     * @param step the type of the talk step whose methods are called.
     */

    public void passDialogueToTalkStep(Dialogue dialogue, TalkStep step) {
        step.runAnalysis(dialogue);
        stepTotalScore = step.getMaxScore();
        stepScoreAccumulator = step.getScoreAccumulator();
        step.setZeroScoreAccumulator();
    }

    /**
     * Calculates the confidence score of the talk step.
     * @return returns the confidence score of the talk step.
     */
    @Override
    public double calculateConfidenceScore() {
        return (stepScoreAccumulator / stepTotalScore) * 100;
    }

}