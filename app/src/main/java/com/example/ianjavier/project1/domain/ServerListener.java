package com.example.ianjavier.project1.domain;

public interface ServerListener {
    public void onServerStartedSuccess();
    public void onServerStartedFailure();
    public void onServerStopped();
}
