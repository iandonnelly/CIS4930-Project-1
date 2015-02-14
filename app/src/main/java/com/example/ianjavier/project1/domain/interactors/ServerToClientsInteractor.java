package com.example.ianjavier.project1.domain.interactors;

import com.example.ianjavier.project1.domain.ThreadedServer;

public interface ServerToClientsInteractor {
    public void startServer(final int port, final ThreadedServer.ServerListener serverListener);
    public void stopServer(ThreadedServer.ServerListener serverListener);
    public boolean isServerStarted();
}
