package com.speakfluid.backend.entities;

import com.speakfluid.backend.entities.json.JSONDialogue;
import com.speakfluid.backend.entities.json.JSONTranscript;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


/**
 * Tests for the JSONTranscript class.
 *
 * @author  Minh Le
 * @version 1.0
 * @since   2022-12-06
 */

class JSONTranscriptTests {

    static JSONDialogue dialogue1 = new JSONDialogue();
    static JSONDialogue dialogue2 = new JSONDialogue();
    static Map<String, Object> metadata = new HashMap<>();


    static ArrayList<JSONDialogue> logs = new ArrayList<JSONDialogue>();

    @BeforeAll
    public static void setUp() {
        metadata.put("key1", "value1");
        metadata.put("key2", "value2");

        dialogue1.setText("text1");
        dialogue1.setMetadata(metadata);

        dialogue2.setText("text2");
        dialogue2.setMetadata(metadata);

        logs.add(dialogue1);
        logs.add(dialogue2);

    }


    @Test
    void testGetLogs() {
        JSONTranscript transcript = new JSONTranscript("file.json", logs);

        assertEquals(transcript.getLogs().get(0).getText(), "text1");
        assertEquals(transcript.getLogs().get(1).getText(), "text2");
        
    }

    @Test
    void testGetFileName() {
        JSONTranscript transcript = new JSONTranscript("file.json", logs);
        assertEquals(transcript.getFileName(), "file.json");
        
    }

}

