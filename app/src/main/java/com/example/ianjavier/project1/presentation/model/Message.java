package com.example.ianjavier.project1.presentation.model;


public class Message {
    public enum MessageType {
        STATUS,
        CURRENT_USER,
        OTHER_USER
    }

    private String mMessage;
    private MessageType mMessageType;

    public Message(String message, MessageType messageType) {
        mMessage = message;
        mMessageType = messageType;
    }

    public String getMessage() {
        return mMessage;
    }

    public MessageType getMessageType() {
        return mMessageType;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public void setMessageType(MessageType messageType) {
        mMessageType = messageType;
    }
}
