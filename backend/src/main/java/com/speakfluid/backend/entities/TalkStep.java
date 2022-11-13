package main.java.com.speakfluid.backend.entities;

abstract class TalkStep {
    public String stepName;
    public static double maxScore;
    public static double scoreAccumulator;

    public abstract void calculateChatbotMsgLength(Dialogue dialogue);
    public abstract void calculateUserMsgLength(Dialogue dialogue);
    public abstract void countMatchKeywords(Dialogue dialogue);
    public abstract void calculateChatbotToUserMsgRatio();
    


}