package com.github.Ukasz09.ticTacToe.ui.backgrounds;

import com.github.Ukasz09.ticTacToe.ui.ViewManager;
import com.github.Ukasz09.ticTacToe.ui.sounds.SoundsPlayer;

public abstract class GameBackground implements IBackground {
    private SoundsPlayer backgroundSound;
    private boolean soundIsPlaying;
    protected ViewManager manager;

    //----------------------------------------------------------------------------------------------------------------//
    public GameBackground(SoundsPlayer backgroundSound) {
        manager = ViewManager.getInstance();
        this.backgroundSound = backgroundSound;
        soundIsPlaying = false;
    }

    //----------------------------------------------------------------------------------------------------------------//
    @Override
    public boolean playBackgroundSound() {
        if (backgroundSound == null)
            return false;
        if (soundIsPlaying)
            backgroundSound.stopSound();
        backgroundSound.playSound();
        soundIsPlaying = true;
        return true;
    }

    @Override
    public boolean stopBackgroundSound() {
        if (soundIsPlaying && backgroundSound != null) {
            backgroundSound.stopSound();
            soundIsPlaying = false;
            return true;
        }
        return false;
    }
}
