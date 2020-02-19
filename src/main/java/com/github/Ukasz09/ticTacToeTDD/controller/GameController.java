package com.github.Ukasz09.ticTacToeTDD.controller;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.GameView;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.GameLogic;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.GameResult;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.IncorrectBoardSizeException;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.IncorrectFieldException;
import javafx.geometry.Point2D;

import java.awt.*;

public class GameController implements IEventKindObserver {
    private GameView gameView;
    private GameLogic gameLogic;
    private ViewManager manager;

    //----------------------------------------------------------------------------------------------------------------//
    public GameController(GameView gameView, GameLogic gameLogic) {
        this.gameView = gameView;
        this.gameLogic = gameLogic;
        this.manager = ViewManager.getInstance();
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void startGame() {
        gameView.startGame(gameLogic.getPlayersQty());
        gameView.attachObserverToPagesManager(this);
    }

    @Override
    public void updateObserver(EventKind eventKind) {
        switch (eventKind) {
            case START_BUTTON_CLICKED: {
                clearLevel();
                resetActualPlayerID();
                gameView.changeSceneToNewNickChoosePage();
            }
            break;

            case CHOSEN_VALID_NAME: {
                boolean hasNextPlayerToUpdate = gameView.updateNextPlayerName();
                if (!hasNextPlayerToUpdate) {
                    clearLevel();
                    gameView.changeSceneToNewAvatarChoosePage();
                }
            }
            break;

            case AVATAR_BUTTON_CLICKED: {
                gameView.updateNextPlayerAvatar();
                boolean hasNextPlayerToUpdate = gameView.changeToNextPlayer();
                if (!hasNextPlayerToUpdate) {
                    clearLevel();
                    gameView.changeSceneToNewSignChoosePage();
                }
            }
            break;

            case SIGN_BUTTON_CLICKED: {
                gameView.updatePlayerSignSheet();
                boolean hasNextPlayerToUpdate = gameView.changeToNextPlayer();
                if (!hasNextPlayerToUpdate) {
                    clearLevel();
                    gameView.changeSceneToNewBoardSizeChoosePage();
                }
            }
            break;

            case BOARD_SIZE_CHOSEN:
            case REPEAT_GAME_BUTTON: {
                clearLevel();
                resetActualPlayerID();
                gameView.changeSceneToNewGameBoardPage();
                try {
                    gameLogic.resetBoard(gameView.getGameBoardSize());
                } catch (IncorrectBoardSizeException e) {
                    //Unchecked
                }
            }
            break;
            case GAME_BOX_BUTTON_CLICKED: {
                Point2D coords = gameView.getLastChosenBoxCoords();
                int coordsX = (int) (coords.getX());
                int coordsY = (int) (coords.getY());
                boolean gameIsOver = checkGameResult(markField(coordsX, coordsY));
                if (!gameIsOver) {
                    gameView.changeToNextPlayer();
                    gameView.updateGamePage();
                }
            }
            break;

            case END_GAME_BUTTON_CLICKED:
                manager.closeMainStage();
                break;
        }
    }

    private void resetActualPlayerID() {
        gameView.resetActualPlayerID();
        gameLogic.resetActualPlayerID();
    }

    private GameResult markField(int coordsX, int coordsY) {
        try {
            GameResult result = gameLogic.markField(coordsX, coordsY);
            gameView.addPlayerSignToBox(coordsX, coordsY);
            return result;
        } catch (IncorrectFieldException e) {
            return GameResult.GAME_NOT_FINISHED;
        }
    }

    /**
     * @return game is over
     */
    private boolean checkGameResult(GameResult result) {
        if (isWin(result)) {
            gameView.denyInteractionWithAllBoxes();
            changeGridBoxesState(gameLogic.getWinningCoords(), SpriteStates.IS_WIN_BOX_ANIMATION);
            int indexOfWinningPlayer = gameLogic.getLastPlayerIndex();
            gameView.changeSceneToWinnerGamePage(indexOfWinningPlayer);
            return true;
        }

        if (isDraw(result)) {
            //todo: pokazac panel remisu
            return true;
        }

        return false;
    }

    private boolean isWin(GameResult result) {
        return (result == GameResult.WIN_PLAYER_0 || result == GameResult.WIN_PLAYER_1);
    }

    private void changeGridBoxesState(Point[] coordsForStateChange, SpriteStates state) {
        for (Point point : coordsForStateChange)
            gameView.changeGridBoxState(state, (int) (point.getX()), (int) (point.getY()));
    }

    private boolean isDraw(GameResult result) {
        return result == GameResult.DRAW;
    }

    private void clearLevel() {
        manager.clearActionNodes();
    }
}
