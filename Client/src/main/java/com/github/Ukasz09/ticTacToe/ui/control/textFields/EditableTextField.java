package com.github.Ukasz09.ticTacToe.ui.control.textFields;

import com.github.Ukasz09.ticTacToe.ui.control.buttons.ButtonsProperties;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;

public class EditableTextField extends GameTextField {
    private final Effect incorrectDataEffect = new InnerShadow(100, Color.DARKRED);

    //----------------------------------------------------------------------------------------------------------------//
    public EditableTextField(String promptText) {
        super(true);
        setPromptText(promptText);
        setDefaultEvents();
        setEditable(true);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setDefaultEvents() {
        setOnMouseEntered(event -> setEffect(ButtonsProperties.mouseEnteredEffect()));
        setOnKeyPressed(event -> setEffect(ButtonsProperties.mouseEnteredEffect()));
        setOnMouseExited(event -> setEffect(null));
    }

    public void applyIncorrectDataEffect() {
        setEffect(incorrectDataEffect);
    }
}
