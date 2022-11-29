package com.speakfluid.backend.entities.message;

import java.util.ArrayList;
import java.util.HashMap;

public class Conversation {

    private HashMap<String, ArrayList<Dialogue<WozMessage>>> map;

    public Conversation() {
        map = new HashMap<String, ArrayList<Dialogue<WozMessage>>>();
    }

    public void put(String key, DialogueList dialogueList) {
        map.put(key, dialogueList.getList());
    }

    public HashMap<String, ArrayList<Dialogue<WozMessage>>> getMap() {
        return map;
    }
}
