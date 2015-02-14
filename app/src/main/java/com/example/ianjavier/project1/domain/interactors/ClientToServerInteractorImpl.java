package com.example.ianjavier.project1.domain.interactors;


import com.example.ianjavier.project1.domain.Client;

public class ClientToServerInteractorImpl implements ClientToServerInteractor {
    private Thread mThread = null;

    @Override
    public void connectToServer(String address, int port, String nickname, Client.ClientListener listener) {
        mThread = new Thread(Client.clientRunnable(address, port, nickname, listener));
        mThread.start();
    }

    @Override
    public void disconnectFromServer() {
        mThread.interrupt();
        Client.disconnectFromServer();
    }

    @Override
    public void sendMessage(String message, String channel) {
        Client.sendMessage("#" + channel + " " + message);
    }

    @Override
    public void joinChannel(String channel) {
        Client.joinChannel(channel);
    }

    @Override
    public void leaveChannel(String channel) {
        Client.leaveChannel(channel);
    }

    @Override
    public boolean isConnectionAlive() {
        return (mThread != null && mThread.isAlive());
    }
}
