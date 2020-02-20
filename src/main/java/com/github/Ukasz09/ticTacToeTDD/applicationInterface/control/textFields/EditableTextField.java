package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.textFields;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.FontProperties;
import javafx.geometry.Pos;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

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
