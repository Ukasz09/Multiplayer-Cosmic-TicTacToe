package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;

public class OscarStatue extends AnimatedSprite {
    private static final ImageSheetProperty DEFAULT_SHEET_PROPERTY = SpritesProperties.oscarProperty();

    //----------------------------------------------------------------------------------------------------------------//
    public OscarStatue(double width, double height, double positionX, double positionY) {
        super(width, height, positionX, positionY, DEFAULT_SHEET_PROPERTY, DEFAULT_SHEET_PROPERTY.getAction(SpriteStates.STANDBY));
    }
}
