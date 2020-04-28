package com.github.Ukasz09.ticTacToe.ui.backgrounds;

import com.github.Ukasz09.ticTacToe.ui.sounds.SoundsPlayer;
import javafx.scene.image.Image;

public class ImageGameBackground extends GameBackground {
    private Image backgroundImage;

    //----------------------------------------------------------------------------------------------------------------//
    public ImageGameBackground(Image backgroundImage, SoundsPlayer backgroundSound) {
        super(backgroundSound);
        this.backgroundImage = backgroundImage;
    }

    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public void render() {
        drawBackground();
    }

    @Override
    public void update() {
        //nothing to do
    }

    private void drawBackground() {
        manager.getGraphicContext().drawImage(backgroundImage, 0, 0, manager.getRightFrameBorder(), manager.getBottomFrameBorder());
    }
}
