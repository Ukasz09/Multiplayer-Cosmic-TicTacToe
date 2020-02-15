package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons;


import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.AnimatedSprite;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObservable;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public class SignButton extends AnimatedSprite implements IEventKindObservable {
    public final static double WIDTH_TO_FRAME_PROPORTION = 20 / 192d;
    public final static double HEIGHT_TO_FRAME_PROPORTION = 20 / 192d;
    private static final Effect EFFECT_FOR_DISABLE_STATUS = new Lighting(new Light.Distant(10, 10, Color.GRAY));

    private boolean isActive = true;
    private Set<IEventKindObserver> observers;

    //-----------------------------------------------------------------------------------------------------------------//
    public SignButton(ImageSheetProperty sheetProperty) {
        super(ViewManager.getInstance().getScaledWidth(WIDTH_TO_FRAME_PROPORTION),
                ViewManager.getInstance().getScaledHeight(HEIGHT_TO_FRAME_PROPORTION),
                0, 0, sheetProperty, sheetProperty.getAction(SpriteStates.STANDBY));
        observers = new HashSet<>();
    }

    //-----------------------------------------------------------------------------------------------------------------//
    public void disable() {
        isActive = false;
        setSheetEffectToDisable();
    }

    private void setSheetEffectToDisable() {
        ImageView view = new ImageView();
        SnapshotParameters snapshot = new SnapshotParameters();
        snapshot.setFill(Color.TRANSPARENT);
        view.setImage(getSpriteSheetProperty().getSheet());
        view.setEffect(EFFECT_FOR_DISABLE_STATUS);
        Image newSheet = view.snapshot(snapshot, null);
        setSpriteSheet(newSheet);
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
}
