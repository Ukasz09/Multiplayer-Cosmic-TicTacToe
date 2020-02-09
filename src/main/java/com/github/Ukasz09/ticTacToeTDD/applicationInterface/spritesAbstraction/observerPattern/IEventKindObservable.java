package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern;

public interface IEventKindObservable {
    void attachObserver(IEventKindObserver observer);

    void detachObserver(IEventKindObserver observer);

    void notifyObservers(EventKind eventKind);
}
