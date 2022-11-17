package com.speakfluid.backend.entities;

import java.io.File;

/**
 * TranscriptFactory defines a way of creating new transcript objects
 *
 * @author  Edward Han
 * @version 1.0
 * @since   2022-11-16
 */
public interface TranscriptFactory {

    Transcript create(String name, String type, File file);
}
