package com.example.ianjavier.project1.domain;

import android.util.Log;

import com.example.ianjavier.project1.presentation.model.Message;
import com.google.common.collect.HashMultimap;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;

public class ThreadedServer {
    public interface ServerListener {
        public void onServerStarted();
        public void onServerStopped();
        public void onServerError();
        public void onMessageReceived(String message, String channel, Message.MessageType messageType);
        public void newChannel(String channel);
        public void deleteChannel(String channel);
    }

    private static ServerSocket serverSocket = null;
    private static HashMultimap<String, PrintWriter> channels = HashMultimap.create();

    public static void startServer(int port, ServerListener listener){
        try{
            serverSocket = new ServerSocket(port);
            listener.onServerStarted();
        } catch (IOException ioEx){
        }

        try{
            while (!Thread.currentThread().isInterrupted()) {
                new Server(serverSocket.accept(), listener).start();
            }
        } catch(IOException ioEx){
        }
    }

    public static void stopServer(ServerListener listener) {
        try{
            if(serverSocket != null){
                serverSocket.close();
            }

            for (PrintWriter printWriter : channels.get("all")) {
                printWriter.println("CLOSE");
                printWriter.close();
            }
        } catch (IOException ioEx){
            listener.onServerError();
        }
        serverSocket = null;
        listener.onServerStopped();
    }

    private static class Server extends Thread {
        private Socket socket;
        private BufferedReader readerBuffer;
        private PrintWriter printWriter;
        private boolean disconnect;

        private ServerListener serverListener;

        public Server(Socket socket, ServerListener serverListener) {
            this.socket = socket;
            this.serverListener = serverListener;
        }

        public void run(){
            try{
                readerBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printWriter = new PrintWriter(socket.getOutputStream(), true);

                synchronized (channels) {
                    channels.put("all", printWriter);
                }
                disconnect = false;

                while(!disconnect){
                    String input = readerBuffer.readLine();

                    if(input == null){
                        return;
                    }


                    Log.i("Server", input);

                    //If disconnect message
                    if (input.startsWith("DISCONNECT")) {
                        disconnect = true;
                    }
                    //If JOIN message
                    else if (input.startsWith("JOIN")){
                        String channel = input.substring(4, input.indexOf(" "));
                        String status = input.substring(input.indexOf(" ") + 1, input.length());

                        synchronized (channels){
                            if (!channels.containsKey(channel)) {
                                serverListener.newChannel(channel);
                            }
                        }

                        serverListener.onMessageReceived(input.substring(input.indexOf(" ") + 1),
                                channel, Message.MessageType.STATUS);

                        synchronized (channels){
                            channels.put(channel, printWriter);
                        };

                        synchronized (channels) {
                            for (PrintWriter printWriter : channels.get(channel)) {
                                printWriter.println("STATUS #" + channel + " " +  status);
                            }
                        }
                    }
                    // If LEAVE message
                    else if (input.startsWith("LEAVE")) {
                        String channel = input.substring(5, input.indexOf(" "));
                        String status = input.substring(input.indexOf(" ") + 1, input.length());

                        serverListener.onMessageReceived(input.substring(input.indexOf(" ") + 1),
                                channel, Message.MessageType.STATUS);

                        synchronized (channels) {
                            for (PrintWriter printWriter : channels.get(channel)) {
                                printWriter.println("STATUS #" + channel + " " + status);
                            }
                        }

                        synchronized (channels) {
                            if (channels.containsEntry(channel, printWriter)) {
                                channels.remove(channel, printWriter);
                            }
                        }

                        synchronized (channels){
                            if (!channels.containsKey(channel)) {
                                serverListener.deleteChannel(channel);
                            }
                        }
                    }
                    //If channel message
                    else if (input.startsWith("#")){
                        String channel = input.substring(1, input.indexOf(" "));

                        serverListener.onMessageReceived(input.substring(input.indexOf(" ") + 1),
                                channel, Message.MessageType.OTHER_USER);

                        synchronized (channels) {
                            for (PrintWriter printWriter : channels.get(channel)){
                                printWriter.println(input);
                            }
                        }
                    }
                    //If status message
                    else if (input.startsWith("STATUS")) {
                        String statusMessage = input.substring(input.indexOf(" ") + 1,input.length());

                        serverListener.onMessageReceived(input.substring(input.indexOf(" ") + 1),
                                null, null);

                        synchronized (channels) {
                            for (PrintWriter printWriter : channels.get("all")) {
                                printWriter.println(input);
                            }
                        }
                    }
                }
            } catch (IOException ioEx){
                Log.e("Server", ioEx.getLocalizedMessage(), ioEx);
            } finally {
                if (printWriter != null) {
                    ArrayList<String> channelsToRemove = new ArrayList<>();

                    synchronized (channels) {
                        Iterator<String> channelItr = channels.keySet().iterator();

                        while(channelItr.hasNext()){
                            String channel = channelItr.next();;
                            channels.get(channel);

                            if(channels.containsEntry(channel, printWriter)){
                                channelsToRemove.add(channel);
                            }
                        }

                        for(String channel : channelsToRemove) {
                            channels.remove(channel, printWriter);
                        }

                        channelsToRemove.clear();
                    }
                }

                try{
                    socket.close();
                } catch (IOException ioEx){
                    Log.e("Server", ioEx.getLocalizedMessage(), ioEx);
                }

                Thread.currentThread().interrupt();
            }
        }
    }
}
