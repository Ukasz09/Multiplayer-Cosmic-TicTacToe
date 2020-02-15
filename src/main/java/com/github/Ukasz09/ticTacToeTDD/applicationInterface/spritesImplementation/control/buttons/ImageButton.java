package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.control.buttons;

import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;

public class ImageButton extends GameButton {
    private static final double SIZE_PROPORTION = 14 / 108d;

    //----------------------------------------------------------------------------------------------------------------//
    public ImageButton(Image image) {
        super();
        setIconImage(image);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setIconImage(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(manager.getScaledWidth(SIZE_PROPORTION));
        imageView.setFitHeight(manager.getScaledWidth(SIZE_PROPORTION));
        setGraphic(imageView);
    }


}
