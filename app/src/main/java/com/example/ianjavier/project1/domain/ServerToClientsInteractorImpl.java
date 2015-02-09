package com.example.ianjavier.project1.domain;


import java.util.Observable;
import java.util.Observer;

public class ServerToClientsInteractorImpl extends Observable implements ServerToClientsInteractor {
    // private Server mServer;

    @Override
    public boolean startServer(String name, int port) {
        // start the server
        return true;
    }

    @Override
    public void stopServer() {
        // stop the server
    }

    @Override
    public void onMessageReceived(String message) {
        // Register as listener to server
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
