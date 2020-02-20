package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.pages;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsPlayer;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sounds.SoundsProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.normal.GameControlButton;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;

public class StartGamePage extends ChoosePage {
    private static final String HEADER_TEXT = "Cosmic Tic-Tac-Toe Game";
    private static final double DEFAULT_VOLUME = 0.5;
    private static final SoundsPlayer DEFAULT_MUSIC = SoundsProperties.gameBackground(DEFAULT_VOLUME);

    //----------------------------------------------------------------------------------------------------------------//
    public StartGamePage() {
        super(new ImageGameBackground(DEFAULT_BACKGROUND, DEFAULT_MUSIC), HEADER_TEXT, Orientation.VERTICAL, 0);
        addButtons();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void addButtons() {
        addStartGameButton();
        addEndGameButton();
    }

    private void addStartGameButton() {
        Button button = new GameControlButton("START GAME");
        button.setOnMouseClicked(event -> notifyObservers(EventKind.START_BUTTON_CLICKED));
        addToContentPane(button);
    }

    private void addEndGameButton() {
        Button button = new GameControlButton("END GAME");
        button.setOnMouseClicked(event -> notifyObservers(EventKind.END_GAME_BUTTON_CLICKED));
        addToContentPane(button);
    }

    @Override
    public void update() {
        //nothing to do yet
    }
}
