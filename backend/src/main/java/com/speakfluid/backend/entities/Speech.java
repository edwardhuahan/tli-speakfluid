package main.java.com.speakfluid.backend.entities;

abstract class Speech {
    String speaker;
    String timestamp;
    String traceType;
    String id;
    String traceFormat;
    String message;

    public Speech(String speaker, String timestamp, String traceType, String id, String traceFormat, String message){
        this.speaker = speaker;
        this.timestamp = timestamp;
        this.traceType = traceType;
        this.id = id;
        this.traceFormat = traceFormat;
        this.message = message;
    }

}
