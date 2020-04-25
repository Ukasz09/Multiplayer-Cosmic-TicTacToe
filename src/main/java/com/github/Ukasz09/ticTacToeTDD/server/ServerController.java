package com.github.Ukasz09.ticTacToeTDD.server;

import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.GameLogic;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.GameResult;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.IncorrectFieldException;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class ServerController implements IMsgObserver {
    public static final int SERVER_PORT = 6666;

    private GameLogic gameLogic;
    private Server server;

    //----------------------------------------------------------------------------------------------------------------//
    public ServerController(GameLogic gameLogic) {
        this.gameLogic = gameLogic;
        this.server = new Server(GameLogic.DEFAULT_PLAYERS_IDENTIFIERS);
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void startGame() throws IOException {
        server.start(SERVER_PORT, this);
    }

    @Override
    public void updateSubscriber(String msg, char signIdentifier) {
        switch (msg) {
            case Messages.START_BTN_CLICKED: {
                clearLevel();
                resetActualPlayerID();
                server.sendMessage(Messages.SCENE_TO_NICK_PAGE, signIdentifier);
            }
            break;

            //todo: jakos wymyslec
            case Messages.CHOSEN_VALID_NAME: {
                //todo; boolean hasNextPlayerToUpdate = gameView.updateNextPlayerName();
                //if (!hasNextPlayerToUpdate) {
                //  clearLevel();
                //todo: gameView.changeSceneToNewAvatarChoosePage();
                //}
            }
            break;

            case Messages.AVATAR_BTN_CLICKED: {
                //todo:
                // gameView.updateNextPlayerAvatar();
                //  boolean hasNextPlayerToUpdate = gameView.changeToNextPlayer();
                //  if (!hasNextPlayerToUpdate) {
                //      clearLevel();
                //      gameView.changeSceneToNewSignChoosePage();
                //  }
            }
            break;

            case Messages.SIGN_BTN_CLICKED: {
                //todo:
                //  gameView.updatePlayerSignSheet();
//                boolean hasNextPlayerToUpdate = gameView.changeToNextPlayer();
//                if (!hasNextPlayerToUpdate) {
//                    clearLevel();
//                    gameView.changeSceneToNewBoardSizeChoosePage();
//                }
            }
            break;

            case Messages.BOARD_SIZE_CHOSEN:
            case Messages.REPEAT_GAME_BUTTON: {
                clearLevel();
                resetActualPlayerID();

                server.sendMessage(Messages.SCENE_TO_BOARD_PAGE, signIdentifier);
                //todo:
//                try {
//                    gameLogic.resetBoard(gameView.getGameBoardSize());
//                } catch (IncorrectBoardSizeException e) {
                //Unchecked
//                }
            }
            break;
            case Messages.BOX_BTN_CLICKED: {
                //todo:
//                Point2D coords = gameView.getLastChosenBoxCoords();
//                int coordsX = (int) (coords.getX());
//                int coordsY = (int) (coords.getY());
//                boolean gameIsOver = checkGameResult(markField(coordsX, coordsY));
//                if (!gameIsOver) {
//                    gameView.changeToNextPlayer();
//                    gameView.updateGamePage();
//                }
            }
            break;

            case Messages.END_GAME_BTN_CLICKED:
                server.sendMessage(Messages.CLOSE_MAIN_STAGE, signIdentifier);
                break;
        }
    }

    private void clearLevel() {
        server.sendMessageToAll(Messages.CLEAR_ACTION_NODES);
    }

    private void resetActualPlayerID() {
        gameLogic.resetActualPlayerID();
        server.sendMessageToAll(Messages.RESET_ACTUAL_PLAYER_ID);
    }

    private GameResult markField(int coordsX, int coordsY) {
        try {
            GameResult result = gameLogic.markField(coordsX, coordsY);
            server.sendMessageToAll(Messages.ADD_SIGN_TO_BOX + Messages.DELIMITER + coordsX + Messages.DELIMITER + coordsY);
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
            server.sendMessageToAll(Messages.DENY_INTERACTION_WITH_BOXES);
            String boxStatesMsg = Messages.CHANGE_BOXES_STATE + Messages.DELIMITER + Messages.WIN_RESULT + Messages.DELIMITER + getWinningCordsMsg();
            server.sendMessageToAll(boxStatesMsg);
            int indexOfWinningPlayer = gameLogic.getLastPlayerIndex();
            server.sendMessageToAll(Messages.SCENE_TO_WINNER_PAGE + Messages.DELIMITER + indexOfWinningPlayer);
            return true;
        }

        if (isDraw(result)) {
            server.sendMessageToAll(Messages.SCENE_TO_DRAW_PAGE);
            return true;
        }

        return false;
    }

    private boolean isWin(GameResult result) {
        return (result == GameResult.WIN_PLAYER_0 || result == GameResult.WIN_PLAYER_1);
    }

    private String getWinningCordsMsg() {
        Point[] cords = gameLogic.getWinningCoords();
        StringBuilder msg = new StringBuilder();
        for (Point p : cords)
            msg.append(p.x).append(Messages.DELIMITER).append(p.y).append(Messages.DELIMITER);
        return msg.toString();
    }

//    private void changeGridBoxesState(Point[] coordsForStateChange, SpriteStates state) {
//        for (Point point : coordsForStateChange)
//            gameView.changeGridBoxState(state, (int) (point.getX()), (int) (point.getY()));
//    }

    private boolean isDraw(GameResult result) {
        return result == GameResult.DRAW;
    }

}

