package com.github.Ukasz09.ticTacToeTDD.controller;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.GameView;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
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
        if (eventKind == EventKind.SIGN_BUTTON_WAS_CHOSEN)
            System.out.println("BUTTON CHOOSEN"); //todo: tmp
    }
}
