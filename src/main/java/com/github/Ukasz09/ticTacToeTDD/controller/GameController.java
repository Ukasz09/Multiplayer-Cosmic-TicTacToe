package com.github.Ukasz09.ticTacToeTDD.controller;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.GameView;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.observerPattern.IEventKindObserver;
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
        gameView.attachObserverToPagesManager(this);
    }

    @Override
    public void updateObserver(EventKind eventKind) {
        switch (eventKind) {
            case CHOSEN_VALID_NAME: {
                boolean hasNextPlayerToUpdate = gameView.updateNextPlayerName();
                if (!hasNextPlayerToUpdate)
                    gameView.showAvatarChoosePage();
            }
            break;


            case AVATAR_BUTTON_CLICKED: {
                boolean hasNextPlayerToUpdate = gameView.updateNextPlayerAvatar();
                if (!hasNextPlayerToUpdate)
                    gameView.showSignChoosePage();
            }
            break;

            case SIGN_BUTTON_CLICKED: {
                boolean hasNextPlayerToUpdate = gameView.updateNextPlayerSignSheet();
                if (!hasNextPlayerToUpdate)
                    gameView.showGamePage();
            }
            break;
        }
    }
}
