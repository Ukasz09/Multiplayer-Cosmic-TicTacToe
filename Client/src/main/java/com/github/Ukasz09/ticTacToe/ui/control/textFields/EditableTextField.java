package com.github.Ukasz09.ticTacToe.ui.control.textFields;

import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

public class EditableTextField extends GameTextField {
    private static final Effect DEFAULT_INCORRECT_DATA_EFFECT = new InnerShadow(100, Color.DARKRED);

    //----------------------------------------------------------------------------------------------------------------//
    public EditableTextField(String promptText) {
        super(true);
        setPromptText(promptText);
        setDefaultEvents();
        setEditable(true);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setDefaultEvents() {
        setOnMouseEntered(event -> setEffect(DEFAULT_MOUSE_ENTERED_EFFECT));
        setOnKeyPressed(event -> setEffect(DEFAULT_MOUSE_ENTERED_EFFECT));
        setOnMouseExited(event -> setEffect(null));
    }

    public void applyIncorrectDataEffect() {
        setEffect(DEFAULT_INCORRECT_DATA_EFFECT);
    }
}
