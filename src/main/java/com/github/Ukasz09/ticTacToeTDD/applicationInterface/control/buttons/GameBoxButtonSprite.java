package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.IKindOfState;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;
import javafx.scene.SnapshotParameters;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class GameBoxButtonSprite extends AnimatedButtonSprite {
    public static final double SIZE_PROPORTION = 25 / 192d;
    private static final ImageSheetProperty SHEET_PROPERTY = SpritesProperties.gridBoxProperty();

    private int coordsX;
    private int coordsY;

    //----------------------------------------------------------------------------------------------------------------//
    public GameBoxButtonSprite(int coordsX, int  coordsY) {
        super(ViewManager.getInstance().getScaledWidth(SIZE_PROPORTION), ViewManager.getInstance().getScaledWidth(SIZE_PROPORTION),
                SHEET_PROPERTY);
        this.coordsX=coordsX;
        this.coordsY=coordsY;
        hueSheet();
    }

    //----------------------------------------------------------------------------------------------------------------//
    public int getCoordsX() {
        return coordsX;
    }

    public int getCoordsY() {
        return coordsY;
    }
}
