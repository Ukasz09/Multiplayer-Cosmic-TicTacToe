package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsPlayer;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImagesProperties;
import javafx.scene.image.Image;

public class GameBackground extends MyBackground {
    private static final double DEFAULT_VOLUME = 0.5;
    private static final Image DEFAULT_IMAGE = ImagesProperties.woodBackground1();
    private static final SoundsPlayer DEFAULT_MUSIC = SoundsProperties.gameBackground(DEFAULT_VOLUME);

    public GameBackground() {
        super(DEFAULT_IMAGE, DEFAULT_MUSIC);
    }
}
