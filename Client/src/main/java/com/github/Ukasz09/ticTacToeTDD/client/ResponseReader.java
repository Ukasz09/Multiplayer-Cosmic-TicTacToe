package com.github.Ukasz09.ticTacToeTDD.client;

import com.github.Ukasz09.ticTacToeTDD.Logger;
import com.github.Ukasz09.ticTacToeTDD.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.eventObservers.IEventKindObserver;

import java.io.IOException;

public class ResponseReader extends Thread {
    private static final String NAME = "ResponseReader";

    private IEventKindObserver messageObserver;
    private Client client;
    private String lastReaderMsg; //null = all messages proceeded
    private boolean gameIsEnd = false;

    //----------------------------------------------------------------------------------------------------------------//
    public ResponseReader(IEventKindObserver messageObserver, Client client) {
        super(NAME);
        this.client = client;
        this.messageObserver = messageObserver;
    }

    @Override
    public void run() {
        while (!gameIsEnd) {
            try {
                if (lastReaderMsg == null) {
                    lastReaderMsg = client.readResponse();
                    messageObserver.updateObserver(EventKind.READED_MSG);
                }
            } catch (IOException e) {
                Logger.logError(getClass(), "Can't read server response: " + e.getMessage());
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    public void stopReading() {
        gameIsEnd = true;
    }
}