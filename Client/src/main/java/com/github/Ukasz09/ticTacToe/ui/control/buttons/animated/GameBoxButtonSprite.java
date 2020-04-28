package com.github.Ukasz09.ticTacToe.ui.control.buttons.animated;

import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;

public class GameBoxButtonSprite extends AnimatedButtonSprite {
    private static final ImageSheetProperty SHEET_PROPERTY = SpritesProperties.gridBoxProperty();

    private int coordsX;
    private int coordsY;

    //----------------------------------------------------------------------------------------------------------------//
    public GameBoxButtonSprite(int coordsX, int coordsY, double buttonSize, boolean withImageViewInRoot) {
        super(buttonSize, buttonSize, SHEET_PROPERTY, withImageViewInRoot);
        this.coordsX = coordsX;
        this.coordsY = coordsY;
    }

    //----------------------------------------------------------------------------------------------------------------//
    public int getCoordsX() {
        return coordsX;
    }

    public int getCoordsY() {
        return coordsY;
    }

    public void denyInteractionWithBox() {
        disable();
        removeNodeFromRoot();
        changeState(SpriteStates.NO_ANIMATION);
    }
}
