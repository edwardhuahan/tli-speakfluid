package com.speakfluid.backend.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.time.LocalDateTime;


/**
 * Tests for the Speech class.
 *
 * @author  Kai Zhuang
 * @version 1.0
 * @since   2022-11-19
 */

class SpeechTests {
    LocalDateTime dt = LocalDateTime.now();
    Speech speech = new Speech(dt, "Filler", "Filler", "Filler", "Test message.");

    @Test
    void testGetTimeStamp() {
        LocalDateTime ts = speech.getTimeStamp();
        assertEquals(dt, ts);
    }

    @Test
    void testGetMessage() {
        String msg = speech.getMessage();
        assertEquals("Test message.", msg);
    }

}