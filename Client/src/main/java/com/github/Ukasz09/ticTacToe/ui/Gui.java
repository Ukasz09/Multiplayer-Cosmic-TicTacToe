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
    public static final int PLAYERS_QTY = 2;

    private ViewManager manager;
    private PlayerViewProperties[] playerViewProperties;
    private PagesManager pagesManager;
    private Set<IGuiObserver> guiObservers;
    private int actualPlayerID;

    //----------------------------------------------------------------------------------------------------------------//
    class GameAnimationTimer extends AnimationTimer {
        @Override
        public void handle(long currentNanoTime) {
            pagesManager.getActualScene().update();
            pagesManager.getActualScene().render();
            notifyObservers(GuiEvents.RESPONSE_CHECK);
        }
    }

    //----------------------------------------------------------------------------------------------------------------//
    public Gui() {
        guiObservers = new HashSet<>();
        manager = ViewManager.getInstance();
        manager.initialize(APPLICATION_TITLE, false);
        pagesManager = new PagesManager();
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void startGame() {
        initializePlayers();
        pagesManager.showHomePage();
        manager.getMainStage().show();
        new GameAnimationTimer().start();
    }

    private void initializePlayers() {
        playerViewProperties = new PlayerViewProperties[PLAYERS_QTY];
        for (int i = 0; i < PLAYERS_QTY; i++)
            playerViewProperties[i] = new PlayerViewProperties();
    }

    //TODO
    public void updateNextPlayerName(String name) {
        playerViewProperties[getNextPlayerNumb()].setName(name);
    }

    public void updateActualPlayerName() {
        playerViewProperties[actualPlayerID].setName(pagesManager.getChosenCorrectName());
    }

    public int getNextPlayerNumb() {
        return getNextPlayerNumb(actualPlayerID);
    }

    public static int getNextPlayerNumb(int playerNumber) {
        return playerNumber == 0 ? 1 : 0;
    }

    public int getActualPlayerID() {
        return actualPlayerID;
    }

    public void setPlayerNumber(int actualPlayerID) {
        this.actualPlayerID = actualPlayerID;
    }

    public void updateNextPlayerAvatar(int avatarId) {
        playerViewProperties[getNextPlayerNumb()].setAvatar(pagesManager.getAvatarImage(avatarId));
    }

    public void updateActualPlayerAvatar() {
        playerViewProperties[actualPlayerID].setAvatar(pagesManager.getAvatarImage(getLastChosenAvatarId()));
    }

    public int getLastChosenAvatarId() {
        return pagesManager.getChosenAvatarId();
    }

    public void updateActualPlayerSign() {
        playerViewProperties[actualPlayerID].setSignSheetProperty(pagesManager.getChosenSignSheet());
    }

    public void updateNextPlayerSign(int signId) {
        playerViewProperties[getNextPlayerNumb()].setSignSheetProperty(pagesManager.getSignSheet(signId));
    }


    public int getLastChosenSignId() {
        return pagesManager.getChosenSignId();
    }

    public void attachObserverToPagesManager(IGuiObserver observer) {
        pagesManager.attachObserver(observer);
    }

    public void changeSceneToNewBoardSizeChoosePage() {
        pagesManager.sceneToBoardSizePage();
    }

    public void changeSceneToNewNickChoosePage() {
        pagesManager.sceneToNickPage();
    }

    public void changeSceneToNewAvatarChoosePage() {
        String firstPlayerNick = playerViewProperties[0].getName();
        pagesManager.sceneToAvatarPage(firstPlayerNick);
    }

    public void changeSceneToNewSignChoosePage() {
        pagesManager.sceneToSignPage();
    }

    public void changeSceneToNewGameBoardPage(int boardSize, int startedPlayer) {
        ImageView avatar1 = getPlayerAvatar(startedPlayer);
        ImageView avatar2 = getPlayerAvatar(getNextPlayerNumb(startedPlayer));
        String nick1 = getPlayerNick(startedPlayer);
        String nick2 = getPlayerNick(getNextPlayerNumb(startedPlayer));
        ImageSheetProperty sign1 = getPlayerSignSheet(startedPlayer);
        ImageSheetProperty sign2 = getPlayerSignSheet(getNextPlayerNumb(startedPlayer));
        pagesManager.showGamePage(startedPlayer, avatar1, avatar2, sign1, sign2, nick1, nick2, boardSize);
    }

    public void addPlayerSignToBox(int rowIndex, int columnIndex, int playerId) {
        pagesManager.addPlayerSignToBox(rowIndex, columnIndex, getPlayerSignSheet(playerId));
    }

    public void showVisiblePlayerBoardPane(int playerId) {
        pagesManager.showVisibleOnlyActualPlayerPane(playerId);
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
        guiObservers.add(observer);
    }

    @Override
    public void detachObserver(IGuiObserver observer) {
        guiObservers.remove(observer);
    }

    @Override
    public void notifyObservers(GuiEvents event) {
        for (IGuiObserver o : guiObservers)
            o.updateGuiObserver(event);
    }

    public void clearActionNodes() {
        manager.clearActionNodes();
    }

    public void close() {
        manager.closeMainStage();
    }

    //todo: tmp
    public void setActualSceneVisible(boolean value) {
        pagesManager.setActualSceneVisible(value);
    }
}
