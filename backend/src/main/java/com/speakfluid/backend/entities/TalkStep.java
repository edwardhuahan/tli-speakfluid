<<<<<<< HEAD
package java.com.speakfluid.backend.entities;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
=======
package com.speakfluid.backend.entities;
import java.lang.String;
import java.time.Duration;
import java.util.ArrayList;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
>>>>>>> 8046603679cec5cf9f57649ac2274456f5e080da

/**
 * The TalkStep abstract class is parent to all Step entities,
 * with useful and common analysis methods implemented for use.
 *
 * @author  Sarah Xu
 * @version 1.0
 * @since   2022-11-12
 */
abstract public class TalkStep {
<<<<<<< HEAD
    private static int scoreAccumulator;
    private int maxScore; // = A CONSTANT
=======
    private int scoreAccumulator;
    private int maxScore;
    private String stepName;

    public String getStepName(){
        return this.stepName;
    }
>>>>>>> 8046603679cec5cf9f57649ac2274456f5e080da

    public double getMaxScore(){
        return this.maxScore;
    }

<<<<<<< HEAD
    public void setScoreAccumulator(int scoreAccum){
        scoreAccumulator = scoreAccum;
    }

    public double getScoreAccumulator(){
        return scoreAccumulator;
=======
    public double getScoreAccumulator(){
        return this.scoreAccumulator;
    }

    public void setZeroScoreAccumulator(){
        this.scoreAccumulator = 0;
>>>>>>> 8046603679cec5cf9f57649ac2274456f5e080da
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
<<<<<<< HEAD
     * countMatchKeywords find the number of matching words in
     * speech message and keywords.
     * @param speech speech from first speaker
     * @param keywords  speech from second speaker
     * @return the number of matches between speech message and keywords
     */
    public int countMatchKeywords(Speech speech, List<String> keywords){
        int numMatches = 0;
        for (String word: speech.getMessage().split(" ")){
            if (keywords.contains(word)) numMatches++;
        }
        return numMatches;
=======
     * countMatchKeywords finds if words in a chatbot/user message matches
     * keywords in a keywordCluster, and adds to the ScoreAccumulator
     * the weighting of that keyword if there is a match.
     * @param speech speech from first speaker
     * @param keywords  list of maps of keyword to weighting, where each map is a keywordCluster
     */
    public void countMatchKeywords(Speech speech, ArrayList<Map<String, Double>> keywords){
        for(Map<String, Double> keywordCluster: keywords){
            for(Map.Entry<String, Double> keyword: keywordCluster.entrySet()){
                if(speech.getMessage().contains(keyword.getKey())){
                    scoreAccumulator += keyword.getValue();
                    break;
                }
            }
        }
>>>>>>> 8046603679cec5cf9f57649ac2274456f5e080da
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
<<<<<<< HEAD
=======

    public abstract void runAnalysis(Dialogue dialogue);

>>>>>>> 8046603679cec5cf9f57649ac2274456f5e080da
}