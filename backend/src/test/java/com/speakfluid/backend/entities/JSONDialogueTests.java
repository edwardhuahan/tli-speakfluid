package com.speakfluid.backend.entities;

import com.speakfluid.backend.entities.json.JSONDialogue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashMap;
import java.util.Map;


/**
 * Tests for the JSONDialogue class.
 *
 * @author  Minh Le
 * @version 1.0
 * @since   2022-12-06
 */

class JSONDialogueTests {

    static JSONDialogue dialogue = new JSONDialogue();
    static Map<String, Object> metadata = new HashMap<>();

    @BeforeAll
    public static void setUp() {
        metadata.put("key1", "value1");
        metadata.put("key2", "value2");
        dialogue.setText("text");
        dialogue.setMetadata(metadata);
    }


    @Test
    void testGetText() {
        String text = dialogue.getText();
        assertEquals("text", text);
    }

    @Test
    void testGetMetadata() {
        Map<String, Object> metadata = dialogue.getMetadata();
        assertEquals(metadata.get("key1"), "value1");
        assertEquals(metadata.get("key2"), "value2");
    }

}

