package com.speakfluid.backend.entities;

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
    private ArrayList<JSONLog> logs;

    public JSONTranscript(String fileName, ArrayList<JSONLog> logs) {
        this.fileName = fileName;
        this.logs = logs;
    }

    public ArrayList<JSONLog> getLogs() {
        return logs;
    }

    public String getFileName() {
        return fileName;
    }

    public void setLogs(ArrayList<JSONLog> logs) {
        this.logs = logs;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
