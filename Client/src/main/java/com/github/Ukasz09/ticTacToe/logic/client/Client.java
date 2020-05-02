package com.github.Ukasz09.ticTacToe.logic.client;

import com.github.Ukasz09.ticTacToe.logic.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    //----------------------------------------------------------------------------------------------------------------//
    public void startConnection(int port) throws IOException {
        try {
            clientSocket = new Socket(SERVER_IP, port);
        } catch (ConnectException e) {
            Logger.logError(getClass(), "Server is offline or other connection error: " + e.getMessage());
            System.exit(-1);
        }
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String msg) {
        System.out.println("WYSLANE: " + msg); //todo: tmp
        out.println(msg);
    }

    public String readResponse() throws IOException {
        return in.readLine();
    }

    public void closeConnection() throws IOException {
        try {
            in.close();
            out.close();
        } catch (Exception e) {
            //unchecked
        } finally {
            clientSocket.close();
        }
    }

    public String getCompoundMsg(String baseMsg, String[] extras) {
        StringBuilder msg = new StringBuilder(baseMsg);
        for (String m : extras)
            msg.append(Messages.DELIMITER).append(m);
        return msg.toString();
    }
}
