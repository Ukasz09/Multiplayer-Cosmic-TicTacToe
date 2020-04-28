package com.github.Ukasz09.ticTacToeTDD.server;

public interface IMsgObservable {
    void attachObserver(IMsgObserver observer);

    void detachObserver(IMsgObserver observer);

    void notifyObservers(String message, char signIdentifier);
}
