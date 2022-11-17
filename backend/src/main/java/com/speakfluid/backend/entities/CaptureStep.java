package com.speakfluid.backend.entities;

import java.lang.String;
import java.lang.reflect.Array;
import java.util.*;
import static java.util.Map.entry;

/**
 * ChoiceStep analysizes incoming Dialogues to determine the
 * whether that chatbot output of the Dialogue is suitable for
 * a Choice Listen Step through a confidence score.
 *
 * Choice Listen Step is mainly used for "Yes" and "No" simple responses.
 *
 * @author  Sarah Xu
 * @version 2.0
 * @since   2022-11-16
 */
public class CaptureStep extends TalkStep {
    private final String stepName = "Capture";
    private int scoreAccumulator = 0;
    private final int maxScore = 30;
    private double confidenceScore;
    private final List<Map<String, Double>> captureKeyWordsChatbot =
            Arrays.asList(
                    Map.ofEntries(entry("please provide", 5.0), entry("please enter", 5.0), entry("what is your", 5.0),
                            entry("provide your", 4.0), entry("provide the", 4.0), entry("enter your", 4.0),
                            entry("enter the", 4.0), entry("provide", 3.0), entry("enter", 3.0)),
                    Map.ofEntries(entry("email address", 5.0), entry("email", 4.0), entry("e-mail", 4.0)),
                    Map.ofEntries(entry("order number", 5.0), entry("number", 3.0)),
                    Map.ofEntries(entry("what time", 5.0), entry("which date", 5.0), entry("which day", 5.0),
                            entry("when", 3.0), entry("time", 3.0), entry("date", 3.0), entry("day", 3.0)),
                    Map.ofEntries(entry("address", 5.0), entry("zip code", 5.0), entry("location", 4.0)),
                    Map.ofEntries(entry("name", 5.0), entry("full name", 5.0), entry("address you", 3.0))
            );

    public CaptureStep(){
        this.scoreAccumulator = 0;
    }

    // includes special characters, week days, times, address, emails
    private final List<Map<String, Double>> captureKeyWordsUsers =
            Arrays.asList(
                    Map.ofEntries(entry("@", 5.0), entry(".com", 5.0), entry(".org", 5.0),
                            entry("email", 3.0)),
                    Map.ofEntries(entry("pm", 5.0), entry("p.m.", 5.0), entry("a.m.", 5.0),
                            entry("am", 5.0), entry("night", 5.0), entry("noon", 5.0),
                            entry("morning.", 5.0), entry("time", 3.0), entry("available", 3.0)),
                    Map.ofEntries(entry("monday", 5.0), entry("tuesday", 5.0), entry("wednesday", 5.0),
                            entry("thursday", 5.0), entry("friday", 5.0), entry("saturday", 5.0),
                            entry("sunday.", 5.0), entry("tomorrow", .0), entry("today", 4.0),
                            entry("tonight", 4.0), entry("weekend", 4.0)),
                    Map.ofEntries(entry("address", 5.0), entry("zip code", 5.0), entry("location", 4.0),
                            entry("home", 4.0), entry("zip", 3.0), entry("code", 3.0))
            );


    /**
     * hasNumbers() is used for situation where a user inputs numbers,
     * such as for phone number, address, date, time, email, etc. which
     * are indications that a capture step should be used to capture these
     * specific, or personal information.
     * @param speech one message from user
     */
    public void hasNumbers(Speech speech){
        for(String word : speech.getMessage().split(" ")){
            if(speech.getMessage().matches(".*[0-9].*")){
                scoreAccumulator += 3;
                break;
            }
        }
    }

    /**
     * isEmail() is checks if user provides an email address,
     * and adds 10 to scoreAccumulator because it is important
     * to use the Capture Step here.
     * @param speech one message from user
     */
    public void isEmail(Speech speech){
        for(String word : speech.getMessage().split(" ")){
            if(speech.getMessage().matches("\\A[A-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[A-Z0-9.-]+\\Z")){
                scoreAccumulator += 10;
                break;
            }
        }
    }

    /**
     * isZipCode() is used for situation where a user provides
     * their zipcode. The current method checks for US and Canadian ZipCodes
     * @param speech one message from user
     */
    public void isZipCode(Speech speech){
        for(String word : speech.getMessage().split(" ")){
            if(speech.getMessage().matches( "^\\d{5}([-+]?\\d{4})?$")
                    || speech.getMessage().matches( "/^[A-Za-z]\\d[A-Za-z][ -]?\\d[A-Za-z]\\d$/")){
                scoreAccumulator += 10;
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
    public void runAnalysis(Dialogue dialogue){
        // Chatbot messages
        for (Speech chatbotMessage : dialogue.getChatBotMessage()){
            countMatchKeywords(chatbotMessage, captureKeyWordsChatbot);
        }
        // User messages
        for (Speech userMessage : dialogue.getUserMessage()){
            countMatchKeywords(userMessage, captureKeyWordsChatbot);
            if(calculateMsgLength(userMessage) <= 3) {
                scoreAccumulator += 5;
            }
            hasNumbers(userMessage);
            isZipCode(userMessage);
            isEmail(userMessage);
        }
    }

}