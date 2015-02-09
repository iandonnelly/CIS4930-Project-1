package com.example.ianjavier.project1.presentation.presenters;

public interface ClientPresenter extends BasePresenter {
    public void onStart(String address, int port, String nickname);
    public void onSendMessageClicked(String message);
}
