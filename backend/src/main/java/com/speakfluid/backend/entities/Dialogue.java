package com.speakfluid.backend.entities;
import java.util.ArrayList;
/**
 * A dialogue is defined as back-forth messages/interactions between the chatBot
 * grouped under the same session ID in the raw transcript. It always starts with a bot's message
 * and end with a user's message.
 * A Dialogue object stores bot's message and the user's message in the order of the actual dialogue;
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
    private ArrayList<Speech> chatbotMessage;
    private ArrayList<Speech> userMessage;
    private ArrayList<String> stepSuggestion;
    private ArrayList<Double> confidenceScore;


    public Dialogue(ArrayList<Speech> chatbotMsg, ArrayList<Speech> userMsg){
        this.chatbotMessage = chatbotMsg;
        this.userMessage= userMsg;
    }

    public ArrayList<Speech> getChatbotMessage(){
        return this.chatbotMessage;
    }

    public ArrayList<Speech> getUserMessage(){
        return this.userMessage;
    }

    public void setConfidenceScore(double confidenceScore) {
        this.confidenceScore.add(confidenceScore);
    }

    public void setStepSuggestion(String talkStep) {
        this.stepSuggestion.add(talkStep);
    }

}