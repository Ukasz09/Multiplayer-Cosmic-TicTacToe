package com.github.Ukasz09.ticTacToe.logic.server;

import com.github.Ukasz09.ticTacToe.controller.Logger;

import java.io.*;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

public class ClientHandler extends Thread {
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;
    private IMsgObserver msgObserver;
    private char sign;
    private boolean gameIsEnd = false;

    //----------------------------------------------------------------------------------------------------------------//
    public ClientHandler(Socket socket, char clientSign, IMsgObserver msgObserver) throws IOException {
        this.clientSocket = socket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.msgObserver = msgObserver;
        sign = clientSign;
    }

    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public void run() {
        try {
            readMessages();
        } catch (IOException e) {
            Logger.logError(getClass(), e.getMessage());
            e.printStackTrace();
        }
    }

    private void readMessages() throws IOException {
        while (!gameIsEnd)
            msgObserver.updateMsgObserver(in.readLine(), sign);
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    public void close() throws IOException {
        gameIsEnd = true;
        in.close();
        out.close();
        clientSocket.close();
    }
}