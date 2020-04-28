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
    private String exitMsg;
    private IMsgObserver msgObserver;
    private char sign;

    //----------------------------------------------------------------------------------------------------------------//
    public ClientHandler(Socket socket, String exitMsg, char clientSign, IMsgObserver msgObserver) throws IOException {
        this.clientSocket = socket;
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.exitMsg = exitMsg;
        this.msgObserver = msgObserver;
        sign = clientSign;
    }

    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public void run() {
        try {
            readMessages();
            close();
        } catch (IOException e) {
            Logger.logError(getClass(), e.getMessage());
            e.printStackTrace();
        }
    }

    private void readMessages() throws IOException {
        String inputLine = "";
        while (!inputLine.equals(exitMsg))
            msgObserver.updateMsgObserver(in.readLine(), sign);
    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    private void close() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}