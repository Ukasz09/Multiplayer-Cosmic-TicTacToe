package com.github.Ukasz09.ticTacToe.ui.scenes.panes;

import com.github.Ukasz09.ticTacToe.ui.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToe.ui.sprites.ImageSprite;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;

public class DrawResultPane extends GameResultPane {
    private IDrawingGraphic drawAnimSprite;

    //----------------------------------------------------------------------------------------------------------------//
    public DrawResultPane(double width, double positionX, double positionY) {
        super(width, positionX, positionY);
        double spriteWidth = getPrefWidth() * SPRITE_WIDTH_PROPORTION;
        drawAnimSprite = new ImageSprite(spriteWidth, getSpriteHeight(), ImagesProperties.drawAnimation(), getSpriteCenterPositionX(spriteWidth), getLayoutY(), false);
    }

    //----------------------------------------------------------------------------------------------------------------//

    @Override
    public void render() {
        drawAnimSprite.render();
    }

    @Override
    public void update() {
        drawAnimSprite.update();
    }
}
