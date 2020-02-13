package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsPlayer;
import javafx.scene.image.Image;

public class MyBackground {
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

    public boolean playBackgroundSound() {
        if (backgroundSound == null)
            return false;
        backgroundSound.playSound();
        backgroundSoundIsPlaying = true;
        return true;
    }

    public boolean stopBackgroundSound() {
        if (backgroundSoundIsPlaying && backgroundSound != null) {
            backgroundSound.stopSound();
            backgroundSoundIsPlaying = false;
            return true;
        }
        return false;
    }
}
