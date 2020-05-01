package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.ui.sprites.ImageSprite;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImagesProperties;
import javafx.geometry.Orientation;
import javafx.scene.image.Image;

public class WaitingPage extends ChoosePage {
    private static final String LABEL_TEXT = "Other player's making decision now...";

    private ImageSprite waitingAnimation;

    //----------------------------------------------------------------------------------------------------------------//
    public WaitingPage() {
        super(StartGamePage.GAME_BACKGROUND, LABEL_TEXT, Orientation.HORIZONTAL, 0);
        initWaitingAnimation();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initWaitingAnimation() {
        double spriteWidth = manager.getRightFrameBorder() / 2;
        double spriteHeight = manager.getBottomFrameBorder() - getHeaderPaneHeight();
        Image animImage = ImagesProperties.waitingAnimation();
        double posX = getSpriteCenterPositionX(spriteWidth);
        waitingAnimation = new ImageSprite(spriteWidth, spriteHeight, animImage, posX, getHeaderPaneHeight(), false);
    }

    @Override
    public void render() {
        super.render();
        waitingAnimation.render();
    }

    @Override
    public void update() {
        //nothing to do
    }
}
