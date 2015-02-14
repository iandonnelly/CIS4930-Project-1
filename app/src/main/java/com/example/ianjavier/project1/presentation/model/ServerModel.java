package com.example.ianjavier.project1.presentation.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ServerModel extends Observable {
    private String mName;
    private int mPort;
    private List<Message> mStatusMessageLog;
    private List<Channel> mChannelList;

    public ServerModel() {
        mStatusMessageLog = new ArrayList<>();
        mChannelList = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public int getPort() {
        return mPort;
    }

    public synchronized void setName(String name) {
        mName = name;
    }

    public synchronized void setPort(int port) {
        mPort = port;
    }

    public synchronized void addStatusMessage(String message) {
        mStatusMessageLog.add(new Message(message, Message.MessageType.STATUS));
        setChanged();
        notifyObservers();
    }

    public synchronized void addChannel(Channel channel) {
        mChannelList.add(channel);
    }

    public synchronized void removeChannel(String channel) {
        for (Channel c : mChannelList) {
            if (c.getName().equals(channel)) {
                mChannelList.remove(c);
            }
        }
    }

    public Channel getChannel(String channel) {
        for (Channel c : mChannelList) {
            if (c.getName().equals(channel)) {
                return c;
            }
        }
        return null;
    }

    public Channel getChannel(int position) {
        return mChannelList.get(position);
    }

    public List<Channel> getChannelList() {
        return mChannelList;
    }

    public Channel getLastChannel() {
        return mChannelList.get(mChannelList.size() - 1);
    }

    public List<Message> getStatusMessageLog() {
        return mStatusMessageLog;
    }

    @Override
    public void deleteObservers() {
        super.deleteObservers();

        for (Channel channel : mChannelList) {
            channel.deleteObservers();
        }
    }
}
