package com.github.Ukasz09.ticTacToe.controller;

import com.github.Ukasz09.ticTacToe.logic.Logger;
import com.github.Ukasz09.ticTacToe.logic.client.Client;
import com.github.Ukasz09.ticTacToe.logic.client.Messages;
import com.github.Ukasz09.ticTacToe.ui.Gui;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObserver;
import javafx.geometry.Point2D;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientController extends Thread implements IGuiObserver {
    private static final int PLAYERS_QTY = 2;
    private static final int SERVER_PORT = 6666;

    private Gui gui;
    private Client client;
    private boolean gameIsEnd = false;
    private String lastReadMsg = null; //null = all messages proceeded

    //----------------------------------------------------------------------------------------------------------------//
    public ClientController(Gui gui) {
        this.client = new Client();
        this.gui = gui;
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void startGame() throws IOException {
        client.startConnection(SERVER_PORT);
        gui.startGame(PLAYERS_QTY);
        gui.attachObserverToPagesManager(this);
        gui.attachObserver(this);
        start();
    }

    @Override
    public void run() {
        while (!gameIsEnd) {
            try {
                //because it is allowed to manipulate FX components only from FX thread
                lastReadMsg = client.readResponse();
                gui.isMessageToProcess(true);
            } catch (IOException e) {
                Logger.logError(getClass(), "Can't read server response: " + e.getMessage());
                e.printStackTrace();
                System.exit(-1);
            }
        }
        endGame();
    }

    private void endGame() {
        try {
            client.stopConnection();
            gui.close();
            System.exit(0);
        } catch (IOException e) {
            Logger.logError(getClass(), "Can't stop connection with client: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    @Override
    public void updateGuiObserver(GuiEvents guiEvents) {
        switch (guiEvents) {
            case RESPONSE_READ:
                processResponse(lastReadMsg);
                break;
            case START_BTN_CLICKED:
            case CHOSEN_VALID_NAME:
            case AVATAR_BTN_CLICKED:
            case SIGN_BTN_CLICKED:
                client.sendMessage(guiEvents.getMsg());
                break;
            case BOARD_SIZE_CHOSEN:
            case REPEAT_GAME_BTN:
                client.sendMessage(guiEvents.getMsg() + Messages.DELIMITER + gui.getGameBoardSize());
                break;
            case BOX_BTN_CLICKED: {
                Point2D coords = gui.getLastChosenBoxCoords();
                int coordsX = (int) (coords.getX());
                int coordsY = (int) (coords.getY());
                String msg = Messages.BOX_BTN_CLICKED + Messages.DELIMITER + coordsX + Messages.DELIMITER + coordsY;
                client.sendMessage(msg);
                gui.changeToNextPlayer();
                gui.updateGamePage();
            }
            break;
            case END_GAME_BTN_CLICKED:
                endGame();
                break;
        }
    }

    private void processResponse(String response) {
        System.out.println("RESPONSE:" + response); //todo: tmp

        switch (response) {
            case Messages.CLEAR_NODES:
                gui.clearActionNodes();
                break;
            case Messages.RESET_PLAYER_ID:
                gui.resetActualPlayerID();
                break;
            case Messages.SCENE_TO_NICK:
                gui.changeSceneToNewNickChoosePage();
                break;
            case Messages.WAITING_FOR_OTHER_PLAYER:           //todo: show msg in gui
                System.out.println("Czekam na drugiego gracza");
                break;
            case Messages.SCENE_TO_AVATAR:
                gui.changeSceneToNewAvatarChoosePage();
                break;
            case Messages.SCENE_TO_SIGN_CHOOSE:
                gui.changeSceneToNewSignChoosePage();
                break;
            case Messages.SCENE_TO_BOARD_SIZE:
                gui.changeSceneToNewBoardSizeChoosePage();
                break;
            case Messages.SCENE_TO_BOARD:
                gui.changeSceneToNewGameBoardPage();
                break;
            case Messages.CLOSE_GUI:
                gameIsEnd = true;
                break;
            case Messages.DENY_INTERACTION_WITH_BOXES:
                gui.denyInteractionWithAllBoxes();
                break;
            case Messages.SCENE_TO_DRAW:
                gui.changeSceneToDrawGamePage();
                break;
            default:
                processCompoundMsg(response);
        }
    }

    private void processCompoundMsg(String msg) {
        if (msg.contains(Messages.ADD_SIGN_TO_BOX)) {
            String[] split = msg.split(Messages.DELIMITER);
            gui.addPlayerSignToBox(Integer.parseInt(split[1]), Integer.parseInt(split[2]));
        } else if (msg.contains(Messages.CHANGE_BOXES_STATE)) {
            String[] split = msg.split(Messages.DELIMITER); //todo: pamietac by usunac w serwerze win
            changeGridBoxesState(decodePointsMsg(split, 1), SpriteStates.IS_WIN_BOX_ANIMATION);
        } else if (msg.contains(Messages.SCENE_TO_WINNER)) {
            int indexOfWinner = Integer.parseInt(msg.split(Messages.DELIMITER)[1]);
            gui.changeSceneToWinnerGamePage(indexOfWinner);
        } else {
            System.out.println("Unknown message");
        }

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
            gui.changeGridBoxState(state, (int) (point.getX()), (int) (point.getY()));
    }
}
