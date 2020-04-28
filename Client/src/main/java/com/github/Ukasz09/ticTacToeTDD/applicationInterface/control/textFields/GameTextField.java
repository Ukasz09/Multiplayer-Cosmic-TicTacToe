package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.textFields;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.FontProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.IGameButtonProperties;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class GameTextField extends TextField implements IGameButtonProperties {
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
        if (opacity)
            setBackground(new Background(new BackgroundFill(DEFAULT_BACKGROUND_COLOR, new CornerRadii(DEFAULT_CORNER_RADIUS), DEFAULT_INSETS)));
        else setBackground(Background.EMPTY);
        int fontSize = (int) (manager.getScaledWidth(FONT_SIZE_PROPORTION));
        setDefaultTextFieldFont(DEFAULT_FONT_COLOR_CSS, fontSize);
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
        setFontColor(DEFAULT_FONT_COLOR_CSS);
    }
}
