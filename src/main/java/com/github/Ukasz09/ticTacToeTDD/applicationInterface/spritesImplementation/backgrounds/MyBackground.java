package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsPlayer;
import javafx.scene.image.Image;

public abstract class MyBackground {
    private Image backgroundImage;
    private SoundsPlayer backgroundSound;
    private boolean backgroundSoundIsPlaying;
    protected ViewManager manager;

    public MyBackground(Image backgroundImage, SoundsPlayer backgroundSound) {
        this.backgroundImage = backgroundImage;
        this.backgroundSound = backgroundSound;
        manager = ViewManager.getInstance();
        backgroundSoundIsPlaying = false;
    }

    public void render() {
        drawBackground();
    }

    private void drawBackground() {
        manager.getGraphicContext().drawImage(backgroundImage, 0, 0, manager.getRightFrameBorder(), manager.getBottomFrameBorder());
    }

    public void playBackgroundSound() {
        backgroundSound.playSound();
        backgroundSoundIsPlaying = true;
    }

    public void stopBackgroundSound() {
        if (backgroundSoundIsPlaying) {
            backgroundSound.stopSound();
            backgroundSoundIsPlaying = false;
        }
    }
}
