package com.github.Ukasz09.ticTacToeTDD.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private static final String SERVER_IP = "127.0.0.1";

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    //----------------------------------------------------------------------------------------------------------------//
    public void startConnection(int port) throws IOException {
        clientSocket = new Socket(SERVER_IP, port);
        out = new PrintWriter(clientSocket.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
    }

    public void sendMessage(String msg) {
        System.out.println("WYSLANE: " + msg);
        out.println(msg);
    }

    public String readResponse() throws IOException {
        return in.readLine();
    }

    public void stopConnection() throws IOException {
        in.close();
        out.close();
        clientSocket.close();
    }
}
