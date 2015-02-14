package com.example.ianjavier.project1.presentation.presenters;

import com.example.ianjavier.project1.R;
import com.example.ianjavier.project1.domain.Client;
import com.example.ianjavier.project1.domain.interactors.ClientToServerInteractor;
import com.example.ianjavier.project1.domain.interactors.ClientToServerInteractorImpl;
import com.example.ianjavier.project1.presentation.model.Channel;
import com.example.ianjavier.project1.presentation.model.ClientModel;
import com.example.ianjavier.project1.presentation.model.Message;
import com.example.ianjavier.project1.presentation.views.BaseView;
import com.example.ianjavier.project1.presentation.views.ClientView;
import com.example.ianjavier.project1.presentation.views.fragments.BaseFragment;

public class ClientPresenterImpl extends BasePresenterImpl implements
        ClientPresenter,
        Client.ClientListener {
    private ClientToServerInteractor mClientToServerInteractor;
    private ClientModel mClientModel;

    public ClientPresenterImpl(BaseView view) {
        super(view);
        mClientToServerInteractor = new ClientToServerInteractorImpl();
        mClientModel = new ClientModel();
    }

    @Override
    public void onStart(String address, int port, String nickname) {
        if (!mClientToServerInteractor.isConnectionAlive()) {
            // Update client model
            mClientModel.setAddress(address);
            mClientModel.setNickname(nickname);
            mClientModel.setPort(port);

            // Update view
            mView.setActionBarTitle(nickname);
            mClientModel.addStatusMessage("Attempting to establish a connection to " + address);

            // Connect to the server
            mClientToServerInteractor.connectToServer(address, port, nickname, this);
        }
    }

    @Override
    public boolean onOptionsItemSelected(int id) {
        if (id == R.id.action_join_channel) {
            ((ClientView)mView).showJoinChannelDialog();
            return true;
        } else if (id == R.id.action_leave_channel) {
            mClientToServerInteractor.leaveChannel(mClientModel.getChannel(
                    mView.getCurrentTabPosition() - 1).getName());
            mView.removeTab(mView.getCurrentTabPosition()- 1);
            mClientModel.removeChannel(mView.getCurrentTabPosition() - 1);
            return true;
        }
        return super.onOptionsItemSelected(id);
    }

    @Override
    public boolean onCreateOptionsMenu() {
        ((ClientView) mView).hideLeaveChannelAction();
        return true;
    }

    @Override
    public void onPauseFragment(int position) {

    }

    @Override
    public void onResumeFragment(int position) {

    }

    @Override
    public void onDisconnectDialogPositiveClicked() {
        mClientModel.addStatusMessage("Connection terminated");
        mClientToServerInteractor.disconnectFromServer();
    }

    @Override
    public void onExitDialogPositiveClicked() {
        mClientModel.addStatusMessage("Connection terminated");
        mClientToServerInteractor.disconnectFromServer();
    }

    @Override
    public void onTabChanged(int position, BaseFragment tab) {
        // Remove previous view observers
        mClientModel.deleteObservers();

        if (position == 0) {
            // Set the status message log and add the view as an observer to the status message log
            tab.setMessageLog(mClientModel.getStatusMessageLog());
            mClientModel.addObserver(tab);
            ((ClientView) mView).hideLeaveChannelAction();
        } else {
            // Set the message log and add the view as an observer to the message log
            tab.setMessageLog(mClientModel.getChannel(position - 1).getMessageLog());
            mClientModel.getChannel(position - 1).addObserver(tab);
            ((ClientView) mView).showLeaveChannelAction();
        }
    }

    @Override
    public void onSendMessageClicked(String message) {
        mClientToServerInteractor.sendMessage(message,
                mClientModel.getChannel(mView.getCurrentTabPosition() - 1).getName());
    }

    @Override
    public void onJoinChannelDialogPositiveClicked(String channel) {
        for (int i = 0; i < mClientModel.getChannelList().size(); i++) {
            if (mClientModel.getChannel(i).getName().equals(channel)) {
                ((ClientView) mView).setCurrentTab(i + 1);
                return;
            }
        }

        mClientModel.addStatusMessage("Joining channel " + channel);
        mClientModel.addChannel(new Channel(channel));
        mClientToServerInteractor.joinChannel(channel);
        mView.addTab(mClientModel.getLastChannel().getName());
    }

    @Override
    public void onConnectToServerSuccess() {
        mClientModel.addStatusMessage("Connection established");
    }

    @Override
    public void onConnectToServerFailure() {
        mClientModel.addStatusMessage("Failed to establish connection");
        mView.showServerErrorDialog();
    }

    @Override
    public void onDisconnectedFromServer() {
        mView.navigateToHome();
    }

    @Override
    public void onServerClosed() {
        mView.navigateToHome();
    }

    @Override
    public void onServerError() {
        mView.showServerErrorDialog();
    }

    @Override
    public void onMessageReceived(String message, String channel, Message.MessageType messageType) {
        if (channel == null && messageType == null) {
            mClientModel.addStatusMessage(message);
        } else {
            mClientModel.getChannel(channel).addMessage(new Message(message, messageType));
        }
    }
}
