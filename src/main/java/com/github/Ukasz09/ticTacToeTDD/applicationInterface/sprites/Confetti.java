package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;
import javafx.scene.image.Image;

public class Confetti extends ImageSprite {
    private static final Image CONFETTI_IMAGE = ImagesProperties.confetti();

    //----------------------------------------------------------------------------------------------------------------//
    public Confetti(double width, double height, double positionX, double positionY) {
        super(width, height, CONFETTI_IMAGE, positionX, positionY);
    }
}
