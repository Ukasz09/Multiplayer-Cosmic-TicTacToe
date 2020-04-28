package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons;

import javafx.geometry.Insets;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public interface IGameButtonProperties {
    //colors
    Color DEFAULT_BACKGROUND_COLOR = new Color(0.23, 0.23, 0.23, 0.4);
    Paint DEFAULT_FONT_COLOR = Color.LIGHTGRAY;
    String DEFAULT_FONT_COLOR_CSS = "lightgray";

    //effects
    Effect DEFAULT_MOUSE_ENTERED_EFFECT = new InnerShadow(1, Color.PURPLE);
    Effect BUTTON_EXITED_EFFECT = new Lighting(new Light.Distant(10, 15, Color.DARKSLATEBLUE));

    //others
    double DEFAULT_CORNER_RADIUS = 25;
    Insets DEFAULT_INSETS = Insets.EMPTY;
}
