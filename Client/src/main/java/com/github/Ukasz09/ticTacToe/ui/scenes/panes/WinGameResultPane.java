package com.github.Ukasz09.ticTacToe.ui.scenes.panes;

import com.github.Ukasz09.ticTacToe.ui.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToe.ui.sprites.gameSprites.OscarStatue;

public class WinGameResultPane extends GameResultPane {
    private IDrawingGraphic oscarStatue;

    public WinGameResultPane(double width, double positionX, double positionY) {
        super(width, positionX, positionY);
        double statueWidth = getPrefWidth() * SPRITE_WIDTH_PROPORTION;
        oscarStatue = new OscarStatue(statueWidth, getSpriteHeight(), getSpriteCenterPositionX(statueWidth), getLayoutY());
    }

    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public void render() {
        oscarStatue.render();
    }

    @Override
    public void update() {
        oscarStatue.update();
    }
}
