package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsPlayer;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImagesProperties;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ChooseBackground extends MyBackground {
    private static final Image DEFAULT_IMAGE = ImagesProperties.signChooseBackground();

    public ChooseBackground() {
        super(DEFAULT_IMAGE, null);
    }

}
