package com.speakfluid.backend.entities;
import java.util.ArrayList;

/**
 * A dialogue is defined as back-forth messages/interactions between the chatBot
 * grouped under the same session ID in the raw transcript.
 * A Dialogue object stores chatbot's message and the user's message in the dialogue;
 * It also stores the suggested talk step and the corresponding confidence score
 * which will be set in the StepSuggestionInteractor(the use case interactor).
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
    private ArrayList<String> stepSuggestion;
    private ArrayList<Double> confidenceScore;


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
    public void setConfidenceScore(double confidenceScore) {

        this.confidenceScore.add(confidenceScore);
    }

    public void setStepSuggestion(String talkStep) {
        this.stepSuggestion.add(talkStep);
    }

    public ArrayList<String> getStepSuggestion() {
        return this.stepSuggestion;
    }

    public ArrayList<Double> getConfidenceScore(){
        return this.confidenceScore;
    }

}