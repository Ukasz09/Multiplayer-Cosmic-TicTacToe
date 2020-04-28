package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;

public interface IEventHandler {
    <T extends Event> void addNewEventHandler(EventType<T> eventType, EventHandler<T> eventHandler);

    <T extends Event> boolean removeEventHandler(EventType<T> eventType, EventHandler<T> eventHandler);

    boolean removeNodeFromRoot();
}
