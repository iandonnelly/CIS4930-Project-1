package com.example.ianjavier.project1.domain;


import java.util.Observable;
import java.util.Observer;

public class ClientToServerInteractorImpl extends Observable implements ClientToServerInteractor {
    // private Client client;

    @Override
    public boolean connectToServer(String address, int port, String nickname) {
        // Attempt to connect to the server
        return true;
    }

    @Override
    public void sendMessage(String message) {
        // Send a message through the client class
    }

    @Override
    public void onMessageReceived(String message) {
        notifyObservers(message);
    }

    @Override
    public void disconnectFromServer() {
        // disconnect from the server
    }

    @Override
    public void addObserver(Observer observer) {
        //addObserver(observer);
    }

    @Override
    public void deleteObserver(Observer observer) {
        //deleteObserver(observer);
    }
}
