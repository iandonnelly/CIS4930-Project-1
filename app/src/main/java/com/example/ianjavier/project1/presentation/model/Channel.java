package com.example.ianjavier.project1.presentation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class Channel extends Observable {
    private String mName;
    private List<Message> mMessageLog;

    public Channel(String name) {
        mName = name;
        mMessageLog = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public void addMessage(Message message) {
        mMessageLog.add(message);
    }

    public List<Message> getMessageLog() {
        return mMessageLog;
    }
}
