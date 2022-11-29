package com.speakfluid.backend.usecases;

import com.speakfluid.backend.entities.message.Conversation;
import com.speakfluid.backend.entities.message.Dialogue;
import com.speakfluid.backend.entities.message.WozMessage;

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
    public ArrayList<Conversation> analyzeTranscript(
            ArrayList<Conversation> transcript) ;

    }
