package com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsPlayer;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IDrawingGraphic;

public abstract class GameBackground implements IBackground {
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
    @Override
    public boolean playBackgroundSound() {
        if (backgroundSound == null)
            return false;
        backgroundSound.playSound();
        backgroundSoundIsPlaying = true;
        return true;
    }

    @Override
    public boolean stopBackgroundSound() {
        if (backgroundSoundIsPlaying && backgroundSound != null) {
            backgroundSound.stopSound();
            backgroundSoundIsPlaying = false;
            return true;
        }
        return false;
    }
}
