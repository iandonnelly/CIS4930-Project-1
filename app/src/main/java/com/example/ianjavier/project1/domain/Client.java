package com.example.ianjavier.project1.domain;

import android.util.Log;

import com.example.ianjavier.project1.presentation.model.Message;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public interface ClientListener {
        public void onConnectToServerSuccess();
        public void onConnectToServerFailure();
        public void onDisconnectedFromServer();
        public void onServerClosed();
        public void onMessageReceived(String message, String channel, Message.MessageType messageType);
        public void onServerError();
    }

    static Socket clientSocket = null;
    static OutputStreamWriter output = null;
    static InputStreamReader input = null;
    static BufferedWriter writerBuffer = null;
    static BufferedReader readerBuffer = null;
    static PrintWriter printWriter = null;
    static String username;
    static ClientListener clientListener;

    //Takes ip address and port of server and attempts to open a socket
    public static void initConnection(String server, int port, String name, ClientListener listener){
        username = name;
        clientListener = listener;

        try{
            //Create Socket
            clientSocket = new Socket(InetAddress.getByName(server), port);

            //Initialize writers for sending messages to server
            output = new OutputStreamWriter(clientSocket.getOutputStream());
            writerBuffer = new BufferedWriter(output);
            printWriter = new PrintWriter(writerBuffer);

            //Initialize readers for reading messages from server
            input = new InputStreamReader(clientSocket.getInputStream());
            readerBuffer = new BufferedReader(input);
            clientListener.onConnectToServerSuccess();
        } catch (UnknownHostException unknownEx){
            clientListener.onConnectToServerFailure();
        } catch (ConnectException connectEx) {
            clientListener.onConnectToServerFailure();
        } catch (IOException ioEx){
            clientListener.onConnectToServerFailure();
        }
    }
    public static void disconnectFromServer(){
        try{
            printWriter.println("STATUS" + username + " has disconnected from the server.");
            printWriter.println("DISCONNECT");

            //Check for error
            if(printWriter.checkError()) {
                Log.d("Client", "An error occurred when sending message.");
            }

            if(clientSocket != null){
                clientSocket.shutdownInput();
                clientSocket.shutdownOutput();
                clientSocket.close();
            }
        } catch (IOException ioEx) {
            //clientListener.onServerError();;
        }
        //Make clientSocket null
        clientSocket = null;
        clientListener.onDisconnectedFromServer();
    }

    //Join channel
    public static void joinChannel(String channel){
        printWriter.println("JOIN" + channel + " " + username + " has joined the channel");

        //Check for error
        if(printWriter.checkError()) {
            Log.d("Client", "An error occurred when sending message.");
        }
    }

    // Leave channel
    public static void leaveChannel(String channel) {
        printWriter.println("LEAVE" + channel + " " + username + " has left the channel");

        //Check for error
        if(printWriter.checkError()) {
            Log.d("Client", "An error occurred when sending message.");
        }
    }

    //Send message
    public static void sendMessage(String message){
        if(message != null && !message.isEmpty()){
            //Message to channel
            if (message.startsWith("#")) {
                printWriter.println(message);
            }
            //General message
            else {
                printWriter.println(username + ": " + message);
            }

            //Check for error
            if(printWriter.checkError()) {
                Log.d("Client", "An error occurred when sending message.");
            }
        }
    }

    //Runnable to use from UI
    public static Runnable clientRunnable(final String server, final int port, final String name,
                                          final ClientListener listener){
        return new Runnable() {
            @Override
            public void run() {
                initConnection(server, port, name, listener);

                printWriter.println("STATUS " + name + " has connected to the server.");

                //Check for error
                if(printWriter.checkError()) {
                    Log.d("Client", "An error occurred when sending message.");
                }

                //Unable to connect to server
                if(clientSocket == null)
                    return;
                //Try some message stuff

                boolean closed = false;

                try {
                    String stringBuffer = null;
                    stringBuffer = readerBuffer.readLine();

                    while (stringBuffer != null && !closed) {
                        String message = stringBuffer;
                        //Check message type
                        if (message.startsWith("CLOSE")) {
                            closed = true;
                            clientListener.onServerClosed();
                        } else if (message.startsWith("STATUS")) {
                            String messageStatus = message.substring(message.indexOf(" "),
                                    message.length());

                            // If channel status
                            if (messageStatus.startsWith("#")) {
                                String channel = message.substring(message.indexOf("#") + 1,
                                        message.indexOf(" "));
                                String status = message.substring(message.indexOf(" ") + 1,
                                        message.length());

                                clientListener.onMessageReceived(status, channel,
                                        Message.MessageType.STATUS);
                            }
                            // if general status
                            else {
                                clientListener.onMessageReceived(message, null, null);
                            };
                        } else if (message.startsWith("#")) {
                            String channel = message.substring(1, message.indexOf(" "));
                            String user = message.substring(message.indexOf(" ") + 1, message.indexOf(":"));

                            if (user.equals(username)) {
                                clientListener.onMessageReceived(
                                        message.substring(message.indexOf(" ") + 1,
                                                message.length()), channel,
                                        Message.MessageType.CURRENT_USER);
                            } else {
                                clientListener.onMessageReceived(
                                        message.substring(message.indexOf(" ") + 1,
                                                message.length()), channel,
                                        Message.MessageType.OTHER_USER);
                            }
                        }

                        stringBuffer = readerBuffer.readLine();
                    }
                } catch (IOException ioEx) {
                    Log.e("Client", ioEx.getLocalizedMessage(), ioEx);
                }
            }
        };
    }
}