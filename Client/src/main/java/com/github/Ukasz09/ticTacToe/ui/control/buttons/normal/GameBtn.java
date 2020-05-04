package com.github.Ukasz09.ticTacToe.ui.control.buttons.normal;

import com.github.Ukasz09.ticTacToe.ui.ViewManager;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.ButtonsProperties;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public abstract class GameBtn extends Button {
    protected ViewManager manager;

    //----------------------------------------------------------------------------------------------------------------//
    public GameBtn() {
        manager = ViewManager.getInstance();
        setDefaultAppearance();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setDefaultAppearance() {
        setBackground(new Background(new BackgroundFill(ButtonsProperties.defaultBackgroundColor(), new CornerRadii(ButtonsProperties.DEFAULT_CORNER_RADIUS), ButtonsProperties.DEFAULT_INSETS)));
    }
}
