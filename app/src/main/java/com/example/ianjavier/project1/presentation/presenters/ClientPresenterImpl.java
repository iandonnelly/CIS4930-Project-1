package com.example.ianjavier.project1.presentation.presenters;

import com.example.ianjavier.project1.domain.ClientToServerInteractor;
import com.example.ianjavier.project1.domain.ClientToServerInteractorImpl;
import com.example.ianjavier.project1.presentation.views.BaseView;

public class ClientPresenterImpl extends BasePresenterImpl implements
        ClientPresenter {
    private ClientToServerInteractor mClientToServerInteractor;

    public ClientPresenterImpl(BaseView view) {
        super(view);
        mClientToServerInteractor = new ClientToServerInteractorImpl();
    }

    @Override
    public void onStart(String address, int port, String nickname) {
        if (mClientToServerInteractor.connectToServer(address, port, nickname)) {
            mClientToServerInteractor.addObserver(super.mView.getFragment());
        } else {
            // Error connecting to server
        }
    }

    @Override
    public void onDisconnectDialogPositiveClicked() {
        mClientToServerInteractor.disconnectFromServer();
        super.onDisconnectDialogPositiveClicked();
    }

    @Override
    public void onServerErrorDialogPositiveClicked() {
        mClientToServerInteractor.disconnectFromServer();
        super.onServerErrorDialogPositiveClicked();
    }

    @Override
    public void onSendMessageClicked(String message) {
        mClientToServerInteractor.sendMessage(message);
    }
}
