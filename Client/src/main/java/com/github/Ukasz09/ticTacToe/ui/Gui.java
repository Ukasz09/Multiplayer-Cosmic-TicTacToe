package com.github.Ukasz09.ticTacToe.ui;

import com.github.Ukasz09.ticTacToe.ui.scenes.PlayerViewProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObservable;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObserver;
import com.github.Ukasz09.ticTacToe.ui.scenes.PagesManager;

import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
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
    private int actualPlayerNumber;

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

    public void updateNextPlayerNick(String nick) {
        playerViewProperties[getNextPlayerNumber()].setNick(nick);
    }

    public void updatePlayerNick() {
        playerViewProperties[actualPlayerNumber].setNick(pagesManager.getChosenCorrectName());
    }

    public String getPlayerNick() {
        return playerViewProperties[actualPlayerNumber].getNick();
    }

    public String getNextPlayerNick() {
        return playerViewProperties[getNextPlayerNumber()].getNick();
    }

    public int getNextPlayerNumber() {
        return getNextPlayerNumber(actualPlayerNumber);
    }

    public static int getNextPlayerNumber(int playerNumber) {
        return playerNumber == 0 ? 1 : 0;
    }

    public int getPlayerNumber() {
        return actualPlayerNumber;
    }

    public void setPlayerNumber(int actualPlayerID) {
        this.actualPlayerNumber = actualPlayerID;
    }

    public void updateNextPlayerAvatar(int avatarNumber) {
        playerViewProperties[getNextPlayerNumber()].setAvatar(pagesManager.getAvatarImage(avatarNumber));
    }

    private Image getNextPlayerAvatar() {
        return playerViewProperties[getNextPlayerNumber()].getAvatar().getImage();
    }

    public void updatePlayerAvatar() {
        playerViewProperties[actualPlayerNumber].setAvatar(pagesManager.getAvatarImage(pagesManager.getChosenAvatarNumber()));
    }

    public void updatePlayerSign() {
        playerViewProperties[actualPlayerNumber].setSignSheet(pagesManager.getChosenSignSheet());
    }

    public void updateNextPlayerSign(int signNumber) {
        playerViewProperties[getNextPlayerNumber()].setSignSheet(pagesManager.getSignSheet(signNumber));
    }

    private ImageSheetProperty getNextPlayerSign() {
        return playerViewProperties[getNextPlayerNumber()].getSignSheet();
    }

    public void sceneToGameBoard(int boardSize, int startedPlayer) {
        ImageView avatar1 = getPlayerAvatar(startedPlayer);
        ImageView avatar2 = getPlayerAvatar(getNextPlayerNumber(startedPlayer));
        String nick1 = getPlayerNick(startedPlayer);
        String nick2 = getPlayerNick(getNextPlayerNumber(startedPlayer));
        ImageSheetProperty sign1 = getPlayerSignSheet(startedPlayer);
        ImageSheetProperty sign2 = getPlayerSignSheet(getNextPlayerNumber(startedPlayer));
        pagesManager.showGamePage(startedPlayer, avatar1, avatar2, sign1, sign2, nick1, nick2, boardSize);
    }

    public void sceneToAvatarPage() {
        pagesManager.sceneToAvatarPage(getNextPlayerAvatar());
    }

    public void sceneToSignPage() {
        pagesManager.sceneToSignPage(getNextPlayerSign());
    }

    public void addPlayerSignToBox(int rowIndex, int columnIndex, int playerNumber) {
        pagesManager.addPlayerSignToBox(rowIndex, columnIndex, getPlayerSignSheet(playerNumber));
    }

    public void changeSceneToWinnerGamePage(int winningPlayerNumber) {
        pagesManager.changeSceneToWinGamePage(winningPlayerNumber, getPlayerNick(winningPlayerNumber));
    }

    private ImageView getPlayerAvatar(int playerNumber) {
        return (playerIndexIsValid(playerNumber)) ? playerViewProperties[playerNumber].getAvatar() : null;
    }

    private boolean playerIndexIsValid(int playerIndex) {
        return (playerIndex >= 0 && playerIndex < playerViewProperties.length);
    }

    private ImageSheetProperty getPlayerSignSheet(int playerNumber) {
        return (playerIndexIsValid(playerNumber)) ? playerViewProperties[playerNumber].getSignSheet() : null;
    }

    private String getPlayerNick(int playerNumber) {
        return (playerIndexIsValid(playerNumber)) ? playerViewProperties[playerNumber].getNick() : null;
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
        guiObservers.forEach(o -> o.updateGuiObserver(event));
    }

    public void clearActionNodes() {
        manager.clearActionNodes();
    }

    public void close() {
        manager.closeMainStage();
    }

    public PagesManager getPagesManager() {
        return pagesManager;
    }
}
