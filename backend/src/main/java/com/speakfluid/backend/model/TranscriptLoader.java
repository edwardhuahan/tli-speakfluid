package com.speakfluid.backend.model;

/**
 * TranscriptLoader is used to load raw transcripts
 * from MongoDB.
 *
 * @author  Kai Zhuang
 * @version 1.0
 * @since   2022-11-12
 */

public class TranscriptLoader {

    private String transcriptName;
    private String transcriptType;
    private String transcriptSize;
    private byte[] transcript;

    public TranscriptLoader() {
    }

    public String getTranscriptName() {
        return transcriptName;
    }

    public void setTranscriptname(String transcriptName) {
        this.transcriptName = transcriptName;
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