package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.panels;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.SignChooseBackground;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class SignChoosePanel extends ChoosePanel {
    private static final String LABEL_TEXT = "Choose your game sign!";

    public SignChoosePanel() {
        super(new SignChooseBackground(), LABEL_TEXT);
        addSignButtonsToPanel();
    }

    private void addSignButtonsToPanel() {
        Button button = new Button("XDDDD"); //todo: tmp
        getContentPanel().getChildren().add(button);
    }

    @Override
    public void update() {
        //nothing to do
    }
}
