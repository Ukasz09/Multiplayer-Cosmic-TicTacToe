package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.panels.buttons;


import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.AnimatedSprite;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.states.SpriteStates;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

import java.util.HashSet;
import java.util.Set;

public class SignButton extends AnimatedSprite implements IEventKindObservable {
    public final static double WIDTH_TO_FRAME_PROPORTION = 20 / 192d;
    public final static double HEIGHT_TO_FRAME_PROPORTION = 20 / 192d;

    private Set<IEventKindObserver> observers;
    private EventKind emittedEventKind;

    public SignButton(ImageSheetProperty sheetProperty, EventKind emittedEventKind) {
        super(getWidthAfterScaling(WIDTH_TO_FRAME_PROPORTION), getHeightAfterScaling(HEIGHT_TO_FRAME_PROPORTION), 0, 0, sheetProperty, sheetProperty.getAction(SpriteStates.STANDBY));
        observers = new HashSet<>();
        this.emittedEventKind = emittedEventKind;
        addButtonEventHandler();
    }

    public void addButtonEventHandler() {
        addNewEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            notifyObservers(emittedEventKind);
        });
    }

    @Override
    public void attachObserver(IEventKindObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IEventKindObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(EventKind eventKind) {
        for (IEventKindObserver observer : observers)
            observer.updateObserver(eventKind);
    }

}
