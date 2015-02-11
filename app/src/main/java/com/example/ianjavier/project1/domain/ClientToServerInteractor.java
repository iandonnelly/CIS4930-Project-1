package com.example.ianjavier.project1.domain;


public interface ClientToServerInteractor {
    public void connectToServer(String address, int port, String nickname,
                                Client.OnMessageReceivedListener listener);
    public void disconnectFromServer();
    public void sendMessage(String message);
}
