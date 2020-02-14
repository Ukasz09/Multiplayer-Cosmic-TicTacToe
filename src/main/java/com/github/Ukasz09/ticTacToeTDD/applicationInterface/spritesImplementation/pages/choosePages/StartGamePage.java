package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsPlayer;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.buttons.GameControlButton;
import javafx.scene.image.Image;

public class StartGamePage extends ChoosePage {
    private static final String HEADER_TEXT = "Cosmic Tic-Tac-Toe Game";
    private static final Image BACKGROUND_IMAGE = ImagesProperties.startGameBackground();
    private static final double DEFAULT_VOLUME = 0.5;
    private static final SoundsPlayer DEFAULT_MUSIC = SoundsProperties.gameBackground(DEFAULT_VOLUME);

    //todo: zrobic inaczej background, wyrzucic stylizacje button do warstwy wyzej
    public StartGamePage() {
        super(new MyBackground(BACKGROUND_IMAGE, DEFAULT_MUSIC), HEADER_TEXT);
        addButtons();
    }

    private void addButtons() {
        getContentPane().getChildren().add(new GameControlButton("START GAME"));
    }

    @Override
    public void update() {
        //nothing to do yet
    }
}
