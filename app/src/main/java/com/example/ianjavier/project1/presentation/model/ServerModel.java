package com.example.ianjavier.project1.presentation.model;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class ServerModel extends Observable {
    private String mName;
    private int mPort;
    private List<Message> mStatusMessageLog;
    private LinkedList<Channel> mChannelList;

    public ServerModel() {
        mStatusMessageLog = new ArrayList<>();
        mChannelList = new LinkedList<>();
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
        Iterator<Channel> channelIterator = mChannelList.iterator();

        while (channelIterator.hasNext()) {
            Channel c = channelIterator.next();

            if (c.getName().equals(channel)) {
                channelIterator.remove();
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

    public int getPosition(String channel) {
        for (int i = 0; i < mChannelList.size(); i++) {
            if (mChannelList.get(i).getName().equals(channel)) {
                return i;
            }
        }
        return 0;
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
