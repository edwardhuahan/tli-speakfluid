package com.speakfluid.backend.entities;
import java.util.ArrayList;

/**
 * A Dialogue object stores chatbot's message and the user's message in a session.
 * It also includes how much does each talk step match.
 *
 * A dialogue is defined as back-forth messages/interactions between the chatBot
 *  grouped under the same session ID in the raw transcript.
 *
 * @author  Zoey Zhang
 * @version 2.0
 * @since   2022-11-12
 */

/* Entity layer */
public class Dialogue<T> {

    // Store the dialogue between the chatBot and the user
    private ArrayList<T> chatBotMessage;
    private ArrayList<T> userMessage;
    private ArrayList<String> stepSuggestionList = new ArrayList<String>();
    private ArrayList<Double> confidenceScoreList = new ArrayList<Double>();;


    public Dialogue(ArrayList<T> chatBotM, ArrayList<T> userM){
        this.chatBotMessage = chatBotM;
        this.userMessage = userM;
    }
    public ArrayList<T> getChatBotMessage(){
        return this.chatBotMessage;
    }
    public ArrayList<T> getUserMessage(){
        return this.userMessage;
    }
    public void addConfidenceScore(double confidenceScore) {
       this.confidenceScoreList.add(confidenceScore);
    }

    public void addStepSuggestion(String talkStep) {
        this.stepSuggestionList.add(talkStep);
    }

    public ArrayList<String> getStepSuggestion() {
        return this.stepSuggestionList;
    }

    public ArrayList<Double> getConfidenceScore(){
        return this.confidenceScoreList;
    }

}