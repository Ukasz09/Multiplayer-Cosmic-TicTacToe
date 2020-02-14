package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsPlayer;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IDrawingGraphic;
import javafx.scene.image.Image;

public abstract class GameBackground implements IDrawingGraphic{
    private SoundsPlayer backgroundSound;
    private boolean backgroundSoundIsPlaying;
    protected ViewManager manager;

    //----------------------------------------------------------------------------------------------------------------//
    public GameBackground(SoundsPlayer backgroundSound) {
        manager = ViewManager.getInstance();
        this.backgroundSound = backgroundSound;
        backgroundSoundIsPlaying = false;
    }

    //----------------------------------------------------------------------------------------------------------------//
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
