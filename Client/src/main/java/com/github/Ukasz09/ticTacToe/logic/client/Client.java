package com.github.Ukasz09.ticTacToe.logic.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    private static final int CONNECTION_TIMEOUT = 3000;

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    //----------------------------------------------------------------------------------------------------------------//
    public void startConnection(String serverAddr, int port) throws IOException {
        clientSocket = new Socket();
        clientSocket.connect(new InetSocketAddress(serverAddr, port), CONNECTION_TIMEOUT);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String msg) {
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

    public boolean isConnected() {
        return clientSocket != null && clientSocket.isConnected();
    }
}
