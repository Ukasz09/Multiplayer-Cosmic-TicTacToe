package com.github.Ukasz09.ticTacToeTDD.server;

public interface IMsgObserver {
    void updateSubscriber(String msg, char signIdentifier);
}
