package com.speakfluid.backend.entities;
import java.time.LocalDateTime;

/**
* The Speech object stores the specific information about
* every message/interaction in a dialogue, including:
 * time stamp: a LocalDateTime variable that marks the time stamp of the message;
 * message: a string contains the actual content of the message.
* @author  Zoey Zhang
* @version 2.0
* @since   2022-11-12
*/

/* Entity layer */
public class Speech {
    private LocalDateTime timeStamp;
    private String message;

    public Speech(LocalDateTime timeStamp,
                  String m){
        this.timeStamp = timeStamp;
        this.message = m;
    }

    public LocalDateTime getTimeStamp() {
        return this.timeStamp;
    }

    public String getMessage() {
        return this.message;
    }

}
