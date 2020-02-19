package com.github.Ukasz09.ticTacToeTDD.controller;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.GameView;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.GameLogic;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.GameResult;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.IncorrectBoardSizeException;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.IncorrectFieldException;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

import java.awt.*;

public class GameController implements IEventKindObserver {
    private GameView gameView;
    private GameLogic gameLogic;
    private ViewManager manager;

    public GameController(GameView gameView, GameLogic gameLogic) {
        this.gameView = gameView;
        this.gameLogic = gameLogic;
        this.manager = ViewManager.getInstance();
    }

    public void startGame() {
        gameView.startGame(gameLogic.getPlayersQty());
        gameView.attachObserverToPagesManager(this);
        ViewManager.getInstance().printNodeQty(); //todo
    }

    @Override
    public void updateObserver(EventKind eventKind) {
        switch (eventKind) {
            case START_BUTTON_CLICKED: {
                manager.removeAllNodesFromRoot();
                gameView.showNickChoosePage();
                ViewManager.getInstance().printNodeQty(); //todo
            }
            break;

            case CHOSEN_VALID_NAME: {
                boolean hasNextPlayerToUpdate = gameView.updateNextPlayerName();
                if (!hasNextPlayerToUpdate) {
                    manager.removeAllNodesFromRoot();
                    gameView.showAvatarChoosePage();
                }

                ViewManager.getInstance().printNodeQty();
            }
            break;

            case AVATAR_BUTTON_CLICKED: {
                gameView.updateNextPlayerAvatar();
                boolean hasNextPlayerToUpdate = gameView.changeToNextPlayer();
                if (!hasNextPlayerToUpdate) {
                    manager.removeAllNodesFromRoot();
                    gameView.showSignChoosePage();
                }

                ViewManager.getInstance().printNodeQty();
            }
            break;

            case SIGN_BUTTON_CLICKED: {
                gameView.updatePlayerSignSheet();
                boolean hasNextPlayerToUpdate = gameView.changeToNextPlayer();
                if (!hasNextPlayerToUpdate) {
                    manager.removeAllNodesFromRoot();
                    gameView.showBoardSizeChoosePage();
                }

                ViewManager.getInstance().printNodeQty();
            }
            break;

            case BOARD_SIZE_CHOSEN: {
                manager.removeAllNodesFromRoot();
                initializeGameBoard();
                try {
                    gameLogic.resetBoard(gameView.getGameBoardSize());
                } catch (IncorrectBoardSizeException e) {
                    //Unchecked
                }

                ViewManager.getInstance().printNodeQty();
            }
            break;
            case GAME_BOX_BUTTON_CLICKED: {

                Point2D coords = gameView.getLastChosenBoxCoords();
                int coordsX = (int) (coords.getX());
                int coordsY = (int) (coords.getY());
                if (!checkGameResult(markField(coordsX, coordsY)))
                    changePlayer();

                ViewManager.getInstance().printNodeQty();
            }
            break;

            case END_GAME_BUTTON_CLICKED:
                ViewManager.getInstance().closeMainStage();
                break;
            default:
                System.out.println("XDD"); //todo: dodac repeat game button event + przetestowac new game button po wygranej
        }
    }

    private void initializeGameBoard() {
        int boardSize = gameView.getGameBoardSize();
        ImageView avatar1 = gameView.getPlayerAvatar(0);
        ImageView avatar2 = gameView.getPlayerAvatar(1);
        String nick1 = gameView.getPlayerNick(0);
        String nick2 = gameView.getPlayerNick(1);
        ImageSheetProperty sign1 = gameView.getPlayerSignSheet(0);
        ImageSheetProperty sign2 = gameView.getPlayerSignSheet(1);
        gameView.showGamePage(avatar1, avatar2, sign1, sign2, nick1, nick2, boardSize);
    }

    private GameResult markField(int coordsX, int coordsY) {
        try {
            GameResult result = gameLogic.markField(coordsX, coordsY);
            gameView.addSignToBox(coordsX, coordsY);
            return result;
        } catch (IncorrectFieldException e) {
            return GameResult.GAME_NOT_FINISHED;
        }
    }

    /**
     * @return winner pane is visible or not
     */
    private boolean checkGameResult(GameResult result) {
        //todo: dac w jedna metode
        if (isWin(result)) {
            Point[] winningCoords = gameLogic.getWinningCoords();
            gameView.denyInteractionWithAllBoxes();
            changeGridBoxesState(winningCoords, SpriteStates.IS_WIN_BOX_ANIMATION);
            int winnerPlayerIndex = gameLogic.getLastPlayerIndex();
            gameView.addWinnerGamePage(winnerPlayerIndex);
            gameView.setWinnerHeaderText(gameView.getPlayerNick(winnerPlayerIndex));
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

    private void changePlayer() {
        gameView.changeToNextPlayer();
        gameView.showVisibleOnlyActualPlayer();
    }
}
