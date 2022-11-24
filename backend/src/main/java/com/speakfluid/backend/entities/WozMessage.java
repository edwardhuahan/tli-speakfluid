package com.speakfluid.backend.entities;

/**
 * The WozMessage class is tailored to the MultiWoZ transcript dataset, in which
 * each message contains two variables:
 * trace_type: either "response" or "request", indicating if the message is from the chatbot or user, respectively
 * message: a string contains the actual content of the message (inherited from Message)
 * @author  Zoey Zhang, Minh Le
 * @version 3.0
 * @since   2022-11-18
 */

/* Entity layer */
public class WozMessage extends Message{

    private String trace_type;

    public WozMessage(String trace_type, String message){
        super(message);
        this.trace_type = trace_type;
    }

    public String getTraceType() {
        return this.trace_type;
    }

    public String getMessage() {
        return this.message;
    }

}
