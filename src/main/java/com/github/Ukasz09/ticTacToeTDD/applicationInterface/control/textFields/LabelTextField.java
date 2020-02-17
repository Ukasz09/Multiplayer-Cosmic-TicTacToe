package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.textFields;

import javafx.geometry.Pos;
import javafx.scene.layout.Background;

public class LabelTextField extends GameTextField {

    public LabelTextField(String text, int fontSize, boolean opacity) {
        this(text, opacity);
        setDefaultTextFieldFont(DEFAULT_FONT_COLOR_CSS, fontSize);
    }

    public LabelTextField(String text, boolean opacity) {
        super(text);
        setEditable(false);
        setAlignment(Pos.CENTER);
        if (!opacity)
            setBackground(Background.EMPTY);
    }

    public void setDefaultFontColor() {
        setFontColor(DEFAULT_FONT_COLOR_CSS);
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void maximizeWidth() {
        setMinWidth(manager.getRightFrameBorder());
        setWidth(manager.getRightFrameBorder());
        setPrefWidth(manager.getRightFrameBorder());
    }
}
