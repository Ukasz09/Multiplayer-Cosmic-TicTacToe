package com.github.Ukasz09.ticTacToe.ui.sounds;

public class SoundsProperties {
    public static SoundsPlayer gameBackground(double volume) {
        String musicPath = "sounds/climaticBackground1.mp3";
        return new SoundsPlayer(musicPath, volume, true);
    }

    public static SoundsPlayer drawEffect(double volume) {
        String musicPath = "sounds/sadTrombone.mp3";
        return new SoundsPlayer(musicPath, volume, false);
    }

    public static SoundsPlayer winEffect(double volume) {
        String musicPath = "sounds/applause.mp3";
        return new SoundsPlayer(musicPath, volume, false);
    }
}
