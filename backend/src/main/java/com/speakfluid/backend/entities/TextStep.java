package java.com.speakfluid.backend.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ChoiceStep analysizes incoming Dialogues to determine the
 * whether that chatbot output of the Dialogue is suitable for
 * a Choice Listen Step through a confidence score.
 *
 * Choice Listen Step is mainly used for "Yes" and "No" simple responses.
 *
 * @author  Zoey Zhang
 * @version 1.0
 * @since   2022-11-12
 */
public class TextStep extends TalkStep {
    private static int scoreAccumulator = 0;
    final int maxScore = 15; // maxscore can be final
    private double confidenceScore;
    final List<String> textKeyWords = Arrays.asList("hi there", "thank you for ",
            "have a great day", "thank you", "my name is", "i am", "happy to have you here", "glad to help"); // textKeyWrod can be final

    public int isStatement(Speech speech) {
        if (speech.getMessage().contains("?")) {
            return 0;
        }
        return 10;
    }

    public int isLastSentence(Dialogue dialogue) {
        ArrayList<Speech> messages = dialogue.getDialogue();
        int dialogueLength = messages.size();

        // check if the last sentence of the dialogue is a bot's message.
        if (messages.get(dialogueLength - 1).getTraceFormat().equals("trace")) {

            // then check if the last sentence is a statement instead of an answered question.
            if (isStatement(messages.get(dialogueLength - 1)) == 10) {
                return 15;
            }
        }
        return 0;
    }

    public int calculateConsecutiveBotMsg(Dialogue dialogue) {
        ArrayList<Speech> messages = dialogue.getDialogue();
        int dialogueLength = messages.size();
        int numConsecutives = 0;
        for (int i = 0; i < dialogueLength - 1; i++) {
            if (messages.get(i).getTraceFormat().equals("trace")) {   // check if it's a bot message or not
                if (messages.get(i + 1).getTraceFormat().equals("trace")) {  // check if the next message is form a bot
                    numConsecutives += 1;
                }
            }
        }return numConsecutives;
    }

    public void runAnalysis(Dialogue dialogue) {

        for (Speech message : dialogue.getDialogue()) {
            if (message.getTraceFormat().equals("trace")) {   // check if it's a bot message or not
                if (calculateMsgLength(message) >= 15) {
                    scoreAccumulator += 10;
                }
            }
        }

        for (Speech message : dialogue.getDialogue()) {
            if (message.getTraceFormat().equals("trace")) {   // check if it's a bot message or not
                scoreAccumulator += countMatchKeywords(message, textKeyWords) * 5;
            }
        }

        for (Speech message : dialogue.getDialogue()) {
            if (message.getTraceFormat().equals("trace")) {   // check if it's a bot message or not
                scoreAccumulator += isStatement(message) * 10;
            }
        }

        scoreAccumulator += isLastSentence(dialogue);
        scoreAccumulator += calculateConsecutiveBotMsg(dialogue) * 5;

    }

}