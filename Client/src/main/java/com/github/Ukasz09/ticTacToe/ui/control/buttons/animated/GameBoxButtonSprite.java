package com.github.Ukasz09.ticTacToe.ui.control.buttons.animated;

import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;

public class GameBoxButtonSprite extends AnimatedButtonSprite {
    private static final ImageSheetProperty SHEET = SpritesProperties.gridBoxProperty();

    private int coordsX;
    private int coordsY;

    //----------------------------------------------------------------------------------------------------------------//
    public GameBoxButtonSprite(int coordsX, int coordsY, double buttonSize, boolean withImageViewInRoot) {
        super(buttonSize, buttonSize, SHEET, withImageViewInRoot);
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

    public void interactionWithBox(boolean allowed, boolean removeFromRoot) {
        if (removeFromRoot) {
            removeNodeFromRoot();
        } else if (allowed)
            enable();
        else disable();
    }
}
