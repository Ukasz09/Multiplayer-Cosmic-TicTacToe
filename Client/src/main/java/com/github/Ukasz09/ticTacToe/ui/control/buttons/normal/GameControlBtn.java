package com.github.Ukasz09.ticTacToe.ui.control.buttons.normal;

import com.github.Ukasz09.ticTacToe.ui.control.buttons.ButtonsProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.FontProperties;

public class GameControlBtn extends GameBtn {
    public static final double BTN_WIDTH_PROP = 35 / 192d;
    private static final double BUTTON_HEIGHT_PROPORTION = 10 / 108d;
    private static final double FONT_SIZE_PROPORTION = 4 / 192d;

    //----------------------------------------------------------------------------------------------------------------//
    public GameControlBtn(String text) {
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
        double btnWidth = manager.getScaledWidth(BTN_WIDTH_PROP);
        double btnHeight = manager.getScaledHeight(BUTTON_HEIGHT_PROPORTION);
        setMinSize(btnWidth, btnHeight);
        setPrefSize(btnWidth, btnHeight);
    }

    private void setDefaultEvents() {
        setOnMouseEntered(event -> setEffect(ButtonsProperties.mouseEnteredEffect()));
        setOnMouseExited(event -> setEffect(null));
    }

    private void setDefaultFont() {
        int fontSize = (int) (manager.getScaledWidth(FONT_SIZE_PROPORTION));
        setFont(FontProperties.chargenRegularFont(fontSize));
        setTextFill(ButtonsProperties.DEFAULT_FONT_COLOR);
    }
}


