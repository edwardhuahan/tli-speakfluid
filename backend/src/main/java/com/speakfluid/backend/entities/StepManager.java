package main.java.com.speakfluid.backend.entities;

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
        step.calculateChatbotMsgLength(dialogue);
        step.calculateUserMsgLength(dialogue);
        step.countMatchKeywords(dialogue);
        step.calculateChatbotToUserMsgRatio();

        //have mapping of talk step to all methods it will have to run

        stepTotalScore = TalkStep.maxScore;
        stepScoreAccumulator = TalkStep.scoreAccumulator;
    }

    /**
     * Calculates the confidence score of the talk step.
     * @return returns the confidence score of the talk step.
     */
    @Override //change to getConfidenceScore()
    public double calculateConfidenceScore() {
        return (stepScoreAccumulator / stepTotalScore);
    }



}