package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds;

public class SoundsProperties {
    public static SoundsPlayer gameBackground(double volume) {
        String musicPath = "/sounds/climaticBackground1.mp3";
        return new SoundsPlayer(musicPath, volume, true);
    }
}
