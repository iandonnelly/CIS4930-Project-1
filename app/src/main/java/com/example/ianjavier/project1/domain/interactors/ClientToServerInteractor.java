package com.example.ianjavier.project1.domain.interactors;


import com.example.ianjavier.project1.domain.ClientListener;

public interface ClientToServerInteractor {
    public void connectToServer(String address, int port, String nickname, ClientListener listener);
    public void disconnectFromServer(ClientListener listener);
    public void sendMessage(String message, String channel);
    public void joinChannel(String channel);
    public void leaveChannel(String channel);
    public boolean isConnectionAlive();
}
