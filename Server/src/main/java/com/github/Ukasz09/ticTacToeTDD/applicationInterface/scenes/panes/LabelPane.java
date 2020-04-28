package com.github.Ukasz09.ticTacToeTDD.applicationInterface.scenes.panes;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.textFields.HeaderTextField;
import javafx.scene.control.TextField;

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
        HeaderTextField labelField = new HeaderTextField(text, (int) (manager.getScaledHeight(FONT_SIZE_PROPORTION)), false);
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
