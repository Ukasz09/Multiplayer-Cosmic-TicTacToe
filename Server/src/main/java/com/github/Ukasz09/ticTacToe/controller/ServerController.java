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
    private boolean otherPlayerWantRepeat = false;
    private boolean otherPlayerWantNewGame = true;

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
    public synchronized void updateMsgObserver(String msg, char clientSign) {
        if (msg.equals(Messages.START_BTN_CLICKED)) startBtnMsg(clientSign);
        else if (msg.contains(Messages.CHOSEN_VALID_NAME))
            chosenNameMsg(clientSign, msg.split(Messages.DELIMITER)[1]);
        else if (msg.equals(Messages.END_GAME_BTN_CLICKED)) server.sendMessage(Messages.CLOSE_GUI, clientSign);
        else if (msg.contains(Messages.BOX_BTN_CLICKED)) boxClickedMsg(msg, clientSign);
        else if (msg.contains(Messages.SIGN_BTN_CLICKED)) signBtnClickedMsg(msg, clientSign);
        else if (msg.contains(Messages.AVATAR_BTN_CLICKED)) avatarBtnClickedMsg(msg, clientSign);
        else if (msg.contains(Messages.BOARD_SIZE_CHOSEN))
            newGameBoardMsg(msg, clientSign);
        else if (msg.contains(Messages.REPEAT_GAME_BTN))
            repeatMsg(msg, clientSign);
        else if (msg.equals(Messages.STOP_CONNECTION)) {
            server.closeClient(clientSign);
            server.sendMessage(Messages.OTHER_PLAYER_QUIT, gameLogic.getNextPlayer(clientSign));
            try {
                server.stop();
            } catch (IOException e) {
                //unchecked
            }
            Logger.logCommunicate("Server is down");
            System.exit(0);
        } else Logger.logCommunicate("Unknown message");
    }

    private void startBtnMsg(char clientSign) {
        otherPlayerWantRepeat = false;
        if (otherPlayerWantNewGame) {
            server.sendMessageToAll(Messages.CLEAR_PLAYERS_DATA);
            server.sendMessage(Messages.SCENE_TO_NICK, clientSign);
            server.sendMessage(Messages.SCENE_TO_NICK, gameLogic.getNextPlayer(clientSign));
        } else {
            otherPlayerWantNewGame = true;
            otherPlayerDecisionMsg("start new game", clientSign);
        }

    }

    private void repeatMsg(String msg, char clientSignId) {
        otherPlayerWantNewGame = false;
        if (otherPlayerWantRepeat) {
            otherPlayerWantRepeat = false;
            newGameBoardMsg(msg, clientSignId);
        } else {
            otherPlayerWantRepeat = true;
            otherPlayerDecisionMsg("repeat actual game", clientSignId);
        }
    }

    private void otherPlayerDecisionMsg(String msg, char actualPlayerSignId) {
        server.sendMessage(Messages.WAITING_FOR_OTHER_PLAYER, actualPlayerSignId);
        String decisionMsg = Messages.DECISION_AFTER_GAME_FINISH + Messages.DELIMITER + msg;
        server.sendMessage(decisionMsg, gameLogic.getNextPlayer(actualPlayerSignId));
    }

    private void chosenNameMsg(char clientSign, String nick) {
        server.sendMessage(Messages.OTHER_PLAYER_NICK + Messages.DELIMITER + nick, gameLogic.getNextPlayer(clientSign));
        if (otherPlayerMadeChoice) {
            otherPlayerMadeChoice = false;
            server.sendMessage(Messages.SCENE_TO_AVATAR, clientSign);
        } else waitForOtherPlayerResponse(clientSign);

    }

    private void newGameBoardMsg(String msg, char clientSignId) {
        otherPlayerWantNewGame = false;
        try {
            int boardSize = Integer.parseInt(msg.split(Messages.DELIMITER)[1]);
            int markQtyForWin;
            if (boardSize == 3)
                markQtyForWin = 3;
            else markQtyForWin = 4;
            gameLogic.resetBoard(boardSize, markQtyForWin);
            otherPlayerMadeChoice = false;
            gameLogic.setActualPlayerID(gameLogic.getNextPlayer(clientSignId));
            server.sendMessageToAll(Messages.SCENE_TO_BOARD + Messages.DELIMITER + gameLogic.getBoardSize() + Messages.DELIMITER + gameLogic.getActualPlayerIndex());
            server.sendMessage(Messages.DENY_INTERACTION_WITH_BOXES, clientSignId);
        } catch (IncorrectBoardSizeException e) {
            //Unchecked
        }
    }

    private void boxClickedMsg(String msg, char clientSignId) {
        String[] split = msg.split(Messages.DELIMITER);
        int coordsX = Integer.parseInt(split[1]);
        int coordsY = Integer.parseInt(split[2]);
        boolean gameIsOver = checkGameResult(markField(coordsX, coordsY, GameLogic.getPlayerId(clientSignId)), GameLogic.getPlayerId(clientSignId));
        if (!gameIsOver) {
            server.sendMessage(Messages.DENY_INTERACTION_WITH_BOXES, clientSignId);
            server.sendMessage(Messages.ALLOW_INTERACTION_WITH_BOXES, gameLogic.getNextPlayer(clientSignId));
        }
    }

    private void signBtnClickedMsg(String msg, char clientSign) {
        String occupyMsg = Messages.OCCUPY_GUI_SIGN + Messages.DELIMITER + msg.split(Messages.DELIMITER)[1]; //occupyMsg:guiSignIndex
        server.sendMessage(occupyMsg, gameLogic.getNextPlayer(clientSign));
        if (otherPlayerMadeChoice) {
            otherPlayerMadeChoice = false;
            server.sendMessage(Messages.SCENE_TO_BOARD_SIZE, clientSign);
        } else {
            waitForOtherPlayerResponse(clientSign);
            server.sendMessage(Messages.SCENE_TO_SIGN_CHOOSE, gameLogic.getNextPlayer(clientSign));
        }
    }

    private void avatarBtnClickedMsg(String msg, char clientSign) {
        String occupyMsg = Messages.OCCUPY_AVATAR + Messages.DELIMITER + msg.split(Messages.DELIMITER)[1]; //occupyMsg:avatarIndex
        server.sendMessage(occupyMsg, gameLogic.getNextPlayer(clientSign));
        if (otherPlayerMadeChoice) {
            otherPlayerMadeChoice = false;
            server.sendMessage(Messages.SCENE_TO_SIGN_CHOOSE, clientSign);
        } else {
            waitForOtherPlayerResponse(clientSign);
            server.sendMessage(Messages.SCENE_TO_AVATAR, gameLogic.getNextPlayer(clientSign));
        }
    }

    private void waitForOtherPlayerResponse(char waitingPlayerSign) {
        otherPlayerMadeChoice = true;
        server.sendMessage(Messages.WAITING_FOR_OTHER_PLAYER, waitingPlayerSign);
    }

    private GameResults markField(int coordsX, int coordsY, int playerId) {
        try {
            GameResults result = gameLogic.play(coordsX, coordsY);
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

