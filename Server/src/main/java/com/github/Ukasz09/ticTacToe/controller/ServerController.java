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
        this.server = new Server(GameLogic.PLAYERS_ID);
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void startGame() throws IOException {
        server.start(SERVER_PORT, this);
    }

    @Override
    public synchronized void updateMsgObserver(String msg, char clientSignId) {
        System.out.println("Otrzymana wiadomosc:" + msg); //todo: tmp

        if (msg.equals(Messages.START_BTN_CLICKED)) startBtnMsg(clientSignId);
        else if (msg.equals(Messages.CHOSEN_VALID_NAME)) chosenNameMsg(clientSignId);
        else if (msg.equals(Messages.END_GAME_BTN_CLICKED)) server.sendMessage(Messages.CLOSE_GUI, clientSignId);
        else if (msg.contains(Messages.BOX_BTN_CLICKED)) boxClickedMsg(msg, clientSignId);
        else if (msg.contains(Messages.SIGN_BTN_CLICKED)) signBtnClickedMsg(msg, clientSignId);
        else if (msg.contains(Messages.AVATAR_BTN_CLICKED)) avatarBtnClickedMsg(msg, clientSignId);
        else if (msg.contains(Messages.BOARD_SIZE_CHOSEN) || msg.contains(Messages.REPEAT_GAME_BTN))
            newGameBoardMsg(msg, clientSignId);
        else Logger.logCommunicate("Unknown message");
    }

    private void startBtnMsg(char clientSignId) {
        server.sendMessage(Messages.SCENE_TO_NICK, clientSignId);
    }

    private void chosenNameMsg(char clientSignId) {
        if (otherPlayerMadeChoice) {
            otherPlayerMadeChoice = false;
            server.sendMessage(Messages.SCENE_TO_AVATAR, clientSignId);
        } else waitForOtherPlayerResponse(clientSignId);

    }

    private void newGameBoardMsg(String msg, char clientSignId) {
        try {
            gameLogic.resetBoard(Integer.parseInt(msg.split(Messages.DELIMITER)[1]));
        } catch (IncorrectBoardSizeException e) {
            //Unchecked
        }
        otherPlayerMadeChoice = false;
        gameLogic.setActualPlayerID(gameLogic.getNextPlayer(clientSignId));
        server.sendMessageToAll(Messages.SCENE_TO_BOARD + Messages.DELIMITER + gameLogic.getBoardSize() + Messages.DELIMITER + gameLogic.getActualPlayerIndex());
        server.sendMessage(Messages.DENY_INTERACTION_WITH_BOXES, clientSignId);
    }

    private void boxClickedMsg(String msg, char clientSignId) {
        String[] split = msg.split(Messages.DELIMITER);
        int coordsX = Integer.parseInt(split[1]);
        int coordsY = Integer.parseInt(split[2]);
//        int playerId = Integer.parseInt(split[3]);
        boolean gameIsOver = checkGameResult(markField(coordsX, coordsY, GameLogic.getPlayerId(clientSignId)), GameLogic.getPlayerId(clientSignId));//todo: usunac w klient dodwaanie id
        if (!gameIsOver) {
            server.sendMessage(Messages.DENY_INTERACTION_WITH_BOXES, clientSignId);
            server.sendMessage(Messages.ALLOW_INTERACTION_WITH_BOXES, gameLogic.getNextPlayer(clientSignId));
        }
    }

    private void signBtnClickedMsg(String msg, char clientSignId) {
        String occupyMsg = Messages.OCCUPY_GUI_SIGN + Messages.DELIMITER + msg.split(Messages.DELIMITER)[1]; //occupyMsg:guiSignIndex
        server.sendMessage(occupyMsg, gameLogic.getNextPlayer(clientSignId));
        if (otherPlayerMadeChoice) {
            otherPlayerMadeChoice = false;
            server.sendMessage(Messages.SCENE_TO_BOARD_SIZE, clientSignId);
        } else {
            waitForOtherPlayerResponse(clientSignId);
            server.sendMessage(Messages.SCENE_TO_SIGN_CHOOSE, gameLogic.getNextPlayer(clientSignId));
        }
    }

    private void avatarBtnClickedMsg(String msg, char clientSignId) {
        String occupyMsg = Messages.OCCUPY_AVATAR + Messages.DELIMITER + msg.split(Messages.DELIMITER)[1]; //occupyMsg:avatarIndex
        server.sendMessage(occupyMsg, gameLogic.getNextPlayer(clientSignId));
        if (otherPlayerMadeChoice) {
            otherPlayerMadeChoice = false;
            server.sendMessage(Messages.SCENE_TO_SIGN_CHOOSE, clientSignId);
        } else {
            waitForOtherPlayerResponse(clientSignId);
            server.sendMessage(Messages.SCENE_TO_AVATAR, gameLogic.getNextPlayer(clientSignId));
        }
    }

    private void waitForOtherPlayerResponse(char waitingPlayerSign) {
        otherPlayerMadeChoice = true;
        server.sendMessage(Messages.WAITING_FOR_OTHER_PLAYER, waitingPlayerSign);
    }

    private GameResults markField(int coordsX, int coordsY, int playerId) {
        try {
            GameResults result = gameLogic.markField(coordsX, coordsY);
            server.sendMessageToAll(Messages.ADD_SIGN_TO_BOX + Messages.DELIMITER + coordsX + Messages.DELIMITER + coordsY + Messages.DELIMITER + playerId);
            return result;
        } catch (IncorrectFieldException e) {
            return GameResults.GAME_NOT_FINISHED;
        }
    }

    /**
     * @return game is over
     */
    private boolean checkGameResult(GameResults result, int playerId) {
        if (isWin(result)) {
            processWinMsg(playerId);
            return true;
        }

        if (isDraw(result)) {
            server.sendMessageToAll(Messages.SCENE_TO_DRAW);
            return true;
        }

        return false;
    }

    private void processWinMsg(int winnerId) {
        server.sendMessageToAll(Messages.DENY_INTERACTION_WITH_BOXES);
        String boxStatesMsg = Messages.CHANGE_BOXES_STATE + Messages.DELIMITER + getWinningCordsMsg();
        server.sendMessageToAll(boxStatesMsg);
        server.sendMessageToAll(Messages.SCENE_TO_WINNER + Messages.DELIMITER + winnerId);
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

