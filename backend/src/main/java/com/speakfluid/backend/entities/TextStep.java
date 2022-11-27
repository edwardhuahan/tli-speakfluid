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
    private final double maxScore = ScoreStandards.standardStepClass + ScoreStandards.additionalAnalysis* 3;
    private final List<Map<String, Double>> textKeyWordsChatBot = Arrays.asList(
            Map.ofEntries(entry("thank you for using", ScoreStandards.highMatch),
                    entry("thank you for contacting", ScoreStandards.highMatch),
                    entry("thank you", ScoreStandards.mediumMatch),
                    entry("thank you for", ScoreStandards.mediumMatch), entry("thanks", ScoreStandards.mediumMatch)),
            Map.ofEntries(entry("have a great day", ScoreStandards.mediumMatch),
                    entry("have a good day", ScoreStandards.mediumMatch),
                    entry("good day", ScoreStandards.mediumMatch)),
            Map.ofEntries(entry("i am", ScoreStandards.lowMatch), entry("my name is", ScoreStandards.lowMatch)),
            Map.ofEntries(entry("goodbye", ScoreStandards.highMatch), entry("bye", ScoreStandards.highMatch)),
            Map.ofEntries(entry("glad to help", ScoreStandards.highMatch),
                    entry("happy to help", ScoreStandards.highMatch))
    );

    private final List<Map<String, Double>> textKeyWordsUser = Arrays.asList(
            Map.ofEntries(entry("thank you for helping", ScoreStandards.highMatch),
                    entry("thank you", ScoreStandards.highMatch),
                    entry("thanks", ScoreStandards.highMatch),entry("thx", ScoreStandards.mediumMatch),
                    entry("appreciated", ScoreStandards.mediumMatch)),
            Map.ofEntries(entry("that's all", ScoreStandards.highMatch),
                    entry("have a good day", ScoreStandards.highMatch),
                    entry("goodbye", ScoreStandards.mediumMatch), entry("bye", ScoreStandards.mediumMatch))
    );

    public TextStep(){
        this.scoreAccumulator = 0;
    }

    public String getStepName(){
        String stepName = "Text";
        return stepName;
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

    /**
     * isnotQuestion determines if each message in the Dialogue is a question or not.
     *
     * @param message message from first speaker(a chatbot or a user)
     * @return indicator which equals 10 if the message is not a question or 0 if the message is a question
     */
    public int isNotQuestion(Message message) {
        int indicator = 0;
        if (!(message.getMessage().contains("?"))) {
            indicator += ScoreStandards.highMatch;
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
            scoreAccumulator += countMatchKeywords((Message) message, textKeyWordsChatBot);
            scoreAccumulator += isNotQuestion((Message) message);
            if (scoreAccumulator != 0.0 && calculateMsgLength((Message) message) >= 10) {
                scoreAccumulator += ScoreStandards.lowMatch;
            }
        }
        for (Object message : dialogue.getUserMessage()) {
            scoreAccumulator += countMatchKeywords((Message) message, textKeyWordsUser);
        }

        scoreAccumulator += calculateConsecutiveBotMsg(dialogue) * ScoreStandards.mediumMatch;

    }


}