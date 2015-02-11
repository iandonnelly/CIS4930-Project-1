package com.example.ianjavier.project1.domain.interactors;


import com.example.ianjavier.project1.domain.OnMessageReceivedListener;

public interface ClientToServerInteractor {
    public void connectToServer(String address, int port, String nickname,
                                OnMessageReceivedListener listener);
    public void disconnectFromServer(String message);
    public void sendMessage(String message);
}
