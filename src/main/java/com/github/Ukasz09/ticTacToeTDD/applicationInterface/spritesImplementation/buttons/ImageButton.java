package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.buttons;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class ImageButton extends GameButton {
    private static final double SIZE_PROPORTION = 14 / 108d;
    protected static final Effect BUTTON_EXITED_EFFECT = new Lighting(new Light.Distant(0, 5, Color.GRAY));

    //----------------------------------------------------------------------------------------------------------------//
    public ImageButton(Image image) {
        super();
        setIconImage(image);
        setDefaultEvents();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setIconImage(Image image) {
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(manager.getScaledWidth(SIZE_PROPORTION));
        imageView.setFitHeight(manager.getScaledWidth(SIZE_PROPORTION));
        setGraphic(imageView);
    }

    @Override
    protected void setDefaultEvents() {
        setEffect(BUTTON_EXITED_EFFECT);
        setOnMouseEntered(event -> setEffect(null));
        setOnMouseExited(event ->setEffect(BUTTON_EXITED_EFFECT));
    }
}
