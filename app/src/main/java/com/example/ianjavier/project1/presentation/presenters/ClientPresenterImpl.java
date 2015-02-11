package com.example.ianjavier.project1.presentation.presenters;

import com.example.ianjavier.project1.domain.interactors.ClientToServerInteractor;
import com.example.ianjavier.project1.domain.interactors.ClientToServerInteractorImpl;
import com.example.ianjavier.project1.presentation.views.BaseView;

public class ClientPresenterImpl extends BasePresenterImpl implements
        ClientPresenter {
    private ClientToServerInteractor mClientToServerInteractor;
    private String mNickname;

    public ClientPresenterImpl(BaseView view) {
        super(view);
        mClientToServerInteractor = new ClientToServerInteractorImpl();
    }

    @Override
    public void onStart(String address, int port, String nickname) {
        mNickname = nickname;
        super.mView.setActionBarTitle(address);
        mClientToServerInteractor.connectToServer(address, port, mNickname, super.mView.getFragment());
    }

    @Override
    public void onDisconnectDialogPositiveClicked() {
        mClientToServerInteractor.disconnectFromServer(mNickname);
        super.onDisconnectDialogPositiveClicked();
    }

    @Override
    public void onServerErrorDialogPositiveClicked() {
        mClientToServerInteractor.disconnectFromServer(mNickname);
        super.onServerErrorDialogPositiveClicked();
    }

    @Override
    public void onSendMessageClicked(String message) {
        mClientToServerInteractor.sendMessage(message);
    }
}
