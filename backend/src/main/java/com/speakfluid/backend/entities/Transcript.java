package com.speakfluid.backend.entities;

import java.io.File;

/**
 * Transcript defines the entity for all transcript data
 *
 * @author  Edward Han
 * @version 1.0
 * @since   2022-11-16
 */

public interface Transcript {

    String getName();

    String getType();

    File getFile();
}