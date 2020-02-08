package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties;

import javafx.scene.image.Image;

public class ImagesProperties {
    public static Image schemeSpriteForImageView() {
        String imagePath = "images/decorations/testedForImageView.png";
        return new Image(imagePath);
    }

    public static Image woodBackground1() {
        String imagePath = "images/backgrounds/backgroundWood1.jpg";
        return new Image(imagePath);
    }
}
