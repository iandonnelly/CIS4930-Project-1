package com.example.ianjavier.project1.presentation.presenters;

import com.example.ianjavier.project1.domain.ServerListener;
import com.example.ianjavier.project1.domain.interactors.ServerToClientsInteractor;
import com.example.ianjavier.project1.domain.utils.NetworkHelper;
import com.example.ianjavier.project1.presentation.model.ServerModel;
import com.example.ianjavier.project1.presentation.views.BaseView;
import com.example.ianjavier.project1.presentation.views.ServerView;
import com.example.ianjavier.project1.presentation.views.fragments.BaseFragment;

public class ServerPresenterImpl extends BasePresenterImpl implements ServerPresenter,
        ServerListener {
    private ServerToClientsInteractor mServerToClientsInteractor;
    private ServerModel mServerModel;

    public ServerPresenterImpl(BaseView view) {
       super(view);
        //mServerToClientsInteractor = new ServerToClientsInteractorImpl();
        mServerModel = new ServerModel();
    }

    @Override
    public void onStart(String name, int port) {
        //if (!mServerToClientsInteractor.isServerStarted()) {
            // Update the server model
            mServerModel.setName(name);
            mServerModel.setPort(port);

            // Update the view
            mView.setActionBarTitle(name);
            mView.setActionBarSubtitle(NetworkHelper.getIPv4Address() + ":" + port);
            mServerModel.addStatusMessage("Starting server");

            // Start the server
            //mServerToClientsInteractor.startServer(port);
        //}
    }

    @Override
    public boolean onOptionsItemSelected(int id) {
        return super.onOptionsItemSelected(id);
    }

    @Override
    public boolean onCreateOptionsMenu() {
        ((ServerView) mView).hideJoinChannelAction();
        ((ServerView) mView).hideLeaveChannelAction();
        return true;
    }

    @Override
    public void onCreateTabView() {
        BaseFragment currentTab = mView.getCurrentTab();
        int currentTabPosition = mView.getCurrentTabPosition();

        if (currentTabPosition == 0) {
            // Set the status message log and add the view as an observer to the status message log
            currentTab.setMessageLog(mServerModel.getStatusMessageLog());
            mServerModel.addObserver(currentTab);
        } else {
            // Set the message log and add the view as an observer to the message log
            currentTab.setMessageLog(mServerModel.getChannel(currentTabPosition - 1).getMessageLog());
            mServerModel.getChannel(currentTabPosition - 1).addObserver(currentTab);
        }
    }

    // @Override
    public void onDisconnectDialogPositiveClicked() {
        //super.mView.getFragment().onMessageReceived("Stopping server",
        //        OnMessageReceivedListener.MessageType.STATUS);
        //mServerToClientsInteractor.stopServer(this);
    }

    @Override
    public void onExitDialogPositiveClicked() {
        mServerModel.addStatusMessage("Stopping server");
        //mServerInteractor.disconnectFromServer(this);
    }

    @Override
    public void onTabChanged(int position, BaseFragment tab) {
        // Remove previous view observers
        mServerModel.deleteObservers();

        if (position == 0) {
            // Set the status message log and add the view as an observer to the status message log
            tab.setMessageLog(mServerModel.getStatusMessageLog());
            mServerModel.addObserver(tab);
        } else {
            // Set the message log and add the view as an observer to the message log
            tab.setMessageLog(mServerModel.getChannel(position - 1).getMessageLog());
            mServerModel.getChannel(position - 1).addObserver(tab);
        }
    }

    @Override
    public void onServerStartedSuccess() {
        mServerModel.addStatusMessage("Server started");
    }

    @Override
    public void onServerStartedFailure() {
        mView.showServerErrorDialog();
    }

   @Override
    public void onServerStopped() {
       mServerModel.addStatusMessage("Server stopped");
       mView.navigateToHome();
    }
}
