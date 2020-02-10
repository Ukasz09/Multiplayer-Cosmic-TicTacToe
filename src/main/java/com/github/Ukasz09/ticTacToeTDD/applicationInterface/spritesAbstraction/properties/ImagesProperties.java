package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties;

import javafx.scene.image.Image;

public class ImagesProperties {
    private static final int AVATARS_QTY = 6;

    public static Image schemeSpriteForImageView() {
        String imagePath = "images/decorations/testedForImageView.png";
        return new Image(imagePath);
    }

    public static Image woodBackground1() {
        String imagePath = "images/backgrounds/backgroundCosmic1.jpg";
        return new Image(imagePath);
    }

    public static Image signChooseBackground() {
        String imagePath = "images/backgrounds/backgroundNightSky1.jpg";
        return new Image(imagePath);
    }

    public static Image[] avatars() {
        String imagePathPrefix = "images/avatars/";
        Image[] images = new Image[AVATARS_QTY];
        for (int i = 0; i < AVATARS_QTY; i++)
            images[i] = new Image(imagePathPrefix + (i + 1) + ".png");
        return images;
    }
}
