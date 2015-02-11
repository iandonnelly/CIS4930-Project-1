package com.example.ianjavier.project1.domain;


public interface ServerToClientsInteractor {
    public boolean startServer(int port, OnMessageReceivedListener listener);
    public void stopServer();
}
