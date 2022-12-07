package com.speakfluid.backend.entities.steps;

import com.speakfluid.backend.entities.ScoreStandards;
import com.speakfluid.backend.entities.message.Dialogue;
import com.speakfluid.backend.entities.message.Message;

import java.lang.String;
import java.util.*;
import static java.util.Map.entry;

/**
 * ChoiceStep analyses incoming Dialogues to determine the
 * whether that chatbot output of the Dialogue is suitable for
 * a Choice Listen Step through a confidence score.
 *
 * Choice Listen Step is mainly used for "Yes" and "No" simple responses.
 *
 * @author  Sarah Xu
 * @version 3.0
 * @since   2022-11-28
 */
public class CaptureStep extends TalkStep {
    private final String stepName = "Capture";
    private double scoreAccumulator = 0;
    private final double maxScore = ScoreStandards.standardStepClass + 2 * ScoreStandards.additionalMethod;
    private final List<Map<String, Double>> captureKeyWordsChatbot =
            Arrays.asList(
                    Map.ofEntries(entry("please provide", ScoreStandards.highMatch),
                            entry("please enter", ScoreStandards.highMatch),
                            entry("what is your", ScoreStandards.highMatch),
                            entry("enter your", ScoreStandards.mediumMatch),
                            entry("provide", ScoreStandards.lowMatch),
                            entry("enter", ScoreStandards.lowMatch)),
                    Map.ofEntries(entry("email", ScoreStandards.highMatch),
                            entry("e-mail", ScoreStandards.highMatch)),
                    Map.ofEntries(entry("order", ScoreStandards.highMatch),
                            entry("receipt", ScoreStandards.mediumMatch),
                            entry("number", ScoreStandards.lowMatch)),
                    Map.ofEntries(entry("what time", ScoreStandards.highMatch),
                            entry("which date", ScoreStandards.highMatch),
                            entry("which day", ScoreStandards.highMatch),
                            entry("when", ScoreStandards.mediumMatch),
                            entry("where", ScoreStandards.mediumMatch),
                            entry("time", ScoreStandards.mediumMatch),
                            entry("date", ScoreStandards.lowMatch),
                            entry("day", ScoreStandards.lowMatch)),
                    Map.ofEntries(entry("address", ScoreStandards.highMatch),
                            entry("zip code", ScoreStandards.highMatch),
                            entry("location", ScoreStandards.highMatch)),
                    Map.ofEntries( entry("name", ScoreStandards.mediumMatch),
                            entry("address", ScoreStandards.mediumMatch))
            );
    // includes special characters, week days, times, address, emails
    private final List<Map<String, Double>> captureKeyWordsUsers =
            Arrays.asList(
                    Map.ofEntries(entry("@", ScoreStandards.highMatch),
                            entry(".com", ScoreStandards.highMatch),
                            entry(".org", ScoreStandards.highMatch),
                            entry("email", ScoreStandards.highMatch)),
                    Map.ofEntries(entry("pm", ScoreStandards.highMatch),
                            entry("p.m.", ScoreStandards.highMatch),
                            entry("a.m.", ScoreStandards.highMatch),
                            entry("am", ScoreStandards.highMatch),
                            entry("night", ScoreStandards.highMatch),
                            entry("noon", ScoreStandards.mediumMatch),
                            entry("morning.", ScoreStandards.mediumMatch),
                            entry("time", ScoreStandards.mediumMatch),
                            entry("available", ScoreStandards.lowMatch)),
                    Map.ofEntries(entry("monday", ScoreStandards.highMatch),
                            entry("tuesday", ScoreStandards.highMatch),
                            entry("wednesday", ScoreStandards.highMatch),
                            entry("thursday", ScoreStandards.highMatch),
                            entry("friday", ScoreStandards.highMatch),
                            entry("saturday", ScoreStandards.highMatch),
                            entry("sunday.", ScoreStandards.highMatch),
                            entry("tomorrow", ScoreStandards.mediumMatch),
                            entry("today", ScoreStandards.mediumMatch),
                            entry("night", ScoreStandards.mediumMatch),
                            entry("weekend", ScoreStandards.mediumMatch)),
                    Map.ofEntries(entry("address", ScoreStandards.highMatch),
                            entry("zip code", ScoreStandards.highMatch),
                            entry("postal code", ScoreStandards.highMatch),
                            entry("location", ScoreStandards.mediumMatch),
                            entry("home", ScoreStandards.mediumMatch),
                            entry("code", ScoreStandards.lowMatch))
            );


    public CaptureStep(){
        this.scoreAccumulator = 0;
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

    /**
     * hasNumbers() is used for situation where a user inputs numbers,
     * such as for phone number, address, date, time, email, etc. which
     * are indications that a capture step should be used to capture these
     * specific, or personal information.
     * @param message one message from user
     */
    public void hasNumbers(Message message){
        for(String word : message.getMessage().split(" ")){
            if(word.matches(".*[0-9].*")){
                scoreAccumulator += ScoreStandards.lowMatch;
                break;
            }
        }
    }

    /**
     * isEmail() is checks if user provides an email address,
     * and adds 10 to scoreAccumulator because it is important
     * to use the Capture Step here.
     * @param message one message from user
     */
    public void isEmail(Message message){
        for(String word : message.getMessage().split(" ")){
            if(word.matches("^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")){
                scoreAccumulator += ScoreStandards.mediumMatch;
                break;
            }
        }
    }

    /**
     * isZipCode() is used for situation where a user provides
     * their zipcode. The current method checks for US and Canadian ZipCodes
     * @param message one message from user
     */
    public void isZipCode(Message message){
        for(String word : message.getMessage().split(" ")){
            if(word.matches( "^\\d{5}([-+]?\\d{4})?$")  //american postal code
                    || word.matches( "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z]●?[0-9][A-Z][0-9]$") //full canadian postal code
                    || word.matches( "^(?!.*[DFIOQU])[A-VXY][0-9][A-Z]")){ //half a canadian (such as M5S) since people often put a space in between the two parts
                scoreAccumulator += ScoreStandards.mediumMatch;
                break;
            }
        }
    }

    /**
     * runAnalysis() takes in a Dialogue object and analyses
     * whether the Capture Step is an appropriate ListenStep here.
     * It increases scoreAccumulator once there is a sign that
     * the dialogue is compatible with a Capture Step.
     * @param dialogue one back and forth between chatbot and user
     */
    public void runAnalysis(Dialogue<?> dialogue){
        // Chatbot messages
        for (Object chatbotMessage : dialogue.getChatBotMessage()){
            scoreAccumulator += countMatchKeywords((Message) chatbotMessage, captureKeyWordsChatbot);
        }
        // User messages
        for (Object userMessage : dialogue.getUserMessage()){
            scoreAccumulator += countMatchKeywords((Message) userMessage, captureKeyWordsUsers);
            hasNumbers((Message) userMessage);
            isZipCode((Message) userMessage);
            isEmail((Message) userMessage);
        }
    }


}