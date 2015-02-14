package com.example.ianjavier.project1.domain.interactors;


import com.example.ianjavier.project1.domain.ThreadedServer;

public class ServerToClientsInteractorImpl implements ServerToClientsInteractor {
    private Thread mThread = null;

    public void startServer(final int port, final ThreadedServer.ServerListener serverListener) {
        mThread = new Thread(new Runnable() {
            @Override
            public void run() {
                ThreadedServer.startServer(port, serverListener);
             }
        });
        mThread.start();
    }

    public void stopServer(ThreadedServer.ServerListener serverListener) {
        ThreadedServer.stopServer(serverListener);
        mThread.interrupt();
    }

    public boolean isServerStarted() {
        return (mThread != null && !mThread.isAlive());
    }
}
