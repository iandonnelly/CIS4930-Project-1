package com.example.ianjavier.project1.domain;

import android.util.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;


public class ThreadedServer extends Thread {
    private static ServerSocket serverSocket = null;
    //Keep track of names and printWriters
    //private static HashSet<String> names = new HashSet<String>();
    private static HashSet<PrintWriter> printWriters = new HashSet<PrintWriter>();

    public static void startServer(int port, ServerListener serverListener){
        try{
            serverSocket = new ServerSocket(port);

            if (serverSocket != null) {
                serverListener.onServerStartedSuccess();
            } else {
                serverListener.onServerStartedFailure();
            }
        } catch (IOException ioEx){
            Log.e("Server", ioEx.getLocalizedMessage(), ioEx);
        }
        //Listens for new connections, launches a new thread when a new client connects
        try{
            while (true && !Thread.currentThread().isInterrupted()) {
                new Server(serverSocket.accept()).start();
            }
        } catch(IOException ioEx){
            Log.e("Server", ioEx.getLocalizedMessage(), ioEx);
        }
    }
    public static void stopServer(ServerListener serverListener){
        try{
            if(serverSocket != null){
                for (PrintWriter printWriter : printWriters){
                    printWriter.println("Server closed");
                }
                serverSocket.close();
                serverListener.onServerStopped();
            }
        } catch (IOException ioEx){
            Log.e("Server", ioEx.getLocalizedMessage(), ioEx);
        }
        serverSocket = null;
    }
    private static class Server extends Thread{
        //private String name;
        private Socket socket;
        private BufferedReader readerBuffer;
        private PrintWriter printWriter;

        public Server(Socket socket) {
            this.socket = socket;
        }

        public void run(){
            try{
                readerBuffer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                printWriter = new PrintWriter(socket.getOutputStream(), true);
                printWriters.add(printWriter);
                /*
                while(true){
                printWriter.println("NEEDNAME");
                name = readerBuffer.readLine();
                if(name == null){
                return;
                }
                synchronized (names){
                if (!names.contains(name)){
                names.add(name);
                break;
                }
                }
                }
                printWriter.println("VALID");
                */
                while(true){
                    String input = readerBuffer.readLine();
                    if(input == null){
                        return;
                    }

                    if (input.startsWith("STATUS")) {
                        //receivedListener.onMessageReceived(input.substring(7, input.length()),
                               // OnMessageReceivedListener.MessageType.STATUS);
                    } else {
                        //receivedListener.onMessageReceived(input,
                                //OnMessageReceivedListener.MessageType.OTHER_USER);
                    }
                    for (PrintWriter printWriter : printWriters){
                        printWriter.println(input);
                    }
                }
            } catch (IOException ioEx){
                Log.e("Server", ioEx.getLocalizedMessage(), ioEx);
            } finally {
                /*if(name != null) {
                names.remove(name);
                }*/
                if (printWriter != null) {
                    printWriters.remove(printWriter);
                }
            }
        }
    }
}

