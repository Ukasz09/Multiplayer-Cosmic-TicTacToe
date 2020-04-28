package com.github.Ukasz09.ticTacToe.ui.control.buttons.normal;

import com.github.Ukasz09.ticTacToe.ui.sprites.properties.FontProperties;

public class GameControlButton extends GameButton {
    private static final double BUTTON_WIDTH_PROPORTION = 35 / 192d;
    private static final double BUTTON_HEIGHT_PROPORTION = 10 / 108d;
    private static final double FONT_SIZE_PROPORTION = 4 / 192d;

    //----------------------------------------------------------------------------------------------------------------//
    public GameControlButton(String text) {
        super();
        initializeButton(text);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initializeButton(String text) {
        setText(text);
        setDefaultSize();
        setDefaultEvents();
        setDefaultFont();
    }

    private void setDefaultSize() {
        double btnWidth = manager.getScaledWidth(BUTTON_WIDTH_PROPORTION);
        double btnHeight = manager.getScaledHeight(BUTTON_HEIGHT_PROPORTION);
        setMinSize(btnWidth, btnHeight);
        setPrefSize(btnWidth, btnHeight);
    }

    private void setDefaultEvents() {
        setOnMouseEntered(event -> setEffect(DEFAULT_MOUSE_ENTERED_EFFECT));
        setOnMouseExited(event -> setEffect(null));
    }

    private void setDefaultFont() {
        int fontSize = (int) (manager.getScaledWidth(FONT_SIZE_PROPORTION));
        setFont(FontProperties.chargenRegularFont(fontSize));
        setTextFill(DEFAULT_FONT_COLOR);
    }
}


