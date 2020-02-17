package com.github.Ukasz09.ticTacToeTDD.controller;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.GameView;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.GameLogic;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

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
            case START_BUTTON_CLICKED:
                gameView.showNickChoosePage();
                break;

            case CHOSEN_VALID_NAME: {
                boolean hasNextPlayerToUpdate = gameView.updateNextPlayerName();
                if (!hasNextPlayerToUpdate)
                    gameView.showAvatarChoosePage();
            }
            break;

            case AVATAR_BUTTON_CLICKED: {
                gameView.updateNextPlayerAvatar();
                boolean hasNextPlayerToUpdate = gameView.changeToNextPlayer();
                if (!hasNextPlayerToUpdate)
                    gameView.showSignChoosePage();
            }
            break;

            case SIGN_BUTTON_CLICKED: {
                gameView.updatePlayerSignSheet();
                boolean hasNextPlayerToUpdate = gameView.changeToNextPlayer();
                if (!hasNextPlayerToUpdate)
                    gameView.showBoardSizeChoosePage();
            }
            break;

            case BOARD_SIZE_CHOSEN: {
                initializeGameBoard();
                gameView.showGamePage();
            }
            break;
            case GAMEBOX_BUTTON_CLICKED: {
                Point2D coords = gameView.getLastChosenBoxCoords();
                gameView.addSignToBox((int) (coords.getX()), (int) (coords.getY()));
                gameView.changeToNextPlayer();
            }
            break;
        }
    }

    private void initializeGameBoard() {
        int boardSize = gameView.getGameBoardSize();
        ImageView avatar1 = gameView.getPlayerAvatar(0);
        ImageView avatar2 = gameView.getPlayerAvatar(1);
        ImageSheetProperty sign1 = gameView.getPlayerSignSheet(0);
        ImageSheetProperty sign2 = gameView.getPlayerSignSheet(1);
        gameView.initializeGameBoard(avatar1, avatar2, sign1, sign2, boardSize);
    }
}
