package com.github.Ukasz09.ticTacToe.ui.sprites.properties;

import javafx.scene.image.Image;

public class ImagesProperties {
    //instances
    private static Image[] avatarsInst;
    private static Image waitingAnimationInst;
    private static Image startGameBackgroundInst;
    private static Image schemeSpriteInst;


    //----------------------------------------------------------------------------------------------------------------//
    private static final int AVATARS_QTY = 14;

    public static Image schemeSpriteForImageView() {
        if (schemeSpriteInst == null) {
            String imagePath = "images/decorations/testedForImageView.png";
            schemeSpriteInst = new Image(imagePath);
        }
        return schemeSpriteInst;
    }

    public static Image startGameBackground() {
        if (startGameBackgroundInst == null) {
            String imagePath = "images/backgrounds/galaxyBackground.gif";
            startGameBackgroundInst = new Image(imagePath);
        }
        return startGameBackgroundInst;
    }

    public static Image waitingAnimation() {
        if (waitingAnimationInst == null) {
            String imagePath = "images/decorations/waiting.gif";
            waitingAnimationInst = new Image(imagePath);
        }
        return waitingAnimationInst;
    }

    public static Image[] avatars() {
        if (avatarsInst == null) {
            String imagePathPrefix = "images/avatars/";
            Image[] images = new Image[AVATARS_QTY];
            for (int i = 0; i < AVATARS_QTY; i++)
                images[i] = new Image(imagePathPrefix + (i + 1) + ".png");
            avatarsInst = images;
        }
        return avatarsInst;
    }

    public static void clearImageInstances() {
        avatarsInst = null;
        waitingAnimationInst = null;
        startGameBackgroundInst = null;
    }
}
