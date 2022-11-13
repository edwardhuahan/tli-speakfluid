package com.speakfluid.backend.entities;
import java.lang.String;
import java.time.Duration;
import java.util.ArrayList;
import java.time.LocalDateTime;

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

    public void setMaxScore(int maxScore){
        this.maxScore = maxScore;
    }

    public double getMaxScore(){
        return this.maxScore;
    }

    public void setScoreAccumulator(int scoreAccum){
        this.scoreAccumulator = scoreAccum;
    }

    public double getScoreAccumulator(){
        return this.scoreAccumulator;
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
     * countMatchKeywords finds the difference in minutes in a
     * dialogue between a question and response, which are two
     * Speech.timeStamp() LocalDateTime objects
     * @param speech speech from first speaker
     * @param keywords  speech from second speaker
     * @return the number of matches between speech message and keywords
     */
    public int countMatchKeywords(Speech speech, ArrayList<String> keywords){
        int numMatches = 0;
        for (String word: speech.getMessage().split(" ")){
            if (keywords.contains(word)) numMatches++;
        }
        return numMatches;
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
}