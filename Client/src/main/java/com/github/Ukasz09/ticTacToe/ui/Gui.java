package com.github.Ukasz09.ticTacToe.ui;

import com.github.Ukasz09.ticTacToe.ui.scenes.PlayerViewProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObservable;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObserver;
import com.github.Ukasz09.ticTacToe.ui.scenes.PagesManager;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

import java.util.HashSet;
import java.util.Set;

public class Gui implements IGuiObservable {
    private final static String APPLICATION_TITLE = "Tic-Tac-Toe game";
    private static final int DEFAULT_PLAYER_ID = 0;

    private ViewManager manager;
    private int playersQty = 0;
    private int actualPlayerID = DEFAULT_PLAYER_ID;
    private PlayerViewProperties[] playerViewProperties;
    private PagesManager pagesManager;
    private Set<IGuiObserver> observers;
    private boolean isMessageToProcess = false;

    //----------------------------------------------------------------------------------------------------------------//
    class GameAnimationTimer extends AnimationTimer {
        @Override
        public void handle(long currentNanoTime) {
            pagesManager.getActualScene().update();
            pagesManager.getActualScene().render();
            if (isMessageToProcess) {
                isMessageToProcess = false;
                notifyObservers(GuiEvents.RESPONSE_READ);
            }
        }
    }

    //----------------------------------------------------------------------------------------------------------------//
    public Gui() {
        observers = new HashSet<>();
        manager = ViewManager.getInstance();
        manager.initialize(APPLICATION_TITLE, false);
        pagesManager = new PagesManager();
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void startGame(int playersQty) {
        initializePlayers(playersQty);
        pagesManager.showHomePage();
        manager.getMainStage().show();
        new GameAnimationTimer().start();
    }

    private void initializePlayers(int playersQty) {
        this.playersQty = playersQty;
        playerViewProperties = new PlayerViewProperties[playersQty];
        for (int i = 0; i < playersQty; i++)
            playerViewProperties[i] = new PlayerViewProperties();
    }

    public boolean updateNextPlayerName() {
        playerViewProperties[actualPlayerID].setName(pagesManager.getLastChosenCorrectName());
        return setActualPlayerIDToNext();
    }

    /**
     * @return actual player index != 0
     */
    private boolean setActualPlayerIDToNext() {
        actualPlayerID++;
        if (actualPlayerID >= playersQty) {
            actualPlayerID = 0;
            return false;
        }
        return true;
    }

    public void updateNextPlayerAvatar() {
        playerViewProperties[actualPlayerID].setAvatar(pagesManager.getLastChosenAvatar());
    }

    public void updatePlayerSignSheet() {
        playerViewProperties[actualPlayerID].setSignSheetProperty(pagesManager.getLastChosenSignSheet());
    }

    public boolean changeToNextPlayer() {
        boolean hasNextPlayer = setActualPlayerIDToNext();
        pagesManager.setActualInitializedPlayerNick(playerViewProperties[actualPlayerID].getName());
        return hasNextPlayer;
    }

    public void attachObserverToPagesManager(IGuiObserver observer) {
        pagesManager.attachObserver(observer);
    }

    public void changeSceneToNewBoardSizeChoosePage() {
        pagesManager.changeSceneToNewBoardSizeChoosePage();
    }

    public void changeSceneToNewNickChoosePage() {
        pagesManager.changeSceneToNewNickChoosePage();
    }

    public void changeSceneToNewAvatarChoosePage() {
        String firstPlayerNick = playerViewProperties[0].getName();
        pagesManager.changeSceneToNewAvatarChoosePage(firstPlayerNick);
    }

    public void changeSceneToNewSignChoosePage() {
        String firstPlayerNick = playerViewProperties[0].getName();
        pagesManager.changeSceneToNewSignChoosePage(firstPlayerNick);
    }

    public void changeSceneToNewGameBoardPage() {
        int boardSize = pagesManager.getGameBoardSize();
        ImageView avatar1 = getPlayerAvatar(0);
        ImageView avatar2 = getPlayerAvatar(1);
        String nick1 = getPlayerNick(0);
        String nick2 = getPlayerNick(1);
        ImageSheetProperty sign1 = getPlayerSignSheet(0);
        ImageSheetProperty sign2 = getPlayerSignSheet(1);
        pagesManager.showGamePage(actualPlayerID, avatar1, avatar2, sign1, sign2, nick1, nick2, boardSize);
    }

    public void addPlayerSignToBox(int rowIndex, int columnIndex) {
        pagesManager.addPlayerSignToBox(rowIndex, columnIndex, getPlayerSignSheet(actualPlayerID));
    }

    public void updateGamePage() {
        pagesManager.showVisibleOnlyActualPlayer(actualPlayerID);
    }

    public void changeGridBoxState(SpriteStates state, int coordsX, int coordsY) {
        pagesManager.changeGridBoxState(state, coordsX, coordsY);
    }

    public void changeSceneToWinnerGamePage(int winningPlayerIndex) {
        pagesManager.changeSceneToWinGamePage(winningPlayerIndex, getPlayerNick(winningPlayerIndex));
    }

    public void changeSceneToDrawGamePage() {
        pagesManager.changeSceneToDrawGamePage();
    }

    public void denyInteractionWithAllBoxes() {
        pagesManager.denyInteractionWithAllBoxes();
    }

    public void resetActualPlayerID() {
        actualPlayerID = DEFAULT_PLAYER_ID;
    }

    public int getGameBoardSize() {
        return pagesManager.getGameBoardSize();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private ImageView getPlayerAvatar(int playerIndex) {
        if (playerIndexIsValid(playerIndex))
            return playerViewProperties[playerIndex].getAvatar();
        return null;
    }

    public Point2D getLastChosenBoxCoords() {
        return pagesManager.getLastChosenBoxCoords();
    }

    private ImageSheetProperty getPlayerSignSheet(int playerIndex) {
        if (playerIndexIsValid(playerIndex))
            return playerViewProperties[playerIndex].getSignSheetProperty();
        return null;
    }

    private String getPlayerNick(int playerIndex) {
        if (playerIndexIsValid(playerIndex))
            return playerViewProperties[playerIndex].getName();
        return null;
    }

    private boolean playerIndexIsValid(int playerIndex) {
        return (playerIndex >= 0 && playerIndex < playerViewProperties.length);
    }

    @Override
    public void attachObserver(IGuiObserver observer) {
        observers.add(observer);
    }

    @Override
    public void detachObserver(IGuiObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(GuiEvents event) {
        for (IGuiObserver o : observers)
            o.updateGuiObserver(event);
    }

    public void isMessageToProcess(boolean value) {
        isMessageToProcess = value;
    }

    public void clearActionNodes() {
        manager.clearActionNodes();
    }

    public void close() {
        manager.closeMainStage();
    }

}
