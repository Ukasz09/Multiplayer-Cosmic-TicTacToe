package com.github.Ukasz09.ticTacToe.controller;

import com.github.Ukasz09.ticTacToe.logic.game.GameLogic;
import com.github.Ukasz09.ticTacToe.logic.game.GameResults;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.IncorrectBoardSizeException;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.IncorrectFieldException;
import com.github.Ukasz09.ticTacToe.logic.server.IMsgObserver;
import com.github.Ukasz09.ticTacToe.logic.server.Messages;
import com.github.Ukasz09.ticTacToe.logic.server.Server;

import java.awt.*;
import java.io.IOException;

public class ServerController implements IMsgObserver {
    public static final int SERVER_PORT = 6666;

    private GameLogic gameLogic;
    private Server server;
    private boolean otherPlayerMadeChoice = false;

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
    public void updateMsgObserver(String msg, char clientSignId) {
        System.out.println("Message:" + msg); //todo: tmp

        switch (msg) {
            case Messages.START_BTN_CLICKED:
                if (otherPlayerMadeChoice) {
                    clearLevel();
                    resetActualPlayerID();
                    server.sendMessageToAll(Messages.SCENE_TO_NICK);
                } else {
                    server.sendMessage(Messages.CLEAR_NODES, clientSignId);
                    waitForOtherPlayerResponse(clientSignId);
                }
                break;
            case Messages.CHOSEN_VALID_NAME:
                if (otherPlayerMadeChoice) {
                    clearLevel();
                    server.sendMessageToAll(Messages.SCENE_TO_AVATAR);
                } else {
                    server.sendMessage(Messages.CLEAR_NODES, clientSignId);
                    waitForOtherPlayerResponse(clientSignId);
                }
                break;
            case Messages.AVATAR_BTN_CLICKED:
                if (otherPlayerMadeChoice) {
                    clearLevel();
                    server.sendMessageToAll(Messages.SCENE_TO_SIGN_CHOOSE);
                } else {
                    server.sendMessage(Messages.CLEAR_NODES, clientSignId);
                    waitForOtherPlayerResponse(clientSignId);
                }
                break;
            case Messages.SIGN_BTN_CLICKED:
                if (otherPlayerMadeChoice) {
                    clearLevel();
                    server.sendMessageToAll(Messages.SCENE_TO_BOARD_SIZE);
                } else {
                    server.sendMessage(Messages.CLEAR_NODES, clientSignId);
                    waitForOtherPlayerResponse(clientSignId);
                }
                break;
            case Messages.END_GAME_BTN_CLICKED:
                server.sendMessage(Messages.CLOSE_GUI, clientSignId);
                break;
            default:
                processCompoundMsg(msg, clientSignId);
        }
    }

    private void processCompoundMsg(String msg, char clientSignId) {
        if (msg.contains(Messages.BOARD_SIZE_CHOSEN) || msg.contains(Messages.REPEAT_GAME_BTN))
            newGameBoardMsg(msg, clientSignId);
        else if (msg.contains(Messages.BOX_BTN_CLICKED))
            boxClickedMsg(msg);
        else Logger.logCommunicate("Unknown message");
    }

    private void clearLevel() {
        server.sendMessageToAll(Messages.CLEAR_NODES);
        otherPlayerMadeChoice = false;
    }

    private void newGameBoardMsg(String msg, char clientSignId) {
        clearLevel();
        resetActualPlayerID();
        server.sendMessage(Messages.SCENE_TO_BOARD, clientSignId);
        try {
            gameLogic.resetBoard(Integer.parseInt(msg.split(Messages.DELIMITER)[1]));
        } catch (IncorrectBoardSizeException e) {
            //Unchecked
        }
    }

    private void boxClickedMsg(String msg) {
        String[] split = msg.split(Messages.DELIMITER);
        int coordsX = Integer.parseInt(split[1]);
        int coordsY = Integer.parseInt(split[2]);
        boolean gameIsOver = checkGameResult(markField(coordsX, coordsY));
        if (!gameIsOver)
            server.sendMessage(Messages.WAITING_FOR_OTHER_PLAYER, gameLogic.getNextPlayer());
    }

    private void resetActualPlayerID() {
        gameLogic.resetActualPlayerID();
        server.sendMessageToAll(Messages.RESET_PLAYER_ID);
    }

    private void waitForOtherPlayerResponse(char waitingPlayerSign) {
        otherPlayerMadeChoice = true;
        server.sendMessage(Messages.WAITING_FOR_OTHER_PLAYER, waitingPlayerSign);
    }

    private GameResults markField(int coordsX, int coordsY) {
        try {
            GameResults result = gameLogic.markField(coordsX, coordsY);
            server.sendMessageToAll(Messages.ADD_SIGN_TO_BOX + Messages.DELIMITER + coordsX + Messages.DELIMITER + coordsY);
            return result;
        } catch (IncorrectFieldException e) {
            return GameResults.GAME_NOT_FINISHED;
        }
    }

    /**
     * @return game is over
     */
    private boolean checkGameResult(GameResults result) {
        if (isWin(result)) {
            processWinMsg();
            return true;
        }

        if (isDraw(result)) {
            server.sendMessageToAll(Messages.SCENE_TO_DRAW);
            return true;
        }

        return false;
    }

    private void processWinMsg() {
        server.sendMessageToAll(Messages.DENY_INTERACTION_WITH_BOXES);
        String boxStatesMsg = Messages.CHANGE_BOXES_STATE + Messages.DELIMITER + getWinningCordsMsg();
        server.sendMessageToAll(boxStatesMsg);
        int indexOfWinner = gameLogic.getLastPlayerIndex();
        server.sendMessageToAll(Messages.SCENE_TO_WINNER + Messages.DELIMITER + indexOfWinner);
    }

    private boolean isWin(GameResults result) {
        return (result == GameResults.WIN_PLAYER_0 || result == GameResults.WIN_PLAYER_1);
    }

    //x1:y1:x2:y2:...
    private String getWinningCordsMsg() {
        Point[] cords = gameLogic.getWinningCoords();
        StringBuilder msg = new StringBuilder();
        for (Point p : cords)
            msg.append(p.x).append(Messages.DELIMITER).append(p.y).append(Messages.DELIMITER);
        return msg.toString();
    }

    private boolean isDraw(GameResults result) {
        return result == GameResults.DRAW;
    }
}

