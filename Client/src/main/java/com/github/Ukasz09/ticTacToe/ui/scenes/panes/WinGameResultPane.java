package com.github.Ukasz09.ticTacToe.ui.scenes.panes;

import com.github.Ukasz09.ticTacToe.ui.sprites.ImageSprite;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;

public class WinGameResultPane extends GameResultPane {
    private ImageSprite winSprite;
    private boolean rotated;

    //----------------------------------------------------------------------------------------------------------------//
    public WinGameResultPane(double width, double positionX, double positionY, boolean rotated) {
        super(width, positionX, positionY);
        this.rotated = rotated;
        initAnimSprite();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initAnimSprite() {
        double spriteWidth = getPrefWidth() * SPRITE_WIDTH_PROPORTION;
        double spriteHeight = getSpriteHeight();
        double spritePosY = manager.getBottomFrameBorder() - spriteHeight;
        winSprite = new ImageSprite(spriteWidth, spriteHeight, ImagesProperties.winAnimation(), getSpriteCenterPositionX(spriteWidth), spritePosY, false);
    }

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
