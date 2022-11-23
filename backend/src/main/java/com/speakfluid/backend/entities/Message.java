package com.speakfluid.backend.entities;

/**
 * The Message abstract class stores the content of the message.
 * This class is designed to be abstract, other Message classes specific to 
 * different datasets can inherit from Message
 * 
 * @author  Minh Le
 * @version 1.0
 * @since   2022-11-18
 */

/* Entity layer */
abstract class Message {

    public String message;

    public Message(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
    
}
