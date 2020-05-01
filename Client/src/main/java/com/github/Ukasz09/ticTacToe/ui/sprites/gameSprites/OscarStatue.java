package com.github.Ukasz09.ticTacToe.ui.sprites.gameSprites;

import com.github.Ukasz09.ticTacToe.ui.sprites.AnimatedSprite;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;

public class OscarStatue extends AnimatedSprite {

    public OscarStatue(double width, double height, double positionX, double positionY) {
        super(width, height, positionX, positionY, SpritesProperties.oscarProperty(), false);
    }
}
