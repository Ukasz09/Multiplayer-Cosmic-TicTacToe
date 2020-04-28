package com.github.Ukasz09.ticTacToe.ui.control.textFields;

public class HeaderTextField extends GameTextField {

    public HeaderTextField(String text, int fontSize, boolean opacity) {
        this(text, opacity);
        setDefaultTextFieldFont(DEFAULT_FONT_COLOR_CSS, fontSize);
    }

    public HeaderTextField(String text, boolean opacity) {
        super(text, opacity);
        maximizeWidth();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void maximizeWidth() {
        setMinWidth(manager.getRightFrameBorder());
        setWidth(manager.getRightFrameBorder());
        setPrefWidth(manager.getRightFrameBorder());
    }
}
