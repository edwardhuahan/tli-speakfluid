package com.speakfluid.backend.usecases;

import com.speakfluid.backend.entities.*;
import com.speakfluid.backend.entities.message.Dialogue;
import com.speakfluid.backend.entities.message.DialogueList;
import com.speakfluid.backend.entities.message.Transcript;
import com.speakfluid.backend.entities.message.WozMessage;
import com.speakfluid.backend.entities.steps.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * WozWozTranscriptAnalysisInteractor is SpeakFluid's use case interactor for the use case of analyzing transcripts.
 * It generates 6 TalkStep entities, 1 ArrayList of Talkstep, 1 StepManager, 1 SuggestionManager.
 * Then the interactor has storeStepSuggestionPair, which stores the suggested step to each dialogue
 * with the corresponding confidence score in the Dialogue object's stepSuggestion and confidenceScore attributes.
 *
 * @author  Zoey Zhang
 * @version 2.0
 * @since   2022-11-17
 */

// Use Case Layer
public class WozTranscriptAnalysisInteractor implements WozTranscriptAnalysisInputBoundary {

    // Generate all TalkStep entities,except Card
    ButtonStep buttonStep = new ButtonStep();
    ImageStep imageStep = new ImageStep();
    CaptureStep captureStep = new CaptureStep();
    CarouselStep carouselStep = new CarouselStep();
    TextStep textStep = new TextStep();
    ChoiceStep choiceStep = new ChoiceStep();
    CardStep cardStep = new CardStep();

    // Make an ArrayList of TalkStep entities to be passed in SuggestionManager
    final ArrayList<TalkStep> steps = new ArrayList<>(
            Arrays.asList(
                    buttonStep, imageStep, captureStep,
                    carouselStep, textStep, choiceStep, cardStep));

    // Generate StepManager and SuggestionManager
    ConfidenceScoreCalculator confidenceScoreCalculator = new ConfidenceScoreCalculator();
    ConfidenceScoreOptimizer confidenceScoreOptimizer = new ConfidenceScoreOptimizer(confidenceScoreCalculator, steps);


    /**
     * analyzeTranscript calls SuggestionManager then stores the suggested talk step and its corresponding confidence
     * score to each Dialogue.Lastly return the analyzed transcript.
     *
     * @param transcript the parsed transcript in the structure of ArrayList<HashMap<String, ArrayList<Dialogue>>>.
     *
     *                   The parsed transcript is a list of interactions id mapping to a list of Dialogue objects.
     *                   Dialogue object represents one conversation between the bot and the user.
     *                   It contains two Arraylists of Message object, representing detailed information of bot's message
     *                   and user's message respectively.
     * @return the analyzed transcript which contains Dialogue objects with updated stepSuggestion and confidenceScore.
     */
    @Override
    public ArrayList<Transcript> analyzeTranscript(ArrayList<Transcript> transcript) {

        // To access all the different id-to-conversation-content pairs in the same session
        for (Transcript idToConversations : transcript) {

            // To access the conversations in the id-to-conversation-content pairs
            for (DialogueList conversation : idToConversations.values()) {

                // To access each back and forth dialogue within each conversation
                for (Dialogue<WozMessage> dialogue : conversation) {
                    if (dialogue.getChatBotMessage().size() != 0){
                        confidenceScoreOptimizer.callConfidenceScoreCalculator(dialogue);
                    /* callStepManager is called to initiate all the TalkSteps and their own analysis for
                    the individual confidence score
                     */

                        ArrayList<Map<String, Double>> suggestionPair = confidenceScoreOptimizer.rankTalkSteps();
                    /* findSuggestedTalkStep is called to return an ArrayList of Maps, where every TalkStep is
                    ranked from highest to lowest confidence score. This is returned as a response model to the frontend
                    in order to be used for analytics displays.

                    The dialogue attributes of step suggestion and confidence score are amended accordingly.
                     */
                        for(Map<String, Double> talkStepPair: suggestionPair) {
                            for(Map.Entry<String, Double> entry : talkStepPair.entrySet()) {
                                String stepSuggestion = entry.getKey();
                                double confidenceScore = entry.getValue();
                                dialogue.addStepSuggestion(stepSuggestion);
                                dialogue.addConfidenceScore(confidenceScore);
                            }
                        }


                    }

                }
            }
        } return transcript;
    }
}