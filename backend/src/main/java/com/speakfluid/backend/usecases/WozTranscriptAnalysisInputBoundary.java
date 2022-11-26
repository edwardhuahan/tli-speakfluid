package com.speakfluid.backend.usecases;

import com.speakfluid.backend.entities.Dialogue;
import com.speakfluid.backend.entities.WozMessage;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * WozTranscriptAnalysisInputBoundary is the interface for the use case interactor.
 *
 * @author  Zoey Zhang
 * @version 1.0
 * @since   2022-11-17
 */

// use case layer
public interface WozTranscriptAnalysisInputBoundary {
    public ArrayList<HashMap<String, ArrayList<Dialogue<WozMessage>>>> analyzeTranscript(
            ArrayList<HashMap<String, ArrayList<Dialogue<WozMessage>>>> transcript) ;

    }
