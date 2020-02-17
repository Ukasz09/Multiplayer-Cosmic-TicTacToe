package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageButton extends GameButton {
    private static final double SIZE_PROPORTION = 14 / 108d;

    //----------------------------------------------------------------------------------------------------------------//
    public ImageButton(Image image) {
        super();
        setIconImage(image);
    }

    public ImageButton(ImageView imageView, double width, double height) {
        super();
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        setGraphic(imageView);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setIconImage(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(manager.getScaledWidth(SIZE_PROPORTION));
        imageView.setFitHeight(manager.getScaledWidth(SIZE_PROPORTION));
        setGraphic(imageView);
    }


}
