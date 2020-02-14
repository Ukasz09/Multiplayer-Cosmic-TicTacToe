package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.buttons;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.FontProperties;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.effect.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class GameControlButton extends Button {
    private static final double BUTTON_WIDTH_PROPORTION = 35 / 192d;
    private static final double BUTTON_HEIGHT_PROPORTION = 10 / 108d;
    private static final double FONT_SIZE_PROPORTION = 4 / 192d;
    private static final Paint FONT_COLOR = Color.LIGHTGRAY;
    private static final Color BACKGROUND_COLOR = new Color(0.23, 0.23, 0.23, 0.5);
    private static final double CORNER_RADIUS = 25;
    private static final Insets INSETS = Insets.EMPTY;
    private static final Effect MOUSE_ENTERED_EFFECT = new InnerShadow(1, Color.PURPLE);

    private ViewManager manager;

    //----------------------------------------------------------------------------------------------------------------//
    public GameControlButton(String text) {
        super(text);
        manager = ViewManager.getInstance();
        initializeButton();
    }

    private void initializeButton() {
        setDefaultSize();
        setDefaultAppearance();
        setDefaultEvents();
        setDefaultFont();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setDefaultSize() {
        double btnWidth = manager.getScaledWidth(BUTTON_WIDTH_PROPORTION);
        double btnHeight = manager.getScaledHeight(BUTTON_HEIGHT_PROPORTION);
        setMinSize(btnWidth, btnHeight);
        setPrefSize(btnWidth, btnHeight);
    }

    private void setDefaultAppearance() {
        setBackground(new Background(new BackgroundFill(BACKGROUND_COLOR, new CornerRadii(CORNER_RADIUS), INSETS)));
    }

    public void setDefaultFont() {
        int fontSize = (int) (manager.getScaledWidth(FONT_SIZE_PROPORTION));
        setFont(FontProperties.chargenRegularFont(fontSize));
        setTextFill(FONT_COLOR);
    }

    private void setDefaultEvents() {
        setOnMouseEntered(event -> setEffect(MOUSE_ENTERED_EFFECT));
        setOnMouseExited(event -> setEffect(null));
    }
}


