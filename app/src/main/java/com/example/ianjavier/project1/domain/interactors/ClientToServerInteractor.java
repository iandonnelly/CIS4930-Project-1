package com.example.ianjavier.project1.domain.interactors;

import com.example.ianjavier.project1.domain.Client;

public interface ClientToServerInteractor {
    public void connectToServer(String address, int port, String nickname, Client.ClientListener listener);
    public void disconnectFromServer();
    public void sendMessage(String message, String channel);
    public void requestUserList();
    public void joinChannel(String channel);
    public void leaveChannel(String channel);
    public boolean isConnectionAlive();
}
