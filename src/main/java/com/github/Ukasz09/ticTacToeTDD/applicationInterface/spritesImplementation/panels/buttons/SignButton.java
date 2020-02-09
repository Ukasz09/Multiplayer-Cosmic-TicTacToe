package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.panels.buttons;


import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.AnimatedSprite;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.states.SpriteStates;
import javafx.scene.input.MouseEvent;

import java.util.HashSet;
import java.util.Set;

public class SignButton extends AnimatedSprite implements IEventKindObservable {
    public final static double WIDTH_TO_FRAME_PROPORTION = 20 / 192d;
    public final static double HEIGHT_TO_FRAME_PROPORTION = 20 / 192d;

    private Set<IEventKindObserver> observers;

    public SignButton(ImageSheetProperty sheetProperty, double positionX, double positionY) {
        super(getWidthAfterScaling(WIDTH_TO_FRAME_PROPORTION), getHeightAfterScaling(HEIGHT_TO_FRAME_PROPORTION), positionX, positionY, sheetProperty, sheetProperty.getAction(SpriteStates.STANDBY));
        observers = new HashSet<>();
        addButtonEventHandler();
    }

    public void addButtonEventHandler(){
        addNewEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            notifyObservers(EventKind.SIGN_BUTTON_WAS_CHOSEN);
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
