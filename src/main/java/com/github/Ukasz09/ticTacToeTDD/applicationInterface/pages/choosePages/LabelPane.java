package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.textFields.LabelTextField;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.FontProperties;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.awt.*;

public class LabelPane extends CenteredPane {
    private static final double HEIGHT_PROPORTION = 1 / 5d;
    public static final double FONT_SIZE_PROPORTION = 6 / 108d;

    private TextField textField;

    //-----------------------------------------------------------------------------------------------------------------//
    public LabelPane(String text) {
        initializePane(text);
        getChildren().add(textField);
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void initializePane(String text) {
        setDefaultPaneSize();
        LabelTextField labelField = new LabelTextField(text, (int) (manager.getScaledHeight(FONT_SIZE_PROPORTION)), false);
        labelField.maximizeWidth();
        textField = labelField;
    }

    private void setDefaultPaneSize() {
        setMinHeight(manager.getScaledHeight(HEIGHT_PROPORTION));
        setMinWidth(manager.getRightFrameBorder());
    }

    public void setLabelText(String text) {
        textField.setText(text);
    }
}
