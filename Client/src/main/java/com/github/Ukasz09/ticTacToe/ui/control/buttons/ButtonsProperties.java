package com.github.Ukasz09.ticTacToe.ui.control.buttons;

import javafx.geometry.Insets;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

public class ButtonsProperties {

    private static Color defaultBackgroundColor;
    public static final Paint DEFAULT_FONT_COLOR = Color.LIGHTGRAY;
    public static final String DEFAULT_FONT_COLOR_CSS = "lightgray";

    public static Color defaultBackgroundColor() {
        if (defaultBackgroundColor == null)
            defaultBackgroundColor = new Color(0.23, 0.23, 0.23, 0.4);
        return defaultBackgroundColor;
    }

    //----------------------------------------------------------------------------------------------------------------//
    private static Effect mouseEnteredEffect;
    private static Effect buttonExitedEffect;
    private static Effect signDisableEffect;


    public static Effect mouseEnteredEffect() {
        if (mouseEnteredEffect == null)
            mouseEnteredEffect = new InnerShadow(1, Color.PURPLE);
        return mouseEnteredEffect;
    }

    public static Effect buttonExitedEffect() {
        if (buttonExitedEffect == null)
            buttonExitedEffect = new Lighting(new Light.Distant(10, 15, Color.DARKSLATEBLUE));
        return buttonExitedEffect;
    }

    public static Effect signDisableEffect() {
        if (signDisableEffect == null)
            signDisableEffect = new Lighting(new Light.Distant(10, 10, Color.GRAY));
        return signDisableEffect;
    }

    //----------------------------------------------------------------------------------------------------------------//
    public static final double DEFAULT_CORNER_RADIUS = 25;
    public static final Insets DEFAULT_INSETS = Insets.EMPTY;
}
