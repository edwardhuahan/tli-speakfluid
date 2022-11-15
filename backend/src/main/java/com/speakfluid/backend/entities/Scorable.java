package com.speakfluid.backend.entities;

/**
 * Scorable is an interface for calculating the confidence score
 * that reflects how much does each talk step fit for the dialogue.
 *
 * @author  Zoey Zhang
 * @version 1.0
 * @since   2022-11-12
 */
interface Scorable {
    double calculateConfidenceScore();


}