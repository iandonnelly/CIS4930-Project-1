package com.example.ianjavier.project1.domain;


import java.util.Observer;

public interface ClientToServerInteractor {
    public boolean connectToServer(String address, int port, String nickname);
    public void disconnectFromServer();
    public void sendMessage(String message);
    public void onMessageReceived(String message);
    public void addObserver(Observer observer);
    public void deleteObserver(Observer observer);
}
