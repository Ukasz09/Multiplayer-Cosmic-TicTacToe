package com.github.Ukasz09.ticTacToeTDD.server;


import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ClientHandler extends Thread implements IMsgObservable {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private String exitMsg;
    private Set<IMsgObserver> observerSet;
    private char sign;

    //----------------------------------------------------------------------------------------------------------------//
    public ClientHandler(Socket socket, String exitMsg, char clientSign) throws IOException {
        this.clientSocket = socket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.exitMsg = exitMsg;
        observerSet = new HashSet<>(1);
        sign = clientSign;
    }

    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public void run() {
        try {
            readMessages();
            close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    private void readMessages() throws IOException {
        String inputLine = "";
        while (!inputLine.equals(exitMsg))
            notifyObservers(in.readLine(), sign);
        System.out.println("Disconnecting client ...");
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    private void close() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }

    @Override
    public void attachObserver(IMsgObserver observer) {
        observerSet.add(observer);
    }

    @Override
    public void detachObserver(IMsgObserver observer) {
        observerSet.remove(observer);
    }

    @Override
    public void notifyObservers(String msg, char signIdentifier) {
        observerSet.forEach(o -> o.updateSubscriber(msg, signIdentifier));
    }
}