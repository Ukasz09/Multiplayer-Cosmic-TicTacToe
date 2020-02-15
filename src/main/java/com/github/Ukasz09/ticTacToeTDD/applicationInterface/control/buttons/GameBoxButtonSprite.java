package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.IKindOfState;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;

public class GameBoxButtonSprite extends AnimatedButtonSprite {
    public static final double SIZE_PROPORTION = 25 / 192d;
    private static final ImageSheetProperty SHEET_PROPERTY = SpritesProperties.gridBoxProperty();

    public GameBoxButtonSprite() {
        super(ViewManager.getInstance().getScaledWidth(SIZE_PROPORTION), ViewManager.getInstance().getScaledWidth(SIZE_PROPORTION),
                SHEET_PROPERTY);

    }
}
