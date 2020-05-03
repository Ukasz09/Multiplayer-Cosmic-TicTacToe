package com.github.Ukasz09.ticTacToe.ui.scenes.panes;

import com.github.Ukasz09.ticTacToe.ui.sprites.ImageSprite;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;

public class LoseGameResultPane extends GameResultPane {
    private ImageSprite loseAnimSprite;
    private boolean rotated;

    //----------------------------------------------------------------------------------------------------------------//
    public LoseGameResultPane(double width, double positionX, double positionY, boolean rotated) {
        super(width, positionX, positionY);
        this.rotated = rotated;
        initAnimSprite();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initAnimSprite() {
        double spriteWidth = getPrefWidth() * SPRITE_WIDTH_PROPORTION;
        double spriteHeight = getSpriteHeight();
        double spritePosY = manager.getBottomFrameBorder() - spriteHeight;
        loseAnimSprite = new ImageSprite(spriteWidth, spriteHeight, ImagesProperties.loseAnimation(), getSpriteCenterPositionX(spriteWidth), spritePosY, false);
    }

    @Override
    public void render() {
        if (rotated)
            loseAnimSprite.renderRotatedSprite();
        else loseAnimSprite.render();
    }

    @Override
    public void update() {
        loseAnimSprite.update();
    }
}
