package com.example.ianjavier.project1.domain.interactors;


import com.example.ianjavier.project1.domain.OnMessageReceivedListener;

public interface ServerToClientsInteractor {
    public void startServer(final int port, final OnMessageReceivedListener listener);
    public void stopServer();
}
