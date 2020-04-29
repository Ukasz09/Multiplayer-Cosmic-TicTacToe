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

    private ViewManager manager;
    private int playersQty = 0;
    private int actualPlayerID = 0;
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

    public void updateNextPlayerName(String name) {
        playerViewProperties[getNextPlayerId()].setName(name);
    }

    public void updateActualPlayerName() {
        playerViewProperties[actualPlayerID].setName(pagesManager.getLastChosenCorrectName());
    }


    public void updateNextPlayerAvatar(int avatarId) {
        playerViewProperties[getNextPlayerId()].setAvatar(pagesManager.getAvatarImage(avatarId));
    }

    public void updateActualPlayerAvatar() {
        playerViewProperties[actualPlayerID].setAvatar(pagesManager.getAvatarImage(getLastChosenAvatarId()));
    }

    public int getLastChosenAvatarId() {
        return pagesManager.getLastChosenAvatarId();
    }

    public void updateActualPlayerSign() {
        playerViewProperties[actualPlayerID].setSignSheetProperty(pagesManager.getLastChosenSignSheet());
    }

    public void updateNextPlayerSign(int signId) {
        playerViewProperties[getNextPlayerId()].setSignSheetProperty(pagesManager.getSignSheet(signId));
    }

    public int getLastChosenSignId() {
        return pagesManager.getLastChosenSignId();
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

    public void changeSceneToNewGameBoardPage(int boardSize, int startedPlayer) {
        ImageView avatar1 = getPlayerAvatar(startedPlayer);
        ImageView avatar2 = getPlayerAvatar(getNextPlayerId(startedPlayer));
        String nick1 = getPlayerNick(startedPlayer);
        String nick2 = getPlayerNick(getNextPlayerId(startedPlayer));
        ImageSheetProperty sign1 = getPlayerSignSheet(startedPlayer);
        ImageSheetProperty sign2 = getPlayerSignSheet(getNextPlayerId(startedPlayer));
        pagesManager.showGamePage(actualPlayerID, avatar1, avatar2, sign1, sign2, nick1, nick2, boardSize);
    }

    public void addPlayerSignToBox(int rowIndex, int columnIndex) {
        pagesManager.addPlayerSignToBox(rowIndex, columnIndex, getPlayerSignSheet(actualPlayerID));
    }

    public void showVisiblePlayerBoardPane(int playerId) {
        pagesManager.showVisibleOnlyActualPlayer(playerId);
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

    public void interactionWithAllBoxes(boolean allowed) {
        pagesManager.interactionWithAllBoxes(allowed);
    }

    public int getGameBoardSize() {
        return pagesManager.getGameBoardSize();
    }

    public int getNextPlayerId() {
        return 1;
    }

    public int getNextPlayerId(int startedPlayerId) {
        return startedPlayerId == 0 ? 1 : 0;
    }

    public int getActualPlayerID() {
        return actualPlayerID;
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

    //todo: tmp
    public void setActualSceneVisible(boolean value){
        pagesManager.setActualSceneVisible(value);
    }
}
