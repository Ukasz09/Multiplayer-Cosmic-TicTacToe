package com.github.Ukasz09.ticTacToe.ui.control.buttons.normal;

import com.github.Ukasz09.ticTacToe.ui.ViewManager;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.IGameBtnProperties;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;

public abstract class GameBtn extends Button implements IGameBtnProperties {
    protected ViewManager manager;

    //----------------------------------------------------------------------------------------------------------------//
    public GameBtn() {
        manager = ViewManager.getInstance();
        setDefaultAppearance();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setDefaultAppearance() {
        setBackground(new Background(new BackgroundFill(DEFAULT_BACKGROUND_COLOR, new CornerRadii(DEFAULT_CORNER_RADIUS), DEFAULT_INSETS)));
    }
}
