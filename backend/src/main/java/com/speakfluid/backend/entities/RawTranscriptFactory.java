package com.speakfluid.backend.entities;

import java.io.File;

/**
 *
 *
 * @author  Edward Han
 * @version 1.0
 * @since   2022-11-16
 */

public class RawTranscriptFactory implements TranscriptFactory {

    @Override
    public Transcript create(String name, String type, File file) {
        return new RawTranscript(name, type, file);
    }
}
