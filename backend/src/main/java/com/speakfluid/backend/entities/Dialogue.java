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
public class Dialogue {

    // Store the dialogue between the chatBot and the user
    private ArrayList<WozMessage> chatBotMessage;
    private ArrayList<WozMessage> userMessage;
    private String stepSuggestion;
    private double confidenceScore;


    public Dialogue(ArrayList<WozMessage> chatBotM, ArrayList<WozMessage> userM){
        this.chatBotMessage = chatBotM;
        this.userMessage = userM;
    }
    public ArrayList<WozMessage> getChatBotMessage(){
        return this.chatBotMessage;
    }
    public ArrayList<WozMessage> getUserMessage(){
        return this.userMessage;
    }
    public void setConfidenceScore(int confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public void setStepSuggestion(String talkStep) {
        this.stepSuggestion = talkStep;
    }

}