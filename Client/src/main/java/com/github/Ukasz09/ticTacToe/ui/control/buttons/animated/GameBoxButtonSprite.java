package com.github.Ukasz09.ticTacToe.ui.control.buttons.animated;

import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;
import javafx.scene.input.MouseEvent;

public class GameBoxButtonSprite extends AnimatedButtonSprite {
    private static final ImageSheetProperty SHEET = SpritesProperties.gridBoxProperty();

    private int coordsX;
    private int coordsY;
    private boolean animationIsEnable = true;

    //----------------------------------------------------------------------------------------------------------------//
    public GameBoxButtonSprite(int coordsX, int coordsY, double buttonSize, boolean withImageViewInRoot) {
        super(buttonSize, buttonSize, SHEET, withImageViewInRoot, SHEET.getAction(SpriteStates.NO_ANIMATION));
        this.coordsX = coordsX;
        this.coordsY = coordsY;
        addAnimationEvents();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void addAnimationEvents() {
        addNewEventHandler(MouseEvent.MOUSE_ENTERED, event -> {
            if (animationIsEnable)
                changeState(SpriteStates.STANDBY);
        });
        addNewEventHandler(MouseEvent.MOUSE_EXITED, event -> {
            if (animationIsEnable)
                changeState(SpriteStates.NO_ANIMATION);
        });
    }

    public int getCoordsX() {
        return coordsX;
    }

    public int getCoordsY() {
        return coordsY;
    }

    public void interactionWithBox(boolean allowed, boolean removeFromRoot) {
        if (removeFromRoot) {
            removeNodeFromRoot();
        } else if (allowed)
            enable();
        else disable();
    }

    public void setAnimationIsEnable(boolean animationIsEnable) {
        this.animationIsEnable = animationIsEnable;
    }
}
