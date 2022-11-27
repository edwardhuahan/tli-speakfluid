package com.speakfluid.backend.entities;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static java.util.Map.entry;

/**
 * TextStep class stores and reflects the features of the TEXT Talk Step functionality of Voiceflow.
 *
 * Text talk step is mainly used when the bot wants to send a message or a notification to the user
 * and no user response is expected.
 *
 *
 *
 * scoreAccumulator is defined to keep track of how much the dialogue that we are analysing fits each feature of
 * Text talk step.
 * maxScore is setted as the score if the dialogue is compilable perfectly with Text talk step features.
 * A confidence score will be calculated as the quotient of scoreAccumulator and maxScore
 * to reflect how well the dialogue matches with the Text Talk Step's features.
 *
 * @author  Zoey Zhang
 * @version 1.0
 * @since   2022-11-12
 */
public class TextStep extends TalkStep {
    private double scoreAccumulator;
    private final double maxScore = 15.0;
    private final String stepName = "Text";
    private final List<Map<String, Double>> textKeyWordsChatBot = Arrays.asList(
            Map.ofEntries(entry("thank you for using", 5.0),entry("thank you for contacting", 5.0),
                    entry("thank you", 4.0), entry("thank you for", 4.5), entry("thanks", 4.0)),
            Map.ofEntries(entry("have a great day", 4.0), entry("have a good day", 4.0),
                    entry("good day", 2.0)),
            Map.ofEntries(entry("i am", 0.5), entry("my name is", 0.5)),
            Map.ofEntries(entry("goodbye", 4.5), entry("bye", 4.5)),
            Map.ofEntries(entry("glad to help", 5.0), entry("happy to help", 5.0))
    );

    private final List<Map<String, Double>> textKeyWordsUser = Arrays.asList(
            Map.ofEntries(entry("thank you for helping", 3.0), entry("thank you", 2.0),
                    entry("thanks", 2.0),entry("thx", 2.0), entry("appreciated", 2.0)),
            Map.ofEntries(entry("that's all", 2.0), entry("have a good day", 2.0),
                    entry("goodbye", 2.0), entry("bye", 2.0))
    );

    public TextStep(){
        this.scoreAccumulator = 0;
    }
    /**
     * isnotQuestion determines if each message in the Dialogue is a question or not.
     *
     * @param message message from first speaker(a chatbot or a user)
     * @return indicator which equals 10 if the message is not a question or 0 if the message is a question
     */
    public int isNotQuestion(Message message) {
        int indicator = 0;
        if (!(message.getMessage().contains("?"))) {
            indicator = 10;
        }
        return indicator;
    }

    /**
     * calculateConsecutiveBotMsg determines there is consecutive bot's messages,
     * with the previous message would be an informative message. Assume there are maximum 5 consecutive sentences.
     *
     * @param dialogue one back-and-forth conversation between the chatbot and the user.
     * @return the number of consecutive chatbot messages.
     */
    public int calculateConsecutiveBotMsg(Dialogue<?> dialogue) {

        int botMsgLength = dialogue.getChatBotMessage().size();
        int userMsgLength = dialogue.getUserMessage().size();
        int numConsecutives = 0;
        if (botMsgLength > userMsgLength){
            numConsecutives = botMsgLength - userMsgLength;
        }return numConsecutives;
    }

    /**
     * runAnalysis for TextStep determines:
     * 1. How many text keywords the bot's messages and user's messages have?
     * 2. If the bot's message is long
     * 3. If the bot's message is a question or not. If not then it has a high change to be a text step.
     * 4. How many consecutive messsages from the chatbot before any user response.
     *
     * @param dialogue one back-and-forth conversation between the chatbot and the user.
     */
    @Override
    public void runAnalysis(Dialogue<?> dialogue) {

        for (Object message : dialogue.getChatBotMessage()) {
            countMatchKeywords((Message) message, textKeyWordsChatBot);
            scoreAccumulator += isNotQuestion((Message) message);
            if (calculateMsgLength((Message) message) >= 10) {
                scoreAccumulator += 3;
            }
        }
        for (Object message : dialogue.getUserMessage()) {
            countMatchKeywords((Message) message, textKeyWordsUser);
        }

        scoreAccumulator += calculateConsecutiveBotMsg(dialogue) * 5;

    }

    public String getStepName(){
        return this.stepName;
    }

    public double getMaxScore(){
        return this.maxScore;
    }

    public double getScoreAccumulator(){
        return this.scoreAccumulator;
    }

    public void setZeroScoreAccumulator(){
        this.scoreAccumulator = 0;
    }

}