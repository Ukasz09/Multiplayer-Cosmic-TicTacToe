package com.github.Ukasz09.ticTacToe.logic.server;

import com.github.Ukasz09.ticTacToe.controller.Logger;
import com.github.Ukasz09.ticTacToe.logic.game.GameLogic;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

public class Server {
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
        for (Character c : signs)
            availablePlayerSigns.add(c);
        Collections.shuffle(this.availablePlayerSigns);
    }

    public void start(int port, IMsgObserver msgObserver) throws IOException {
        serverSocket = new ServerSocket(port);
        Logger.logCommunicate("Server is running...");
        connectNewClients(msgObserver);
    }

    private void connectNewClients(IMsgObserver msgObserver) throws IOException {
        while (connectedClients < 2)
            try {
                addNewClientHandler(msgObserver).start();
            } catch (SocketException e) {
                stop();
            }
    }

    private ClientHandler addNewClientHandler(IMsgObserver msgObserver) throws IOException {
        char clientSign = availablePlayerSigns.remove(0);
        ClientHandler handler = new ClientHandler(serverSocket.accept(), clientSign, msgObserver);
        clientHandlers.put(clientSign, handler);
        connectedClients++;
        Logger.logCommunicate("Client connected. Sign: " + clientSign);
        sendMessage(Messages.GIVEN_CLIENT_SIGN + Messages.DELIMITER + GameLogic.getPlayerId(clientSign), clientSign);
        return handler;
    }

    public void sendMessageToAll(String msg) {
        clientHandlers.forEach((k, v) -> v.sendMessage(msg));
    }

    public void sendMessage(String msg, char signIdentifier) {
        System.out.println("Wiadmoasc do wyslaania: " + msg + ", do: " + signIdentifier); //todo: tmp
        clientHandlers.forEach((k, v) -> {
            if (k == signIdentifier)
                v.sendMessage(msg);
        });
    }

    public void closeClient(char clientSign) {
        clientHandlers.forEach((k, v) -> {
            if (k == clientSign) {
                try {
                    v.close();
                } catch (IOException e) {
                    //unchecked
                }
            }
        });
    }

    public void stop() throws IOException {
        serverSocket.close();
    }
}
