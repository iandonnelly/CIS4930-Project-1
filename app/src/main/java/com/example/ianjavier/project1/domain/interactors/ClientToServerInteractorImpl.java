package com.example.ianjavier.project1.domain.interactors;


import com.example.ianjavier.project1.domain.Client;
import com.example.ianjavier.project1.domain.OnMessageReceivedListener;

public class ClientToServerInteractorImpl implements ClientToServerInteractor {

    @Override
    public void connectToServer(String address, int port, String nickname,
                                OnMessageReceivedListener listener) {
        Thread thread = new Thread(Client.clientRunnable(address, port, nickname, listener));
        thread.start();
    }

    @Override
    public void sendMessage(String message) {
        Client.sendMessage(message);
    }

    @Override
    public void disconnectFromServer(String nickname) {
        Client.disconnectFromServer(nickname);
    }
}
