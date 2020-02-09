package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.panels;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.FontProperties;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

public class LabelPane extends CenteredPane {
    private static final double LABEL_HEIGHT_TO_SCREEN_PROPORTION = 1 / 7d;
    private static final double FONT_SIZE_TO_SCREEN_PROPORTION = 8 / 108d;
    private static final String FONT_COLOR = "lightgray";

    private final Font font = FontProperties.chargenRegularFont((int) (FONT_SIZE_TO_SCREEN_PROPORTION * manager.getBottomFrameBorder()));
    private TextField textField;

    public LabelPane(String text) {
        super();
        initializePane(text);
        getChildren().add(textField);
    }

    private void initializePane(String text) {
        setProperPaneSize();
        initializeTextField(text);
    }

    private void setProperPaneSize() {
        setMinHeight(getHeightAfterScaling(LABEL_HEIGHT_TO_SCREEN_PROPORTION));
        setMinWidth(manager.getRightFrameBorder());
    }

    private void initializeTextField(String text) {
        textField = new TextField(text);
        textField.setEditable(false);
        textField.setMinWidth(getMinWidth());
        textField.setMinHeight(getMinHeight());
        textField.setAlignment(Pos.CENTER);
        textField.setBackground(Background.EMPTY);
        setTextFieldFont();
    }

    private void setTextFieldFont() {
        textField.setStyle("-fx-text-inner-color: " + FONT_COLOR + ";");
        textField.setFont(font);
    }
}
