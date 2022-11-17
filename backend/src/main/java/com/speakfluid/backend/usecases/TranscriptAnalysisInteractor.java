package com.speakfluid.backend.usecases;

import com.speakfluid.backend.entities.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * TranscriptAnalysisInteractor is SpeakFluid's use case interactor for the use case of analyzing transcripts.
 * It generates 6 TalkStep entities, 1 ArrayList of Talkstep, 1 StepManager, 1 SuggestionManager.
 * Then the interactor has storeStepSuggestionPair, which stores the suggested step to each dialogue
 * with the corresponding confidence score in the Dialogue object's stepSuggestion and confidenceScore attributes.
 *
 * @author  Zoey Zhang
 * @version 2.0
 * @since   2022-11-17
 */

// Use Case Layer
public class TranscriptAnalysisInteractor implements TranscriptAnalysisInputBoundary{

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
    StepManager stepManager = new StepManager();
    SuggestionManager suggestionManager = new SuggestionManager(stepManager, steps);


    /**
     * analyzeTranscript calls SuggestionManager then stores the suggested talk step and its corresponding confidence
     * score to each Dialogue.Lastly return the analyzed transcript.
     *
     * @param transcript the parsed transcript in the structure of ArrayList<HashMap<String, ArrayList<Dialogue>>>.
     *
     *                   The parsed transcript is a list of conversations id mapping to a list of Dialogue objects.
     *                   Dialogue object represents one conversation between the bot and the user.
     *                   It contains two Arraylists of Speech object, representing detailed information of bot's message
     *                   and user's message respectively.
     * @return the analyzed transcript which contains Dialogue objects with updated stepSuggestion and confidenceScore.
     */
    @Override
    public ArrayList<HashMap<String, ArrayList<Dialogue>>> analyzeTranscript(
            ArrayList<HashMap<String, ArrayList<Dialogue>>> transcript) {

        // To access all the different id-to-conversation-content pairs in the same session
        for (HashMap<String, ArrayList<Dialogue>> idToConversations : transcript) {

            // To access the conversations in the id-to-conversation-content pairs
            for (ArrayList<Dialogue> conversation : idToConversations.values()) {

                // To access each back and forth dialogue within each conversation
                for (Dialogue dialogue : conversation) {
                    suggestionManager.callStepManager(dialogue);
                    /* callStepManager is called to initiate all the TalkSteps and their own analysis for
                    the individual confidence score
                     */

                    HashMap<String, Double> suggestionPair = suggestionManager.findSuggestedTalkStep();
                    /* findSuggestedTalkStep is called to compare all the individual confidence score from the step
                    entities to return a mapping of the name and the confidence score of the step that has
                    the highest score.
                    We decide to return the top three step-suggestions with the score if the top one score is low.Thus,
                    the following for loop gives the option to append more than one step-score-pair.
                     */
                    for (Map.Entry<String, Double> entry : suggestionPair.entrySet()) {
                        String stepSuggestion = entry.getKey();
                        double confidenceScore = entry.getValue();
                        dialogue.setStepSuggestion(stepSuggestion);
                        dialogue.setConfidenceScore(confidenceScore);
                    }
                }
            }
        } return transcript;
    }
}