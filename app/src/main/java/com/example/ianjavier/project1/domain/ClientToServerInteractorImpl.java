package com.example.ianjavier.project1.domain;


public class ClientToServerInteractorImpl implements ClientToServerInteractor {

    @Override
    public void connectToServer(String address, int port, String nickname,
                                OnMessageReceivedListener listener) {
        // Thread thread = new Thread(Client.getRunnable(address, port, nickname, listener));
        // thread.start();
    }

    @Override
    public void sendMessage(String message) {
        // Client.sendMessage(message);
    }

    @Override
    public void disconnectFromServer() {
        // Client.disconnectFromServer();
    }
}
