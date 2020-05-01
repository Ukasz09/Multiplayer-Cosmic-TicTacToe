package com.github.Ukasz09.ticTacToe.ui.sprites;

import javafx.scene.image.Image;

public class ImageSprite extends SpriteWithEventHandler {
    private Image spriteImage;

    //-----------------------------------------------------------------------------------------------------------------//
    public ImageSprite(double width, double height, Image spriteImage, double positionX, double positionY, boolean withImageViewInRoot) {
        super(width, height, positionX, positionY, withImageViewInRoot);
        this.spriteImage = spriteImage;
    }

    //-----------------------------------------------------------------------------------------------------------------//
    @Override
    public void render() {
        renderSprite();
    }

    private void renderSprite() {
        manager.getGraphicContext().drawImage(spriteImage, positionX, positionY, width, height);
    }
}
