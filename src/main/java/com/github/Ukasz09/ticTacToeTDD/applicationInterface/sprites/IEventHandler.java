package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites;

import javafx.event.EventHandler;
import javafx.event.EventType;

public interface IEventHandler {
    void addNewEventHandler(EventType eventType, EventHandler eventHandler);

    boolean removeEventHandler(EventType eventType, EventHandler eventHandler);

    boolean removeNodeFromRoot();
}
