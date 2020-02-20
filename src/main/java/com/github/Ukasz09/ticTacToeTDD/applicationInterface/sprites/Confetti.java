package com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.SpritesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;
import javafx.scene.image.Image;

public class Confetti extends ImageGameBackground {
    private static final Image CONFETTI_IMAGE = ImagesProperties.confetti();

    //----------------------------------------------------------------------------------------------------------------//

    public Confetti() {
        super(CONFETTI_IMAGE);
    }
}
