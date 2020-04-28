package com.github.Ukasz09.ticTacToeTDD.controller;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.Gui;
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
    private Gui gui;
    private GameLogic gameLogic;
    private ViewManager manager;

    //----------------------------------------------------------------------------------------------------------------//
    public GameController(Gui gui, GameLogic gameLogic) {
        this.gui = gui;
        this.gameLogic = gameLogic;
        this.manager = ViewManager.getInstance();
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void startGame() {
        gui.startGame(gameLogic.getPlayersQty());
        gui.attachObserverToPagesManager(this);
    }

    @Override
    public void updateObserver(EventKind eventKind) {
        switch (eventKind) {
            case START_BUTTON_CLICKED: {
                clearLevel();
                resetActualPlayerID();
                gui.changeSceneToNewNickChoosePage();
            }
            break;

            case CHOSEN_VALID_NAME: {
                boolean hasNextPlayerToUpdate = gui.updateNextPlayerName();
                if (!hasNextPlayerToUpdate) {
                    clearLevel();
                    gui.changeSceneToNewAvatarChoosePage();
                }
            }
            break;

            case AVATAR_BUTTON_CLICKED: {
                gui.updateNextPlayerAvatar();
                boolean hasNextPlayerToUpdate = gui.changeToNextPlayer();
                if (!hasNextPlayerToUpdate) {
                    clearLevel();
                    gui.changeSceneToNewSignChoosePage();
                }
            }
            break;

            case SIGN_BUTTON_CLICKED: {
                gui.updatePlayerSignSheet();
                boolean hasNextPlayerToUpdate = gui.changeToNextPlayer();
                if (!hasNextPlayerToUpdate) {
                    clearLevel();
                    gui.changeSceneToNewBoardSizeChoosePage();
                }
            }
            break;

            case BOARD_SIZE_CHOSEN:
            case REPEAT_GAME_BUTTON: {
                clearLevel();
                resetActualPlayerID();
                gui.changeSceneToNewGameBoardPage();
                try {
                    gameLogic.resetBoard(gui.getGameBoardSize());
                } catch (IncorrectBoardSizeException e) {
                    //Unchecked
                }
            }
            break;
            case GAME_BOX_BUTTON_CLICKED: {
                Point2D coords = gui.getLastChosenBoxCoords();
                int coordsX = (int) (coords.getX());
                int coordsY = (int) (coords.getY());
                boolean gameIsOver = checkGameResult(markField(coordsX, coordsY));
                if (!gameIsOver) {
                    gui.changeToNextPlayer();
                    gui.updateGamePage();
                }
            }
            break;

            case END_GAME_BUTTON_CLICKED:
                manager.closeMainStage();
                break;
        }
    }

    private void resetActualPlayerID() {
        gui.resetActualPlayerID();
        gameLogic.resetActualPlayerID();
    }

    private GameResult markField(int coordsX, int coordsY) {
        try {
            GameResult result = gameLogic.markField(coordsX, coordsY);
            gui.addPlayerSignToBox(coordsX, coordsY);
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
            gui.denyInteractionWithAllBoxes();
            changeGridBoxesState(gameLogic.getWinningCoords(), SpriteStates.IS_WIN_BOX_ANIMATION);
            int indexOfWinningPlayer = gameLogic.getLastPlayerIndex();
            gui.changeSceneToWinnerGamePage(indexOfWinningPlayer);
            return true;
        }

        if (isDraw(result)) {
            gui.changeSceneToDrawGamePage();
            return true;
        }

        return false;
    }

    private boolean isWin(GameResult result) {
        return (result == GameResult.WIN_PLAYER_0 || result == GameResult.WIN_PLAYER_1);
    }

    private void changeGridBoxesState(Point[] coordsForStateChange, SpriteStates state) {
        for (Point point : coordsForStateChange)
            gui.changeGridBoxState(state, (int) (point.getX()), (int) (point.getY()));
    }

    private boolean isDraw(GameResult result) {
        return result == GameResult.DRAW;
    }

    private void clearLevel() {
        manager.clearActionNodes();
    }
}
