package com.speakfluid.backend.entities.json;

import java.util.Map;

public class JSONDialogue {

    /**
     * Used specifically to represent MultiWoz Transcript json files with the following structure:
     * {
     *      filename1:
     *          {
     *              "log": [
     *                  {
     *                      "text": ...
     *                      metadata: {
     *                          ...
     *                      }
     *                  }
     *              ]
     *          }
     *     filename2:
     *     ...
     * }
     *
     */
    private String text;
    private Map<String, Object> metadata;
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Map<String, Object> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
    }
}
