package com.github.Ukasz09.ticTacToe.ui.control.buttons.normal;

import com.github.Ukasz09.ticTacToe.ui.ViewManager;
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
        setGraphic(getProperSizeImageView(image));
    }

    public static ImageView getProperSizeImageView(Image image) {
        ImageView iv = new ImageView(image);
        iv.setFitWidth(ViewManager.getInstance().getScaledWidth(SIZE_PROPORTION));
        iv.setFitHeight(ViewManager.getInstance().getScaledWidth(SIZE_PROPORTION));
        return iv;
    }
}
