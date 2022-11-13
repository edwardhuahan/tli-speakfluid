package main.java.com.speakfluid.backend.entities;

import java.util.*;

 public class SuggestionManager {

     ArrayList<TalkStep> steps;
     HashMap<String, Double> talkStepToScoreMapping = new HashMap<>();
     StepManager stepManager;
     double stepConfidenceScore;
     double highestConfidenceScore;

     public SuggestionManager(StepManager manager, ArrayList<TalkStep> stepList) {
         this.stepManager = manager;
         this.steps = stepList;
     }

     /**
      * Calls the StepManager object to implement all methods for each TalkStep and store the
      * returned confidence score in the talkStepToScoreMapping HashMap to keep track of the
      * talk step to its confidence score
      * @param step the TalkStep whose methods are being called
      * @param dialogue the dialogue ArrayList containing the Speeches of the user and chatbot
      */
     public void callManager(TalkStep step, Dialogue dialogue){
         for(TalkStep talkStep: steps){
             stepManager.passDialogueToTalkStep(dialogue, talkStep);
             stepConfidenceScore = stepManager.calculateConfidenceScore();
             talkStepToScoreMapping.put(step.stepName, stepConfidenceScore);
         }
     }

     /**
      * Returns the talk step with the highest confidence score from the talkStepToScoreMapping.
      * @return String returns the suggested talk step as a String.
      */
     public String findSuggestedTalkStep() {
          highestConfidenceScore = Collections.max(talkStepToScoreMapping.values());
          for(Map.Entry<String, Double> entry: talkStepToScoreMapping.entrySet()) {
              if(entry.getValue() == highestConfidenceScore){
                  //what if there are multiple talk steps with the same confidence score?
                  return entry.getKey();
              }
          }
          return ""; //just because java nags me to return a String
     }















}