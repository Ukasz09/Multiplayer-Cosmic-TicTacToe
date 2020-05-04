package com.github.Ukasz09.ticTacToe.ui.scenes.panes;

import com.github.Ukasz09.ticTacToe.ui.sprites.IDrawingGraphic;

public abstract class GameResultPane extends CenteredPane implements IDrawingGraphic {
    protected static final double SPRITE_WIDTH_PROPORTION = 7 / 8d;
    protected static final double SPRITE_HEIGHT_PROPORTION = 7 / 8d;

    public GameResultPane(double width, double positionX, double positionY) {
        super();
        setUpPane(width, positionX, positionY);
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void setUpPane(double width, double positionX, double positionY) {
        setPageWidth(width);
        setLayoutX(positionX);
        setLayoutY(positionY);
    }

    private void setPageWidth(double width) {
        setWidth(width);
        setMinWidth(width);
        setPrefWidth(width);
    }

    protected double getSpriteHeight() {
        return (manager.getBottomFrameBorder() - getLayoutY()) * SPRITE_HEIGHT_PROPORTION;
    }

    protected double getSpriteCenterPositionX(double statueWidth) {
        return (getLayoutX() + getWidth() / 2 - statueWidth / 2);
    }
}
