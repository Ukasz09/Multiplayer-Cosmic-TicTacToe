package com.github.Ukasz09.ticTacToe.ui.scenes.panes;

import com.github.Ukasz09.ticTacToe.ui.sprites.ImageSprite;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;

public class WinGameResultPane extends GameResultPane {
    private ImageSprite winSprite;
    private boolean rotated;

    //----------------------------------------------------------------------------------------------------------------//
    public WinGameResultPane(double width, double positionX, double positionY, boolean rotated) {
        super(width, positionX, positionY);
        double statueWidth = getPrefWidth() * SPRITE_WIDTH_PROPORTION;
        this.rotated = rotated;
        winSprite = new ImageSprite(statueWidth, getSpriteHeight(), ImagesProperties.winAnimation(), getSpriteCenterPositionX(statueWidth), getLayoutY(), false);
    }

    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public void render() {
        if (rotated)
            winSprite.renderRotatedSprite();
        else
            winSprite.render();
    }

    @Override
    public void update() {
        winSprite.update();
    }
}
