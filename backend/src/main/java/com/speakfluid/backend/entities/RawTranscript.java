package com.speakfluid.backend.entities;

import java.io.File;

/**
 * RawTranscript defines how an unprocessed Transcript file should be stored
 *
 * @author  Edward Han
 * @version 1.0
 * @since   2022-11-16
 */
class RawTranscript implements Transcript {

    private final String name;
    private final String type;
    private final File file;

    RawTranscript(String name, String type, File file) {
        this.name = name;
        this.type = type;
        this.file = file;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public File getFile() {
        return file;
    }
}
