package com.example.ianjavier.project1.domain;


import java.util.Observer;

public interface ServerToClientsInteractor {
    public boolean startServer(String name, int port);
    public void stopServer();
    public void onMessageReceived(String message);
    public void addObserver(Observer observer);
    public void deleteObserver(Observer observer);
}
