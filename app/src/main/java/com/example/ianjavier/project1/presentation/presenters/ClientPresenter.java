package com.example.ianjavier.project1.presentation.presenters;

public interface ClientPresenter {
    public void onStart(String address, int port, String nickname);
    public void onSendMessageClicked(String message);
    public void onJoinChannelDialogPositiveClicked(String channel);
}
