package com.example.ianjavier.project1.presentation.presenters;

import com.example.ianjavier.project1.domain.ThreadedServer;
import com.example.ianjavier.project1.domain.interactors.ServerToClientsInteractor;
import com.example.ianjavier.project1.domain.interactors.ServerToClientsInteractorImpl;
import com.example.ianjavier.project1.domain.utils.NetworkHelper;
import com.example.ianjavier.project1.presentation.model.Channel;
import com.example.ianjavier.project1.presentation.model.Message;
import com.example.ianjavier.project1.presentation.model.ServerModel;
import com.example.ianjavier.project1.presentation.views.BaseView;
import com.example.ianjavier.project1.presentation.views.ServerView;
import com.example.ianjavier.project1.presentation.views.fragments.BaseFragment;

public class ServerPresenterImpl extends BasePresenterImpl implements ServerPresenter,
        ThreadedServer.ServerListener {
    private ServerToClientsInteractor mServerToClientsInteractor;
    private ServerModel mServerModel;

    public ServerPresenterImpl(BaseView view) {
       super(view);
        mServerToClientsInteractor = new ServerToClientsInteractorImpl();
        mServerModel = new ServerModel();
    }

    @Override
    public void onStart(String name, int port) {
        if (!mServerToClientsInteractor.isServerStarted()) {
            // Update the server model
            mServerModel.setName(name);
            mServerModel.setPort(port);

            // Update the view
            mView.setActionBarTitle(name);
            mView.setActionBarSubtitle(NetworkHelper.getIPv4Address() + ":" + port);

            mServerModel.addStatusMessage("Starting server");

            // Start the server
            mServerToClientsInteractor.startServer(port, this);
        }
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
    public void onDisconnectDialogPositiveClicked() {
        mServerModel.addStatusMessage("Stopping server");
        mServerToClientsInteractor.stopServer(this);
    }

    @Override
    public void onExitDialogPositiveClicked() {
        mServerModel.addStatusMessage("Stopping server");
        mServerToClientsInteractor.stopServer(this);
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
    public void onServerStarted() {
        mServerModel.addStatusMessage("Server started");
    }

   @Override
    public void onServerStopped() {
       mServerModel.addStatusMessage("Server stopped");
       mView.navigateToHome();
    }

    @Override
    public void onServerError() {
        mView.showServerErrorDialog();
    }

    @Override
    public void onMessageReceived(String message, String channel, Message.MessageType messageType) {
        if (channel == null && messageType == null) {
            mServerModel.addStatusMessage(message);
        } else {
            mServerModel.getChannel(channel).addMessage(new Message(message, messageType));
        }
    }

    @Override
    public void newChannel(String channel) {
        mServerModel.addChannel(new Channel(channel));
        mView.addTab(mServerModel.getLastChannel().getName());
    }

    @Override
    public void deleteChannel(String channel) {
        mServerModel.getPosition(channel);
        mView.removeTab(mServerModel.getPosition(channel) + 1);
        mServerModel.removeChannel(channel);
    }

    @Override
    public void onPauseFragment(int position) {
        if (position == 0) {
            mServerModel.deleteObservers();
        } else {
            if ((mServerModel.getChannelList().size() - 1) >= (position - 1)) {
                mServerModel.getChannel(position - 1).deleteObservers();
            }
        }
    }

    @Override
    public void onResumeFragment(int position) {
        if (position == 0) {
            if (mServerModel.countObservers() < 1) {
                mServerModel.addObserver(mView.getTab(position));
                mView.getTab(position).setMessageLog(mServerModel.getStatusMessageLog());
            }
        } else {
            if (mServerModel.getChannel(position - 1).countObservers() < 1) {
                mServerModel.addObserver(mView.getTab(position));
                mView.getTab(position).setMessageLog(mServerModel.getChannel(position - 1).getMessageLog());
            }
        }
    }
}
