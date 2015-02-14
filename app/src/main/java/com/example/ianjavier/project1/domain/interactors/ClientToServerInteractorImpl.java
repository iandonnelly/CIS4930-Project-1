package com.example.ianjavier.project1.domain.interactors;


import com.example.ianjavier.project1.domain.ClientListener;

public class ClientToServerInteractorImpl implements ClientToServerInteractor {
    private Thread mThread = null;

    @Override
    public void connectToServer(String address, int port, String nickname, ClientListener listener) {
        //mThread = new Thread(Client.clientRunnable(address, port, nickname, listener));
        //mThread.start();
    }

    @Override
    public void disconnectFromServer(ClientListener listener) {
        //mThread.interrupt();
        //Client.disconnectFromServer(listener);
    }

    @Override
    public void sendMessage(String message, String channel) {
        //Client.sendMessage("#" + channel + " " + message);
    }

    @Override
    public void joinChannel(String channel) {
        //Client.sendMessage("JOIN " + channel);
    }

    @Override
    public void leaveChannel(String channel) {
        //Client.sendMessage("LEAVE" + channel);
    }

    @Override
    public boolean isConnectionAlive() {
        //return (mThread != null && mThread.isAlive());
        return true;
    }

    public void onMessageReceived() {

    }
}
