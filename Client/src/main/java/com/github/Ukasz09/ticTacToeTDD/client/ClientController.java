package com.github.Ukasz09.ticTacToeTDD.client;

import com.github.Ukasz09.ticTacToeTDD.Logger;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.Gui;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToeTDD.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.Messages;
import javafx.geometry.Point2D;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ClientController extends Thread implements IEventKindObserver {
    private static final int PLAYERS_QTY = 2;
    private static final int SERVER_PORT = 6666;

    private Gui gui;
    private ViewManager manager;
    private Client client;

    private boolean gameIsEnd = false;
    private String lastReaderMsg = null; //null = all messages proceeded

    //----------------------------------------------------------------------------------------------------------------//
//    public class ResponseReader extends Thread {
//        @Override
//        public void run() {
//            while (gameIsEnd) {
//                try {
//                    if (lastReaderMsg == null)
//                        lastReaderMsg = client.readResponse();
//                } catch (IOException e) {
//                    Logger.logError(getClass(), "Can't read server response: " + e.getMessage());
//                    e.printStackTrace();
//                    System.exit(-1);
//                }
//            }
//        }
//    }

    //----------------------------------------------------------------------------------------------------------------//
    public ClientController(Gui gui) {
        this.client = new Client();
        this.gui = gui;
        manager = ViewManager.getInstance();
    }

    public void startGame() throws IOException {
        client.startConnection(SERVER_PORT);
        gui.startGame(PLAYERS_QTY);
        gui.attachObserverToPagesManager(this);
        gui.attachObserver(this);
        start();
//        startResponseProcessingLoop();
    }

    @Override
    public void run() {
        while (!gameIsEnd) {
            try {
                lastReaderMsg = client.readResponse(); //because it is allowed to manipulate FX components only from FX thread
                gui.isMessageToProcess(true);
            } catch (IOException e) {
                Logger.logError(getClass(), "Can't read server response: " + e.getMessage());
                e.printStackTrace();
                System.exit(-1);
            }
        }
        endGame();
    }

//    private void startResponseProcessingLoop() {
//        while (!gameIsEnd) {
//            if (lastReaderMsg != null)
//                processResponse(lastReaderMsg);
//            lastReaderMsg = null;
//        }
//        endGame();
//    }

    private void endGame() {
        try {
            client.stopConnection();
        } catch (IOException e) {
            Logger.logError(getClass(), "Can't stop connection with client: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
        System.exit(0);
    }

    private void processResponse(String response) {
        System.out.println("RESPONSE:" + response);

        switch (response) {
            case Messages.CLEAR_ACTION_NODES:
                manager.clearActionNodes();
                break;

            case Messages.RESET_ACTUAL_PLAYER_ID:
                gui.resetActualPlayerID();
                break;

            case Messages.SCENE_TO_NICK_PAGE: {
                gui.changeSceneToNewNickChoosePage();
            }
            break;

            case Messages.WAITING_FOR_OTHER_PLAYER: {
                //todo: wyprintowac ze czekam na drugiego gracza
                System.out.println("Czekam na drugiego gracza");
            }
            break;

            case Messages.SCENE_TO_AVATAR_PAGE: {
                gui.changeSceneToNewAvatarChoosePage();
            }
            break;

            case Messages.SCENE_TO_SIGN_CHOOSE_PAGE: {
                gui.changeSceneToNewSignChoosePage();
            }
            break;

            case Messages.SCENE_TO_BOARD_SIZE_PAGE: {
                gui.changeSceneToNewBoardSizeChoosePage();
            }
            break;

            case Messages.SCENE_TO_BOARD_PAGE: {
                gui.changeSceneToNewGameBoardPage();
            }
            break;

            case Messages.CLOSE_MAIN_STAGE: {
                gameIsEnd = true;
            }
            break;

            case Messages.DENY_INTERACTION_WITH_BOXES: {
                gui.denyInteractionWithAllBoxes();
            }
            break;

            case Messages.SCENE_TO_DRAW_PAGE: {
                gui.changeSceneToDrawGamePage();
            }
            break;

            //todo: tmp
            default: {
                if (response.contains(Messages.ADD_SIGN_TO_BOX)) {
                    String[] splited = response.split(Messages.DELIMITER);
                    gui.addPlayerSignToBox(Integer.parseInt(splited[1]), Integer.parseInt(splited[2]));
                } else if (response.contains(Messages.CHANGE_BOXES_STATE)) {
                    String[] splited = response.split(Messages.DELIMITER);
                    //todo: pomijam splited[1]=winResult
                    changeGridBoxesState(decodePointsMsg(splited, 2), SpriteStates.IS_WIN_BOX_ANIMATION);
                } else if (response.contains(Messages.SCENE_TO_WINNER_PAGE)) {
                    int indexOfWinner = Integer.parseInt(response.split(Messages.DELIMITER)[1]);
                    gui.changeSceneToWinnerGamePage(indexOfWinner);
                } else {
                    System.out.println("Unknown msg");
                }
            }


        }
    }

    private ArrayList<Point> decodePointsMsg(String[] splitedMsg, int firstPointIndex) {
        ArrayList<Point> points = new ArrayList<>();
        for (int i = firstPointIndex; i < splitedMsg.length - 1; i += 2) {
            int x = Integer.parseInt(splitedMsg[i]);
            int y = Integer.parseInt(splitedMsg[i + 1]);
            points.add(new Point(x, y));
        }
        return points;
    }

    private void changeGridBoxesState(List<Point> coordsForStateChange, SpriteStates state) {
        for (Point point : coordsForStateChange)
            gui.changeGridBoxState(state, (int) (point.getX()), (int) (point.getY()));
    }

    @Override
    public void updateObserver(EventKind eventKind) {
        switch (eventKind) {
            case READED_MSG:
                processResponse(lastReaderMsg);
                break;

            case START_BUTTON_CLICKED: {
                client.sendMessage(Messages.START_BTN_CLICKED);
            }
            break;

            case CHOSEN_VALID_NAME: {
                client.sendMessage(Messages.CHOSEN_VALID_NAME);
            }
            break;

            case AVATAR_BUTTON_CLICKED: {
                client.sendMessage(Messages.AVATAR_BTN_CLICKED);
            }
            break;

            case SIGN_BUTTON_CLICKED: {
                client.sendMessage(Messages.SIGN_BTN_CLICKED);
            }
            break;

            case BOARD_SIZE_CHOSEN: {
                String msg = Messages.BOARD_SIZE_CHOSEN + Messages.DELIMITER + gui.getGameBoardSize();
                client.sendMessage(msg);
            }
            break;

            case REPEAT_GAME_BUTTON: {
                String msg = Messages.REPEAT_GAME_BUTTON + Messages.DELIMITER + gui.getGameBoardSize();
                client.sendMessage(msg);
            }
            break;
            case GAME_BOX_BUTTON_CLICKED: {
                Point2D coords = gui.getLastChosenBoxCoords();
                int coordsX = (int) (coords.getX());
                int coordsY = (int) (coords.getY());
                String msg = Messages.BOX_BTN_CLICKED + Messages.DELIMITER + coordsX + Messages.DELIMITER + coordsY;
                client.sendMessage(msg);
                gui.changeToNextPlayer();
                gui.updateGamePage();
            }
            break;

            case END_GAME_BUTTON_CLICKED:
                manager.closeMainStage();
                break;
        }
    }
}
