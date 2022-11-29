package com.speakfluid.backend.entities.message;

import java.util.ArrayList;

public class DialogueList {

    private ArrayList<Dialogue<WozMessage>> list;

    public DialogueList() {
        list = new ArrayList<>();
    }

    public void add(Dialogue<WozMessage> dialogue) {
        list.add(dialogue);
    }

    public ArrayList<Dialogue<WozMessage>> getList() {
        return list;
    }
}
