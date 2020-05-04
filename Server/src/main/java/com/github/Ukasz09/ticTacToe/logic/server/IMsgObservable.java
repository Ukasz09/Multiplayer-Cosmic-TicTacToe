package com.github.Ukasz09.ticTacToe.logic.server;

public interface IMsgObservable {
    void attachMsgObserver(IMsgObserver observer);

    void detachMsgObserver(IMsgObserver observer);

    void notifyMsgObservers(String message, char signIdentifier);
}
