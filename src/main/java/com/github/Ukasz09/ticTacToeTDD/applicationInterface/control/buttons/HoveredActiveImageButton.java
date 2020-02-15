package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons;

import javafx.scene.effect.Effect;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

public class HoveredActiveImageButton extends ImageButton {
    private static final Effect BUTTON_EXITED_EFFECT = new Lighting(new Light.Distant(10, 10, Color.GRAY));

    public HoveredActiveImageButton(Image image) {
        super(image);
        setDefaultEvents();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setDefaultEvents() {
        setEffect(BUTTON_EXITED_EFFECT);
        setOnMouseEntered(event -> setEffect(null));
        setOnMouseExited(event -> setEffect(BUTTON_EXITED_EFFECT));
    }
}
