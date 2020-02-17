package com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons;

import javafx.geometry.Insets;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public interface IGameButtonProperties {
    Color DEFAULT_BACKGROUND_COLOR = new Color(0.23, 0.23, 0.23, 0.4);
    //    Color DEFAULT_BACKGROUND_COLOR = new Color(0.7, 0.1, 0.9, 0.1);
    double DEFAULT_CORNER_RADIUS = 25;
    Insets DEFAULT_INSETS = Insets.EMPTY;
    Paint DEFAULT_FONT_COLOR = Color.LIGHTGRAY;
    String DEFAULT_FONT_COLOR_CSS = "lightgray";
    Effect DEFAULT_MOUSE_ENTERED_EFFECT = new InnerShadow(1, Color.PURPLE);
}
