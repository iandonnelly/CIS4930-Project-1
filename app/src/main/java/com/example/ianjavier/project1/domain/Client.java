package com.example.ianjavier.project1.domain;

import android.util.Log;
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
    /*
    public interface OnValidateNameListener{
    public void onNameValidate(boolean valid);
    public String getName();
    }*/
    static Socket clientSocket = null;
    static OutputStreamWriter output = null;
    static InputStreamReader input = null;
    static BufferedWriter writerBuffer = null;
    static BufferedReader readerBuffer = null;
    static PrintWriter printWriter = null;
    static String username;
    //Takes ip address and port of server and attempts to open a socket

    public static void initConnection(String server, int port, String name){
        username = name;
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

        } catch (UnknownHostException unknownEx){
            Log.e("Client", unknownEx.getLocalizedMessage(), unknownEx);
        } catch (ConnectException connectEx) {
            Log.e("Client", connectEx.getLocalizedMessage(), connectEx);
        } catch (IOException ioEx){
            Log.e("Client", ioEx.getLocalizedMessage(), ioEx);
        }
    }
    public static void disconnectFromServer(String name, ClientListener clientListener){
        try{
            printWriter.println("STATUS " + name + " has disconnected from the server.");
            if(printWriter.checkError()){
                Log.d("Client", "An error occurred when sending message.");
            }
            if(clientSocket != null){
                clientSocket.shutdownInput();
                clientSocket.shutdownOutput();
                clientSocket.close();
                clientListener.onDisconnectedFromServer();
            }
        } catch (IOException ioEx) {
            Log.e("Client", ioEx.getLocalizedMessage(), ioEx);
        }
        //Make clientSocket null
        clientSocket = null;
    }
    //Send message
    public static void sendMessage(String message){
        if(message != null && !message.isEmpty()){
            printWriter.println(username + ": " + message);
            //Check for error
            if(printWriter.checkError()){
                Log.d("Client", "An error occurred when sending message.");
            }
        }
    }
    /*public static boolean validateName(final OnValidateNameListener listener) {
    while(true) {
    //Send name
    sendMessage(listener.getName());
    //Receive reply
    try {
    String stringBuffer = null;
    stringBuffer = readerBuffer.readLine();
    while (stringBuffer != null) {
    String message = stringBuffer;
    if (message.equals("VALID")) {
    listener.onNameValidate(true);
    } else if (message.equals("NEEDNAME")) {
    listener.onNameValidate(false);
    }
    stringBuffer = readerBuffer.readLine();
    }
    } catch (IOException ioEx) {
    Log.e("Client", ioEx.getLocalizedMessage(), ioEx);
    }
    }
    }*/
    //Runnable to use from UI
    public static Runnable clientRunnable(final String server, final int port, final String name,
                                          final ClientListener clientListener){
        return new Runnable() {
            @Override
            public void run() {
                initConnection(server, port, name);
                printWriter.println("STATUS " + name + " has connected to the server.");
                if (printWriter.checkError()) {
                    Log.d("Client", "An error occurred when sending message.");
                }
                //validateName(nameListener);
                //Unable to connect to server
                if (clientSocket == null) {
                    clientListener.onConnectToServerFailure();
                    return;
                } else {
                    clientListener.onConnectToServerSuccess();
                }
                //Try some message stuff
                try {
                    String stringBuffer = null;
                    stringBuffer = readerBuffer.readLine();
                    while (stringBuffer != null && !Thread.currentThread().isInterrupted()) {
                        String message = stringBuffer;

                        if (message.startsWith("STATUS")) {
                            //messageListener.onMessageReceived(message.substring(7, message.length()),
                              //      OnMessageReceivedListener.MessageType.STATUS);
                        } else if (message.startsWith(name)) {
                            //messageListener.onMessageReceived(message,
                             //       OnMessageReceivedListener.MessageType.CURRENT_USER);
                        } else {
                            //messageListener.onMessageReceived(message,
                             //       OnMessageReceivedListener.MessageType.OTHER_USER);
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
