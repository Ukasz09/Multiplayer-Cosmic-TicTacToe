package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.AnimatedSprite;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public abstract class AnimatedButtonSprite extends AnimatedSprite implements IEventKindObservable {
    private final Effect HUE_EFFECT = new ColorAdjust((int) (Math.random() * 3) - 1, 0, 0, 0);

    private boolean isActive = true;
    private Set<IEventKindObserver> observers;

    //-----------------------------------------------------------------------------------------------------------------//
    public AnimatedButtonSprite(double width, double height, ImageSheetProperty sheetProperty) {
        super(width, height, 0, 0, sheetProperty, sheetProperty.getAction(SpriteStates.STANDBY));
        observers = new HashSet<>();
    }

    //-----------------------------------------------------------------------------------------------------------------//
    public void disable() {
        isActive = false;
    }

    //-----------------------------------------------------------------------------------------------------------------//
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

    public boolean isActive() {
        return isActive;
    }

    protected Image getSheetEffect(Effect effectToApply) {
        ImageView view = new ImageView();
        SnapshotParameters snapshot = new SnapshotParameters();
        snapshot.setFill(Color.TRANSPARENT);
        view.setImage(getSpriteSheetProperty().getSheet());
        view.setEffect(effectToApply);
        Image newSheet = view.snapshot(snapshot, null);
        return newSheet;
    }

    protected Image getHuedSheet() {
        return getSheetEffect(HUE_EFFECT);
    }

    protected void hueSheet(){
        getSpriteSheetProperty().setImageSheet(getHuedSheet());
    }

}