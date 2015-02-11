package com.example.ianjavier.project1.domain;


import java.util.Observable;

public class ServerToClientsInteractorImpl implements ServerToClientsInteractor {
    @Override
    public boolean startServer(int port, OnMessageReceivedListener listener) {
        // ThreadedServer.startServer(port, listener);
        return true;
    }

    @Override
    public void stopServer() {
        // ThreadedServer.stopServer();
    }
}
