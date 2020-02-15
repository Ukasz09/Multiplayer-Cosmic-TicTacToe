package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsPlayer;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.GameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.buttons.GameControlButton;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StartGamePage extends ChoosePage {
    private static final String HEADER_TEXT = "Cosmic Tic-Tac-Toe Game";
    private static final Image BACKGROUND_IMAGE = ImagesProperties.startGameBackground();
    private static final double DEFAULT_VOLUME = 0.5;
    private static final SoundsPlayer DEFAULT_MUSIC = SoundsProperties.gameBackground(DEFAULT_VOLUME);

    //----------------------------------------------------------------------------------------------------------------//
    public StartGamePage() {
        super(new ImageGameBackground(BACKGROUND_IMAGE, DEFAULT_MUSIC), HEADER_TEXT);
        addButtons();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void addButtons() {
        addStartGameButton();
    }

    private void addStartGameButton() {
        Button button = new GameControlButton("START GAME");
        button.setOnMouseClicked(event -> notifyObservers(EventKind.START_BUTTON_CLICKED));
        getContentPane().getChildren().add(button);
    }

    @Override
    public void update() {
        //nothing to do yet
    }
}
