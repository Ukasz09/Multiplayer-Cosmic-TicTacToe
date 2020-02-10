package com.github.Ukasz09.ticTacToeTDD.controller;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.GameView;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.buttons.SignButton;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.GameLogic;

public class GameController implements IEventKindObserver {
    private GameView gameView;
    private GameLogic gameLogic;

    public GameController(GameView gameView, GameLogic gameLogic) {
        this.gameView = gameView;
        this.gameLogic = gameLogic;
    }

    public void startGame() {
        gameView.startGame(gameLogic.getPlayersQty());
        gameView.getPagesManager().attachObserver(this);
    }

    @Override
    public void updateObserver(EventKind eventKind) {
        System.out.println("CLICKED");
        switch (eventKind) {
            case SIGN_BUTTON_1_CLICKED:
            case SIGN_BUTTON_2_CLICKED:
            case SIGN_BUTTON_3_CLICKED:
            case SIGN_BUTTON_4_CLICKED:
            case SIGN_BUTTON_5_CLICKED: {
                gameView.getPagesManager().showGamePage();
                gameView.updateNextPlayerSignSheet(gameView.getPagesManager().getSignSheetFromButton(eventKind));
            }
            break;
        }
    }
}
