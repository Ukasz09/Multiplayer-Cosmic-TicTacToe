package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.panels;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.SignChooseBackground;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class SignChoosePanel extends Panel {
    private static final String LABEL_TEXT = "Choose your game sign!";

    private CenteredPane signsPane;
    private LabelPane labelPane;

    public SignChoosePanel() {
        super(new SignChooseBackground());
        initializePanel();
    }

    private void initializePanel() {
        labelPane = new LabelPane(LABEL_TEXT);
        signsPane = new CenteredPane();

        addSignButtonsToPanel();
        setCenter(signsPane);
        setTop(labelPane);
    }

    private void addSignButtonsToPanel() {
        Button button = new Button("XDDDD");
        signsPane.getChildren().add(button);
    }

    @Override
    public void update() {
        //nothing to do
    }
}
