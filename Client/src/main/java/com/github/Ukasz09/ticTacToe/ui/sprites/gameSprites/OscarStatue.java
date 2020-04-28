package com.github.Ukasz09.ticTacToe.ui.sprites.gameSprites;

import com.github.Ukasz09.ticTacToe.ui.sprites.AnimatedSprite;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;

public class OscarStatue extends AnimatedSprite {
    private static final ImageSheetProperty DEFAULT_SHEET_PROPERTY = SpritesProperties.oscarProperty();

    //----------------------------------------------------------------------------------------------------------------//
    public OscarStatue(double width, double height, double positionX, double positionY) {
        super(width, height, positionX, positionY, DEFAULT_SHEET_PROPERTY, DEFAULT_SHEET_PROPERTY.getAction(SpriteStates.STANDBY), false);
    }
}
