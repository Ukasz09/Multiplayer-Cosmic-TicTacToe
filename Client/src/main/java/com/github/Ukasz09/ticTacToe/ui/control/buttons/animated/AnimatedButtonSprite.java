package com.github.Ukasz09.ticTacToe.ui.control.buttons.animated;

import com.github.Ukasz09.ticTacToe.ui.sprites.AnimatedSprite;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.FrameStatePositions;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObservable;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObserver;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.Effect;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

import java.util.HashSet;
import java.util.Set;

public abstract class AnimatedButtonSprite extends AnimatedSprite implements IGuiObservable {
    private boolean isActive = true;
    private Set<IGuiObserver> observers;

    //-----------------------------------------------------------------------------------------------------------------//
    public AnimatedButtonSprite(double width, double height, ImageSheetProperty sheet, boolean withImageViewInRoot) {
        this(width, height, sheet, withImageViewInRoot, sheet.getAction(SpriteStates.STANDBY));
    }

    public AnimatedButtonSprite(double width, double height, ImageSheetProperty sheet, boolean withImageViewInRoot, FrameStatePositions startedFrameState) {
        super(width, height, 0, 0, sheet, startedFrameState, withImageViewInRoot);
        observers = new HashSet<>(2);
    }

    //-----------------------------------------------------------------------------------------------------------------//
    public void disable() {
        isActive = false;
    }

    public void enable() {
        isActive = true;
        setRandomStartedFramePosition();
    }

    @Override
    public void attachObserver(IGuiObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IGuiObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(GuiEvents event) {
        observers.forEach(observer -> observer.updateGuiObserver(event));
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Bloat operation. User responsibly
     */
    protected Image getSheetWithEffect(Image sheet, Effect effectToApply, double opacity) {
        ImageView view = new ImageView();
        SnapshotParameters snapshot = new SnapshotParameters();
        snapshot.setFill(Color.TRANSPARENT);
        view.setImage(sheet);
        view.setEffect(effectToApply);
        view.setOpacity(opacity);
        return view.snapshot(snapshot, null);
    }
}
