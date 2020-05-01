package com.github.Ukasz09.ticTacToe.ui.scenes.panes;

import com.github.Ukasz09.ticTacToe.ui.sprites.ImageSprite;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;

public class LoseGameResultPane extends GameResultPane {
    private ImageSprite loseAnimSprite;
    private boolean rotated;

    //----------------------------------------------------------------------------------------------------------------//
    public LoseGameResultPane(double width, double positionX, double positionY, boolean rotated) {
        super(width, positionX, positionY);
        double spriteWidth = getPrefWidth() * SPRITE_WIDTH_PROPORTION;
        this.rotated = rotated;
        loseAnimSprite = new ImageSprite(spriteWidth, getSpriteHeight(), ImagesProperties.loseAnimation(), getSpriteCenterPositionX(spriteWidth), getLayoutY(), false);
    }

    //----------------------------------------------------------------------------------------------------------------//
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
