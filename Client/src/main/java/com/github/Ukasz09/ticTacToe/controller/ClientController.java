package com.github.Ukasz09.ticTacToe.controller;

import com.github.Ukasz09.ticTacToe.logic.Logger;
import com.github.Ukasz09.ticTacToe.logic.client.Client;
import com.github.Ukasz09.ticTacToe.logic.client.Messages;
import com.github.Ukasz09.ticTacToe.ui.Gui;
import com.github.Ukasz09.ticTacToe.ui.scenes.pages.GameBoardPage;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObserver;
import javafx.geometry.Point2D;

import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;

public class ClientController extends Thread implements IGuiObserver {
    private static final int SERVER_PORT = 6666;

    private Gui gui;
    private Client client;
    private Queue<String> messagesToProcess;

    //----------------------------------------------------------------------------------------------------------------//
    public ClientController(Gui gui) {
        this.client = new Client();
        this.gui = gui;
        messagesToProcess = new LinkedBlockingQueue<>();
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void startGame() throws IOException {
        client.startConnection(SERVER_PORT);
        gui.startGame();
        gui.getPagesManager().attachObserver(this);
        gui.attachObserver(this);
        start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                //because it is allowed to manipulate FX components only from FX thread
                String msg = client.readResponse();
                if (msg != null)
                    messagesToProcess.add(msg);
            } catch (IOException e) {
                Logger.logError(getClass(), "Can't read server response: " + e.getMessage());
                e.printStackTrace();
                System.exit(-1);
            }
        }
    }

    private void endGame() {
        try {
            gui.close();
            client.stopConnection();
            System.exit(0);
        } catch (IOException e) {
            Logger.logError(getClass(), "Can't stop connection with client: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public synchronized void updateGuiObserver(GuiEvents guiEvents) {
        switch (guiEvents) {
            case RESPONSE_CHECK:
                if (!messagesToProcess.isEmpty())
                    processServerResponse(messagesToProcess.poll());
                break;
            case START_BTN_CLICKED:
                client.sendMessage(guiEvents.getMsg());
                break;
            case CHOSEN_VALID_NAME: {
                gui.updatePlayerNick();
                client.sendMessage(guiEvents.getMsg() + Messages.DELIMITER + gui.getPlayerNick());
            }
            break;
            case AVATAR_BTN_CLICKED:
                avatarChosenAction();
                break;
            case SIGN_BTN_CLICKED:
                signChosenAction();
                break;
            case BOARD_SIZE_CHOSEN:
            case REPEAT_GAME_BTN: {
                gui.clearActionNodes();
                String msgToSend = guiEvents.getMsg() + Messages.DELIMITER + gui.getPagesManager().getGameBoardSize();
                client.sendMessage(msgToSend);
            }
            break;
            case BOX_BTN_CLICKED:
                boxBtnClickedAction();
                break;
            case END_GAME_BTN_CLICKED:
                endGame();
                break;
        }
    }

    private void processServerResponse(String response) {
        System.out.println("RESPONSE:" + response); //todo: tmp

        switch (response) {
            case Messages.SCENE_TO_NICK:
                gui.getPagesManager().sceneToNickPage(gui.getNextPlayerNick());
                break;
            case Messages.WAITING_FOR_OTHER_PLAYER:
                gui.getPagesManager().sceneToWaitingPage();
                break;
            case Messages.SCENE_TO_AVATAR:
                gui.sceneToAvatarPage();
                break;
            case Messages.SCENE_TO_SIGN_CHOOSE:
                gui.sceneToSignPage();
                break;
            case Messages.SCENE_TO_BOARD_SIZE:
                gui.getPagesManager().sceneToBoardSizePage();
                break;
            case Messages.CLOSE_GUI:
                endGame();
                break;
            case Messages.DENY_INTERACTION_WITH_BOXES: {
                gui.getPagesManager().interactionWithAllBoxes(false);
                gui.getPagesManager().changeAllGridBoxStates(SpriteStates.NO_ANIMATION);
            }
            break;
            case Messages.ALLOW_INTERACTION_WITH_BOXES: {
                gui.getPagesManager().changeGameBoardPageHeader(GameBoardPage.ACTUAL_PLAYER_MOVE_HEADER_TXT);
                gui.getPagesManager().interactionWithAllBoxes(true);
                gui.getPagesManager().changeAllGridBoxStates(SpriteStates.STANDBY);
                gui.getPagesManager().showVisibleOnlyActualPlayerPane(gui.getPlayerNumber());
            }
            break;
            case Messages.SCENE_TO_DRAW: {
                gui.getPagesManager().changeSceneToDrawGamePage();
                gui.getPagesManager().changeAllGridBoxStates(SpriteStates.NO_ANIMATION);
            }
            break;
            default:
                processCompoundMsg(response);
        }
    }

    private void processCompoundMsg(String msg) {
        if (msg.contains(Messages.ADD_SIGN_TO_BOX))
            processSignToBoxMsg(msg);
        else if (msg.contains(Messages.CHANGE_BOXES_STATE))
            processChangeBoxStateMsg(msg);
        else if (msg.contains(Messages.SCENE_TO_WINNER))
            processSceneToWinnerMsg(msg);
        else if (msg.contains(Messages.OCCUPY_AVATAR))
            processOccupyAvatar(msg);
        else if (msg.contains(Messages.OCCUPY_GUI_SIGN))
            processOccupySign(msg);
        else if (msg.contains(Messages.SCENE_TO_BOARD))
            processSceneToBoard(msg);
        else if (msg.contains(Messages.GIVEN_CLIENT_SIGN_NUMB))
            processGivenClientSignNumber(msg);
        else if (msg.contains(Messages.OTHER_PLAYER_NICK))
            processOtherPlayerNick(msg);
        else System.out.println("Unknown message: " + msg); //TODO:tmp
    }

    private ArrayList<Point> decodePointsMsg(String[] split, int fstCordIndex) {
        ArrayList<Point> points = new ArrayList<>();
        for (int i = fstCordIndex; i < split.length - 1; i += 2) {
            int x = Integer.parseInt(split[i]);
            int y = Integer.parseInt(split[i + 1]);
            points.add(new Point(x, y));
        }
        return points;
    }

    private void changeGridBoxesState(List<Point> coordsForStateChange, SpriteStates state) {
        for (Point point : coordsForStateChange)
            gui.getPagesManager().changeGridBoxState(state, (int) (point.getX()), (int) (point.getY()));
    }

    private void avatarChosenAction() {
        String avatarChosenMsg = Messages.AVATAR_BTN_CLICKED + Messages.DELIMITER + gui.getPagesManager().getChosenAvatarNumber();
        gui.updatePlayerAvatar();
        client.sendMessage(avatarChosenMsg);
    }

    private void signChosenAction() {
        gui.clearActionNodes();
        String signChosenMsg = Messages.SIGN_BTN_CLICKED + Messages.DELIMITER + gui.getPagesManager().getChosenSignId();
        gui.updatePlayerSign();
        client.sendMessage(signChosenMsg);
    }

    private void boxBtnClickedAction() {
        gui.getPagesManager().showVisibleOnlyActualPlayerPane(gui.getNextPlayerNumber());
        gui.getPagesManager().changeGameBoardPageHeader(GameBoardPage.OPPONENT_MOVE_HEADER_TXT);

        Point2D coords = gui.getPagesManager().getLastChosenBoxCoords();
        String coordsX = String.valueOf((int) (coords.getX()));
        String coordsY = String.valueOf((int) (coords.getY()));
        String actualPlayerId = String.valueOf(gui.getPlayerNumber());
        String msgToSend = client.getCompoundMsg(Messages.BOX_BTN_CLICKED, new String[]{coordsX, coordsY, actualPlayerId});
        client.sendMessage(msgToSend);
    }

    private void processSignToBoxMsg(String msg) {
        String[] split = msg.split(Messages.DELIMITER);
        int rowIndex = Integer.parseInt(split[1]);
        int columnIndex = Integer.parseInt(split[2]);
        int playerId = Integer.parseInt(split[3]);
        gui.addPlayerSignToBox(rowIndex, columnIndex, playerId);
        gui.getPagesManager().disableInteractionWithBox(rowIndex, columnIndex, true, true);
    }

    private void processChangeBoxStateMsg(String msg) {
        String[] split = msg.split(Messages.DELIMITER);
        changeGridBoxesState(decodePointsMsg(split, 1), SpriteStates.IS_WIN_BOX_ANIMATION);
    }

    private void processSceneToWinnerMsg(String msg) {
        int indexOfWinner = Integer.parseInt(msg.split(Messages.DELIMITER)[1]);
        gui.getPagesManager().showVisibleOnlyActualPlayerPane(gui.getPlayerNumber());
        if (gui.getPlayerNumber() == indexOfWinner)
            gui.getPagesManager().changeSceneToWinGamePage(indexOfWinner);
        else
            gui.getPagesManager().changeSceneToLoseGamePage(gui.getPlayerNumber());
    }

    private void processOccupyAvatar(String msg) {
        int avatarId = Integer.parseInt(msg.split(Messages.DELIMITER)[1]);
        gui.updateNextPlayerAvatar(avatarId);
    }

    private void processOccupySign(String msg) {
        int signId = Integer.parseInt(msg.split(Messages.DELIMITER)[1]);
        gui.updateNextPlayerSign(signId);
    }

    private void processSceneToBoard(String msg) {
        String[] split = msg.split(Messages.DELIMITER);
        int boardSize = Integer.parseInt(split[1]);
        int startedPlayerId = Integer.parseInt(split[2]);
        gui.sceneToGameBoard(boardSize, startedPlayerId);
        gui.getPagesManager().showVisibleOnlyActualPlayerPane(startedPlayerId);
        if (gui.getPlayerNumber() != startedPlayerId)
            gui.getPagesManager().changeGameBoardPageHeader(GameBoardPage.OPPONENT_MOVE_HEADER_TXT);
    }

    private void processGivenClientSignNumber(String msg) {
        int playerNumber = Integer.parseInt(msg.split(Messages.DELIMITER)[1]);
        gui.setPlayerNumber(playerNumber);
    }

    private void processOtherPlayerNick(String msg) {
        gui.updateNextPlayerNick(msg.split(Messages.DELIMITER)[1]);
    }
}
