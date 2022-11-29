package com.speakfluid.backend.usecases;

import com.speakfluid.backend.entities.message.Transcript;

import java.util.ArrayList;

/**
 * WozTranscriptAnalysisInputBoundary is the interface for the use case interactor.
 *
 * @author  Zoey Zhang
 * @version 1.0
 * @since   2022-11-17
 */

// use case layer
public interface WozTranscriptAnalysisInputBoundary {
    public ArrayList<Transcript> analyzeTranscript(
            ArrayList<Transcript> transcript) ;

    }
