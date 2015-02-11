package com.example.ianjavier.project1.presentation.presenters;

public interface ServerPresenter {
    public void onStart(String name, int port, OnMessageReceivedListener listener);
}
