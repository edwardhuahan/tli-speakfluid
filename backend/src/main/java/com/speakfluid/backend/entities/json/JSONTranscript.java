package com.speakfluid.backend.entities.json;

import java.util.ArrayList;

public class JSONTranscript {

    /**
     * Used specifically to represent MultiWoz Transcript json files with the following structure:
     * {
     *      filename1:
     *          {
     *              "log": ...
     *          }
     *     filename2:
     *     ...
     * }
     *
     */
    private String fileName;
    private ArrayList<JSONDialogue> logs;

    public JSONTranscript(String fileName, ArrayList<JSONDialogue> logs) {
        this.fileName = fileName;
        this.logs = logs;
    }

    public ArrayList<JSONDialogue> getLogs() {
        return logs;
    }

    public String getFileName() {
        return fileName;
    }

    public void setLogs(ArrayList<JSONDialogue> logs) {
        this.logs = logs;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
