package com.example.ianjavier.project1.domain.interactors;


import com.example.ianjavier.project1.domain.ServerListener;

public class ServerToClientsInteractorImpl  {
    private Thread mThread = null;

    public void startServer(final int port, final ServerListener serverListener) {
        //mThread = new Thread(new Runnable() {
        //    @Override
        //    public void run() {
        //        ThreadedServer.startServer(port, listener, serverListener);
        //     }
        //});
        // mThread.start();
    }

    public void stopServer(ServerListener serverListener) {
        //ThreadedServer.stopServer(serverListener);
        //mThread.interrupt();
    }

    public boolean isServerStarted() {
        //return (mThread != null && !mThread.isAlive());
        return true;
    }
}
