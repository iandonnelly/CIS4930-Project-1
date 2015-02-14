package com.example.ianjavier.project1.domain.interactors;


import com.example.ianjavier.project1.domain.ServerListener;

public interface ServerToClientsInteractor {
    public void startServer(final int port, final ServerListener serverListener);
    public void stopServer(ServerListener serverListener);
    public boolean isServerStarted();
}
