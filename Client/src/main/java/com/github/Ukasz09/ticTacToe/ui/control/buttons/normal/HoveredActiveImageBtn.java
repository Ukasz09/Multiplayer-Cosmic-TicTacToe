package com.github.Ukasz09.ticTacToe.ui.control.buttons.normal;

import com.github.Ukasz09.ticTacToe.ui.control.buttons.ButtonsProperties;
import javafx.scene.image.Image;

public class HoveredActiveImageBtn extends GameImageBtn {

    public HoveredActiveImageBtn(Image image) {
        super(image);
        setDefaultEvents();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setDefaultEvents() {
        setEffect(ButtonsProperties.buttonExitedEffect());
        setOnMouseEntered(event -> setEffect(null));
        setOnMouseExited(event -> setEffect(ButtonsProperties.buttonExitedEffect()));
    }
}
