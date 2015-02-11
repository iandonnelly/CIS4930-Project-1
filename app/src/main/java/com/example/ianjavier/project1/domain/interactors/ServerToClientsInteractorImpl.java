package com.example.ianjavier.project1.domain.interactors;


import com.example.ianjavier.project1.domain.OnMessageReceivedListener;
import com.example.ianjavier.project1.domain.ThreadedServer;

public class ServerToClientsInteractorImpl implements ServerToClientsInteractor {
    @Override
    public void startServer(final int port, final OnMessageReceivedListener listener) {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadedServer.startServer(port, listener);
            }
        });
        thread.start();
    }

    @Override
    public void stopServer() {
        ThreadedServer.stopServer();
    }
}
