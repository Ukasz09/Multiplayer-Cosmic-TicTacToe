package com.github.Ukasz09.ticTacToe.ui.control.buttons.normal;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameImageButton extends GameButton {
    private static final double SIZE_PROPORTION = 14 / 108d;

    //----------------------------------------------------------------------------------------------------------------//
    public GameImageButton(Image image) {
        super();
        setIconImage(image);
    }

    public GameImageButton(ImageView imageView, double width, double height) {
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
