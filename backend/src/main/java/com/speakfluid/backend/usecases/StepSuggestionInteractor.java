package java.com.speakfluid.backend.usecases;

import java.com.speakfluid.backend.entities.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
/**
 * The Speech object stores the specific information about
 * every message/interaction in a dialogue, including:
 * time stamp: a LocalDateTime variable that marks the time stamp of the message;
 * trace format: a string that identifies if it's the chatbot or the user;
 * message's ID: a string that identifies distinct messages;
 * trace type: a string identifies the initial talkStep;
 * message: a string contains the actual content of the message.
 * @author  Zoey Zhang
 * @version 1.0
 * @since   2022-11-12
 */

public class StepSuggestionInteractor{

    ButtonStep buttonStep = new ButtonStep();
    ImageStep imageStep = new ImageStep();
    CaptureStep captureStep = new CaptureStep();
    CarouselStep carouselStep = new CarouselStep();
    TextStep textStep = new TextStep();
    ChoiceStep choiceStep = new ChoiceStep();

    final ArrayList<TalkStep> steps = new ArrayList<>(
            Arrays.asList(
                    buttonStep, imageStep, captureStep,
                    carouselStep, textStep, choiceStep));
    StepManager stepManager = new StepManager();
    SuggestionManager suggestionManager = new SuggestionManager(stepManager, steps);

    public void storeStepSuggestConfidenceScore(ArrayList<ArrayList<Dialogue>> transcript){

        // To access all the different dialogues in the same session
        for (ArrayList<Dialogue> dialogues : transcript) {

            // To access each dialogue
            for (Dialogue dialogue : dialogues) {

                // Try and
                for (TalkStep step : steps) {
                    suggestionManager.callManager(step, dialogue);
                    HashMap<String, Double> suggestionPair = suggestionManager.findSuggestedTalkStep();
                    for (Map.Entry<String, Double> entry : suggestionPair.entrySet()) {
                        String stepSuggestion = entry.getKey();
                        double confidenceScore = entry.getValue();
                        dialogue.setStepSuggestion(stepSuggestion);
                        dialogue.setConfidenceScore(confidenceScore);
                    }
                }
            }
        }

        }
    }



