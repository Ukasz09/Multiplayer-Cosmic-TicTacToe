package com.github.Ukasz09.ticTacToe.ui.scenes.panes;

import com.github.Ukasz09.ticTacToe.ui.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToe.ui.sprites.gameSprites.OscarStatue;

public class GameResultPane extends CenteredPane implements IDrawingGraphic {
    private static final double SPRITE_WIDTH_PROPORTION = 7 / 8d;
    private static final double SPRITE_HEIGHT_PROPORTION = 7 / 8d;

    private IDrawingGraphic spriteToRender = null;

    //----------------------------------------------------------------------------------------------------------------//
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

    public void addOscarStatue() {
        double statueWidth = getPrefWidth() * SPRITE_WIDTH_PROPORTION;
        spriteToRender = new OscarStatue(statueWidth, getSpriteHeight(), getSpriteCenterPositionX(statueWidth), getLayoutY());
    }

    private double getSpriteHeight() {
        return (manager.getBottomFrameBorder() - getLayoutY()) * SPRITE_HEIGHT_PROPORTION;
    }

    private double getSpriteCenterPositionX(double statueWidth) {
        return (getLayoutX() + getWidth() / 2 - statueWidth / 2);
    }

    //todo: zmienic by nie bylo ifow
    @Override
    public void render() {
        if (spriteToRender != null)
            spriteToRender.render();
    }

    @Override
    public void update() {
        if (spriteToRender != null)
            spriteToRender.update();
    }
}
