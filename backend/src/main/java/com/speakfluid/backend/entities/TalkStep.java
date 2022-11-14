package com.speakfluid.backend.entities;
import java.lang.String;
import java.time.Duration;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The TalkStep abstract class is parent to all Step entities,
 * with useful and common analysis methods implemented for use.
 *
 * @author  Sarah Xu
 * @version 1.0
 * @since   2022-11-12
 */
abstract public class TalkStep {
    private int scoreAccumulator;
    private int maxScore;
    private String stepName;

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
     * calculateMsgLength finds the length of a chatbot or user
     * message by the number of words
     * @param speech speech from first speaker
     * @return the length of a speech message
     */
    public int calculateMsgLength(Speech speech){
        return speech.getMessage().split(" ").length;
    }

    /**
     * countMatchKeywords find the number of matching words in
     * speech message and keywords and adds it to scoreAccumulator
     * @param speech speech from first speaker
     * @param keywords  list of list of keyword groups with mappings of keyword to weighting
     */
    public void countMatchKeywords(Speech speech, ArrayList<ArrayList<Map<String, Double>>> keywords){
        for (ArrayList<Map<String,Double>> commonUtterances: keywords){
            for(Map<String,Double> utteranceGroup : commonUtterances){
                for(Map.Entry<String, Double> entry: utteranceGroup.entrySet()) {
                    if(speech.getMessage().contains(entry.getKey())){
                        scoreAccumulator += entry.getValue();
                    }

                }
            }
        }
    }

    /**
     * calculateResponseTime finds the difference in minutes in a
     * dialogue between a question and response, which are two
     * Speech.timeStamp() LocalDateTime objects
     * @param question speech from first speaker
     * @param response  speech from second speaker
     * @return long the number of numbers between the two timeStamps
     */
    public long calculateResponseTime(Speech question, Speech response){
        LocalDateTime d1 = question.getTimeStamp();
        LocalDateTime d2 = response.getTimeStamp();
        Duration difference = Duration.between(d1, d2);
        long minutes = difference.toMinutes();
        long seconds = difference.toSeconds();
        return minutes*60 + seconds;
    }

    public abstract void runAnalysis();

}