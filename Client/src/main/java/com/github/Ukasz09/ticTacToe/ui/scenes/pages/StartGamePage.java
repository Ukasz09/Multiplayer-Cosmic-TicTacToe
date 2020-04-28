package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.ui.backgrounds.GameBackground;
import com.github.Ukasz09.ticTacToe.ui.sounds.SoundsPlayer;
import com.github.Ukasz09.ticTacToe.ui.sounds.SoundsProperties;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.ui.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToe.ui.control.buttons.normal.GameControlButton;
import javafx.geometry.Orientation;
import javafx.scene.control.Button;

public class StartGamePage extends ChoosePage {
    private static final String HEADER_TEXT = "Cosmic Tic-Tac-Toe Game";
    private static final double DEFAULT_VOLUME = 0.8;
    private static final SoundsPlayer DEFAULT_MUSIC = SoundsProperties.gameBackground(DEFAULT_VOLUME);

    public static final GameBackground GAME_BACKGROUND = new ImageGameBackground(DEFAULT_BACKGROUND, DEFAULT_MUSIC);

    //----------------------------------------------------------------------------------------------------------------//
    public StartGamePage() {
        super(GAME_BACKGROUND, HEADER_TEXT, Orientation.VERTICAL, 0);
        addButtons();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void addButtons() {
        addStartGameButton();
        addEndGameButton();
    }

    private void addStartGameButton() {
        Button button = new GameControlButton("START GAME");
        button.setOnMouseClicked(event -> notifyObservers(GuiEvents.START_BTN_CLICKED));
        addToContentPane(button);
    }

    private void addEndGameButton() {
        Button button = new GameControlButton("END GAME");
        button.setOnMouseClicked(event -> notifyObservers(GuiEvents.END_GAME_BTN_CLICKED));
        addToContentPane(button);
    }

    @Override
    public void update() {
        //nothing to do yet
    }
}
