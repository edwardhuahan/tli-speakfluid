package com.speakfluid.backend.entities;
import java.util.ArrayList;

// Entity layer

// A dialogue is defined as back-forth messages/interactions between the chatBot
// grouped under the same session ID in the raw transcript.
public class Dialogue {

    // Store the dialogue between the chatBot and the user
    private ArrayList<Speech> chatBotMessage;
    private ArrayList<Speech> userMessage;


    // Store the suggested talk step and the corresponding confidence score
    // which will be set in the StepSuggestionInteractor(the use case interactor)
    private String stepSuggestion;
    private int confidenceScore;

    public Dialogue(ArrayList<Speech> chatBotM, ArrayList<Speech> userM){
        this.chatBotMessage = chatBotM;
        this.userMessage = userM;
    }
    public ArrayList<Speech> getChatBotMessage(){
        return this.chatBotMessage;
    }
    public ArrayList<Speech> getUserMessage(){
        return this.userMessage;
    }
    public void setConfidenceScore(int confidenceScore) {
        this.confidenceScore = confidenceScore;
    }

    public void setStepSuggestion(String talkStep) {
        this.stepSuggestion = talkStep;
    }
}