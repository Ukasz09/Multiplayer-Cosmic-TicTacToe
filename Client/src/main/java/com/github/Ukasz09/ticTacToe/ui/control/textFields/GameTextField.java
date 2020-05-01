package com.github.Ukasz09.ticTacToe.ui.control.textFields;

import com.github.Ukasz09.ticTacToe.ui.ViewManager;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.ButtonsProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.FontProperties;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameTextField extends TextField {
    private static final double WIDTH_PROPORTION = 35 / 192d;
    private static final double HEIGHT_PROPORTION = 10 / 108d;
    private static final double FONT_SIZE_PROPORTION = 3 / 192d;

    protected ViewManager manager;

    //----------------------------------------------------------------------------------------------------------------//
    public GameTextField(boolean opacity) {
        this("", opacity);
    }

    public GameTextField(String text, boolean opacity) {
        manager = ViewManager.getInstance();
        initializeTextField(opacity);
        setText(text);
        setEditable(false);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initializeTextField(boolean opacity) {
        setDefaultAppearance(opacity);
        setDefaultSize();
    }

    private void setDefaultAppearance(boolean opacity) {
        if (opacity) {
            Color bgColor = ButtonsProperties.defaultBackgroundColor();
            setBackground(new Background(new BackgroundFill(bgColor, new CornerRadii(ButtonsProperties.DEFAULT_CORNER_RADIUS), ButtonsProperties.DEFAULT_INSETS)));
        } else setBackground(Background.EMPTY);
        int fontSize = (int) (manager.getScaledWidth(FONT_SIZE_PROPORTION));
        setDefaultTextFieldFont(ButtonsProperties.DEFAULT_FONT_COLOR_CSS, fontSize);
        setFocusTraversable(false);
        setAlignment(Pos.CENTER);
    }

    protected void setDefaultTextFieldFont(String fontColorCSS, int fontSize) {
        Font font = FontProperties.chargenRegularFont(fontSize);
        setFontColor(fontColorCSS);
        setFont(font);
    }

    public void setFontColor(String fontColorCSS) {
        setStyle("-fx-text-inner-color: " + fontColorCSS + ";");
    }

    private void setDefaultSize() {
        double textFieldWidth = manager.getScaledWidth(WIDTH_PROPORTION);
        double textFieldHeight = manager.getScaledHeight(HEIGHT_PROPORTION);
        setMinSize(textFieldWidth, textFieldHeight);
        setPrefSize(textFieldWidth, textFieldHeight);
    }

    public void setDefaultFontColor() {
        setFontColor(ButtonsProperties.DEFAULT_FONT_COLOR_CSS);
    }
}
