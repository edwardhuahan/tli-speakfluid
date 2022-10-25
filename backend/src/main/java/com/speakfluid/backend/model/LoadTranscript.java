package com.speakfluid.backend.model;

public class LoadTranscript {

    private String transcriptname;
    private String transcriptType;
    private String transcriptSize;
    private byte[] transcript;

    public LoadTranscript() {
    }

    public String getTranscriptname() {
        return transcriptname;
    }

    public void setTranscriptname(String transcriptname) {
        this.transcriptname = transcriptname;
    }

    public String getTranscriptType() {
        return transcriptType;
    }

    public void setTranscriptType(String transcriptType) {
        this.transcriptType = transcriptType;
    }

    public String getTranscriptSize() {
        return transcriptSize;
    }

    public void setTranscriptSize(String transcriptSize) {
        this.transcriptSize = transcriptSize;
    }

    public byte[] getTranscript() {
        return transcript;
    }

    public void setTranscript(byte[] transcript) {
        this.transcript = transcript;
    }
}