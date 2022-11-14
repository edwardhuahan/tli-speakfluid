package com.speakfluid.backend.entities;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * ChoiceStep analysizes incoming Dialogues to determine the
 * whether that chatbot output of the Dialogue is suitable for
 * a Choice Listen Step through a confidence score.
 *
 * Choice Listen Step is mainly used for "Yes" and "No" simple responses.
 *
 * @author  Sarah Xu
 * @version 1.0
 * @since   2022-11-12
 */
public class ChoiceStep extends TalkStep implements Scorable{
    private int scoreAccumulator = 0;
    private int maxScore = 15;
    private double confidenceScore;
    private List<String> choiceKeyWordsChatbot = Arrays.asList("would you", "would you like",
            "return to main", "should i", "can i", "is this", "is that", "is");
    private List<String> choiceKeyWordsUser = Arrays.asList("yes", "no", "ok", "sure", "yeah", "yea", "nah", "nope",
            "yse", "ya", "ye", "n", "y", "not sure", "no.", "yes.");

    public ChoiceStep(Dialogue dialogue){

    }

    /**
     * This method is inherited from the parent TalkStep class
     * @param speech a Speech object
     * @param choiceKeyWordsChatbot an ArrayList of chatbot keywords
     */
    @Override
    public int countMatchKeywords(Speech speech, ArrayList<String> choiceKeyWordsChatbot) {
        int numMatches = 0;
        for (String word: speech.getMessage().split(" ")){
            if (choiceKeyWordsChatbot.contains(word)) numMatches++;
        }
        return numMatches;
    }


    /**
     * This is the main method which makes use of addNum method.
     * @param args Unused.
     * @return Nothing.
     */
    public static void main(String args[]) {

    }
}