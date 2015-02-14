package com.example.ianjavier.project1.presentation.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class ClientModel extends Observable {
    private String mAddress;
    private int mPort;
    private String mNickname;
    private List<Message> mStatusMessageLog;
    private List<Channel> mChannelList;

    public ClientModel() {
        mStatusMessageLog = new ArrayList<>();
        mChannelList = new ArrayList<>();
    }

    public String getAddress() {
        return mAddress;
    }

    public int getPort() {
        return mPort;
    }

    public String getNickname() {
        return mNickname;
    }

    public void setAddress(String address) {
        mAddress = address;
    }

    public void setPort(int port) {
        mPort = port;
    }

    public void setNickname(String nickname) {
        mNickname = nickname;
    }

    public void addStatusMessage(String message) {
        mStatusMessageLog.add(new Message(message, Message.MessageType.STATUS));
        notifyObservers();
    }

    public void addChannel(Channel channel) {
        mChannelList.add(channel);
    }

    public void removeChannel(int position) {
        mChannelList.remove(position);
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
