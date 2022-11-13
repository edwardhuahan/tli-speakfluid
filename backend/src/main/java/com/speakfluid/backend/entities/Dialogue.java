package main.java.com.speakfluid.backend.entities;

import java.util.ArrayList;

abstract class Dialogue {

    ArrayList<Speech> dialogue;
    String suggestedTalkStep;
    //do we want to set the confidence score in the SuggestionManager class?
    double confidenceScore;

    public abstract void setTalkStep(String talkstep);
    public abstract void setConfidenceScore(Double confidenceScore);

}