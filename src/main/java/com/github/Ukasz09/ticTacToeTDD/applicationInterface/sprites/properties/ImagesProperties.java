package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties;

import javafx.scene.image.Image;

public class ImagesProperties {
    private static final int AVATARS_QTY = 12;

    public static Image schemeSpriteForImageView() {
        String imagePath = "images/decorations/testedForImageView.png";
        return new Image(imagePath);
    }

    public static Image cosmic1Background() {
        String imagePath = "images/backgrounds/backgroundCosmic1.jpg";
        return new Image(imagePath);
    }

//    public static Image nightSky1Background() {
//        String imagePath = "images/backgrounds/startFlare.gif";
//        return new Image(imagePath);
//    }

    public static Image startGameBackground() {
        String imagePath = "images/backgrounds/galaxyBackground.gif";
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
