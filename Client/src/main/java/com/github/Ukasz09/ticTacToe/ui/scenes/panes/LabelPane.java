package com.github.Ukasz09.ticTacToe.ui.scenes.panes;

import com.github.Ukasz09.ticTacToe.ui.control.textFields.HeaderTextField;
import javafx.scene.control.TextField;

public class LabelPane extends CenteredPane {
    private static final double HEIGHT_PROPORTION = 1 / 5d;
    public static final double FONT_SIZE_PROPORTION = 6 / 108d;

    private TextField textField;

    //-----------------------------------------------------------------------------------------------------------------//
    public LabelPane(String text) {
        this(text, FONT_SIZE_PROPORTION);
    }

    public LabelPane(String text, double fontSizeProportion) {
        initializePane(text, fontSizeProportion, false);
        getChildren().add(textField);
    }

    public LabelPane(String text, double fontSizeProportion, boolean sizeFitToFont) {
        initializePane(text, fontSizeProportion, sizeFitToFont);
        getChildren().add(textField);
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void initializePane(String text, double fontSizeProportion, boolean sizeFitToFont) {
        if (!sizeFitToFont)
            setDefaultPaneSize();
        textField = new HeaderTextField(text, (int) (manager.getScaledHeight(fontSizeProportion)), false);
    }

    private void setDefaultPaneSize() {
        setMinHeight(manager.getScaledHeight(HEIGHT_PROPORTION));
        setMinWidth(manager.getRightFrameBorder());
    }

    public void setLabelText(String text) {
        textField.setText(text);
    }
}
