package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.control.buttons;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public abstract class GameButton extends Button implements IGameButtonProperties {
    protected ViewManager manager;

    //----------------------------------------------------------------------------------------------------------------//
    public GameButton() {
        manager = ViewManager.getInstance();
        setDefaultAppearance();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setDefaultAppearance() {
        setBackground(new Background(new BackgroundFill(DEFAULT_BACKGROUND_COLOR, new CornerRadii(DEFAULT_CORNER_RADIUS), DEFAULT_INSETS)));
    }
}
