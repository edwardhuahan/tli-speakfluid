package com.speakfluid.backend.entities;
import java.lang.String;
// import java.time.Duration;
// import java.util.ArrayList;
// import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * The TalkStep abstract class is parent to all Step entities,
 * with useful and common analysis methods implemented for use.
 *
 * @author  Sarah Xu, Minh Le
 * @version 2.0
 * @since   2022-11-18
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
     * @param message message from first speaker
     * @return the length of a message
     */
    public int calculateMsgLength(Message message){
        return message.getMessage().split(" ").length;
    }

    /**
     * countMatchKeywords finds if words in a chatbot/user message matches
     * keywords in a keywordCluster, and adds to the ScoreAccumulator
     * the weighting of that keyword if there is a match.
     * @param message speech from first speaker
     * @param keywords  list of maps of keyword to weighting, where each map is a keywordCluster
     */
    public int countMatchKeywords(Message message, List<Map<String, Double>> keywords){
        int totalMatchWeighting = 0;
        for(Map<String, Double> keywordCluster: keywords){
            for(Map.Entry<String, Double> keyword: keywordCluster.entrySet()){
                if(message.getMessage().contains(keyword.getKey())){
                    totalMatchWeighting += keyword.getValue();
                }
            }
        }
        return totalMatchWeighting;
    }

    // /**
    //  * calculateResponseTime finds the difference in minutes in a
    //  * dialogue between a question and response, which are two
    //  * Speech.timeStamp() LocalDateTime objects
    //  * @param question speech from first speaker
    //  * @param response  speech from second speaker
    //  * @return long the number of numbers between the two timeStamps
    //  */
    // public long calculateResponseTime(WozMessage question, WozMessage response){
    //     LocalDateTime d1 = question.getTimeStamp();
    //     LocalDateTime d2 = response.getTimeStamp();
    //     Duration difference = Duration.between(d1, d2);
    //     long minutes = difference.toMinutes();
    //     long seconds = difference.toSeconds();
    //     return minutes*60 + seconds;
    // }

    public abstract void runAnalysis(Dialogue dialogue);

}