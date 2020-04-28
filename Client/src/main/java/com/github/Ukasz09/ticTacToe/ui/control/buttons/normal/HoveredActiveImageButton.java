package com.github.Ukasz09.ticTacToe.ui.control.buttons.normal;

import javafx.scene.image.Image;

public class HoveredActiveImageButton extends GameImageButton {

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
