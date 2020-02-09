package com.github.Ukasz09.ticTacToeTDD.controller;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.GameView;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.Logger;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.GameLogic;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.TicTacToeExceptions;

public class GameController implements IEventKindObserver {
    private GameView gameView;
    private GameLogic gameLogic;

    public GameController(GameView gameView, GameLogic gameLogic) {
        this.gameView = gameView;
        this.gameLogic = gameLogic;
    }

    public void startGame() {
        gameView.startGame();
        gameView.attachObserver(this);
    }

    @Override
    public void updateObserver(EventKind eventKind) {
        switch (eventKind) {
            case SIGN_BUTTON_1_CLICKED:
            case SIGN_BUTTON_2_CLICKED:
            case SIGN_BUTTON_3_CLICKED:
            case SIGN_BUTTON_4_CLICKED:
            case SIGN_BUTTON_5_CLICKED:
                setPlayerSignSprite(gameView.getSignButton(eventKind).getSpriteSheetProperty());
                break;
        }
    }

    private void setPlayerSignSprite(ImageSheetProperty sheetProperty) {
        //todo
    }
}
