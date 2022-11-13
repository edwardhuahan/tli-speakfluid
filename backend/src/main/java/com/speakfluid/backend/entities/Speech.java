package com.speakfluid.backend.entities;
import java.time.LocalDateTime;
// Entity layer
public class Speech {
    // Store the specific information about every message/interaction in a dialogue, including:
    // time stamp;
    // trace format: a string that identifies if it's the chat bot or the user;
    // message's ID: a string that identifies distinct messages;
    // trace type: a string identifies the initial talkStep;
    // message: a string contains the actual content of the message.

    private LocalDateTime timeStamp;
    private String traceFormat;
    private String messageID;
    private String traceType;
    private String message;

    public Speech(LocalDateTime timeStamp,
                  String tFormat,
                  String id,
                  String tType,
                  String m){
        this.timeStamp = timeStamp;
        this.traceFormat = tFormat;
        this.messageID = id;
        this.traceType = tType;
        this.message = m;
    }

    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }

    public String getTraceFormat(){
        return this.traceFormat;
    }

    public String getMessageID(){
        return this.messageID;
    }

    public String getTraceType(){
        return this.traceType;
    }


    public String getMessage() {
        return this.message;
    }

}
