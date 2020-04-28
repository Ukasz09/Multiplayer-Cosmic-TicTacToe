package com.github.Ukasz09.ticTacToeTDD.server;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

public class Server {
    private static final String EXIT_MSG = "DISCONNECT_CLIENT";

    private ServerSocket serverSocket;
    private HashMap<Character, ClientHandler> clientHandlers;
    private List<Character> availablePlayerSigns;
    private int connectedClients;

    //----------------------------------------------------------------------------------------------------------------//
    public Server(char[] playerSigns) {
        this.clientHandlers = new HashMap<>(2);
        initializePlayerSigns(playerSigns);
        connectedClients = 0;
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initializePlayerSigns(char[] signs) {
        this.availablePlayerSigns = new ArrayList<>(2);
        Collections.shuffle(this.availablePlayerSigns);
        for (Character c : signs)
            availablePlayerSigns.add(c);
    }

    public void start(int port, IMsgObserver msgObserver) throws IOException {
        serverSocket = new ServerSocket(port);
        connectNewClients(msgObserver);
    }

    private void connectNewClients(IMsgObserver msgObserver) throws IOException {
        while (connectedClients < 2)
            addNewClientHandler(msgObserver).start();
    }

    private ClientHandler addNewClientHandler(IMsgObserver msgObserver) throws IOException {
        char clientSign = availablePlayerSigns.remove(0);
        ClientHandler handler = new ClientHandler(serverSocket.accept(), EXIT_MSG, clientSign);
        handler.attachObserver(msgObserver);
        clientHandlers.put(clientSign, handler);
        connectedClients++;
        return handler;
    }

    public void sendMessageToAll(String msg) {
        clientHandlers.forEach((k, v) -> v.sendMessage(msg));
    }

    public void sendMessage(String msg, char signIdentifier) {
        clientHandlers.forEach((k, v) -> {
            if (k == signIdentifier)
                v.sendMessage(msg);
        });
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}
