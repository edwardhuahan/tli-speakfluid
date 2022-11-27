package com.speakfluid.backend.entities;
/**
 * ScoreStandards stores all the scaling used for analysis.
 *
 *  @author  Zoey Zhang
 *  @version 1.0
 *  @since   2022-11-26
 */
public class ScoreStandards {
    public static double highMatch = 5.0;
    public static double mediumMatch = 3.0;
    public static double lowMatch = 1.0;
    // a standard step class is considered as a class with only one set of keywords.
    public static double standardStepClass = 15.0;
    // Additional Analysis includes a second set of keywords and or additional analysis method.
    // For example, TextStep has two sets of keywords and one additional method.
    // Its maxScore is standdardStepClass + 2 * additionalAnalysis.
    public static double additionalAnalysis = 3.0;

}
