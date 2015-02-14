package com.example.ianjavier.project1.domain;

import com.example.ianjavier.project1.presentation.model.Message;

public interface ClientListener {
    public void onConnectToServerSuccess();
    public void onConnectToServerFailure();
    public void onDisconnectedFromServer();
    public void onJoinChannelSuccess();
    public void onJoinChannelFailure();
    public void onServerClosed();
    public void onMessageReceived(String message, String channel, Message.MessageType messageType);
    public void onServerError();
}
