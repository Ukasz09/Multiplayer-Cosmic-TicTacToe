package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsPlayer;
import javafx.scene.image.Image;

public class ImageGameBackground extends GameBackground {
    private Image backgroundImage;

    //----------------------------------------------------------------------------------------------------------------//
    public ImageGameBackground(Image backgroundImage) {
        super(null);
        this.backgroundImage = backgroundImage;
    }

    public ImageGameBackground(Image backgroundImage, SoundsPlayer backgroundSound) {
        super(backgroundSound);
        this.backgroundImage = backgroundImage;
    }

    //----------------------------------------------------------------------------------------------------------------//
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