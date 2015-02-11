package com.example.ianjavier.project1.presentation.presenters;

import com.example.ianjavier.project1.domain.interactors.ServerToClientsInteractor;
import com.example.ianjavier.project1.domain.interactors.ServerToClientsInteractorImpl;
import com.example.ianjavier.project1.domain.utils.NetworkHelper;
import com.example.ianjavier.project1.presentation.views.BaseView;

public class ServerPresenterImpl extends BasePresenterImpl implements ServerPresenter {
    private ServerToClientsInteractor mServerToClientsInteractor;

    public ServerPresenterImpl(BaseView view) {
        super(view);
        mServerToClientsInteractor = new ServerToClientsInteractorImpl();
    }

    @Override
    public void onStart(String name, int port) {
        super.mView.setActionBarTitle(NetworkHelper.getIPv4Address());
        mServerToClientsInteractor.startServer(port, super.mView.getFragment());
    }

    @Override
    public void onDisconnectDialogPositiveClicked() {
        mServerToClientsInteractor.stopServer();
        super.onDisconnectDialogPositiveClicked();
    }

    @Override
    public void onServerErrorDialogPositiveClicked() {
        mServerToClientsInteractor.stopServer();
        super.onServerErrorDialogPositiveClicked();
    }
}
