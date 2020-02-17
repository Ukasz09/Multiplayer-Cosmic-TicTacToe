package com.github.Ukasz09.ticTacToeTDD.applicationInterface;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.PlayerViewProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.PagesManager;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

public class GameView {
    private final static String APPLICATION_TITLE = "Tic-Tac-Toe game";

    private int playersQty = 0;
    private int actualPlayerID = 0;
    private PlayerViewProperties[] playerViewProperties;
    private ViewManager manager;
    private PagesManager pagesManager;

    //----------------------------------------------------------------------------------------------------------------//
    class GameAnimationTimer extends AnimationTimer {
        @Override
        public void handle(long currentNanoTime) {
            pagesManager.getActualScene().render();
            pagesManager.getActualScene().update();
        }
    }

    public GameView() {
        manager = ViewManager.getInstance();
        manager.initialize(APPLICATION_TITLE, true);
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
        return changeIdOfInitializedPlayerToNext();
    }

    public void updateNextPlayerAvatar() {
        playerViewProperties[actualPlayerID].setAvatar(pagesManager.getLastChosenAvatar());
    }

    public void updatePlayerSignSheet() {
        playerViewProperties[actualPlayerID].setSignSheetProperty(pagesManager.getLastChosenSignSheet());
    }

    public boolean changeToNextPlayer() {
        boolean hasNextPlayer = changeIdOfInitializedPlayerToNext();
        pagesManager.setActualInitializedPlayerNick(playerViewProperties[actualPlayerID].getName());
        return hasNextPlayer;
    }

    private boolean changeIdOfInitializedPlayerToNext() {
        actualPlayerID++;
        if (actualPlayerID >= playersQty) {
            actualPlayerID = 0;
            return false;
        }
        return true;
    }

    public void attachObserverToPagesManager(IEventKindObserver observer) {
        pagesManager.attachObserver(observer);
    }

    public void detachObserverToPagesManager(IEventKindObserver observer) {
        pagesManager.detachObserver(observer);
    }

    public void showHomePage() {
        pagesManager.showHomePage();
    }

    public void showBoardSizeChoosePage() {
        pagesManager.showBoardSizeChoosePage();
    }

    public void showNickChoosePage() {
        pagesManager.showNickChoosePage();
    }

    public void showAvatarChoosePage() {
        String firstPlayerNick = playerViewProperties[0].getName();
        pagesManager.showAvatarChoosePage(firstPlayerNick);
    }

    public void showSignChoosePage() {
        String firstPlayerNick = playerViewProperties[0].getName();
        pagesManager.showSignChoosePage(firstPlayerNick);
    }

    public void showGamePage() {
        pagesManager.showGamePage(actualPlayerID);
    }

    public void initializeGameBoard(ImageView avatar1, ImageView avatar2, ImageSheetProperty sign1, ImageSheetProperty sign2, String nick1, String nick2, int boardSize) {
        pagesManager.initializeGameBoard(avatar1, avatar2, sign1, sign2, nick1, nick2, boardSize);
    }

    public int getGameBoardSize() {
        return pagesManager.getGameBoardSize();
    }

    public ImageView getPlayerAvatar(int playerIndex) {
        if (playerIndexIsValid(playerIndex))
            return playerViewProperties[playerIndex].getAvatar();
        return null;
    }

    public Point2D getLastChosenBoxCoords() {
        return pagesManager.getLastChosenBoxCoords();
    }

    public void addSignToBox(int rowIndex, int columnIndex) {
        pagesManager.addSignToBox(rowIndex, columnIndex, getPlayerSignSheet(actualPlayerID));
    }

    public ImageSheetProperty getPlayerSignSheet(int playerIndex) {
        if (playerIndexIsValid(playerIndex))
            return playerViewProperties[playerIndex].getSignSheetProperty();
        return null;
    }

    public String getPlayerNick(int playerIndex) {
        if (playerIndexIsValid(playerIndex))
            return playerViewProperties[playerIndex].getName();
        return null;
    }

    private boolean playerIndexIsValid(int playerIndex) {
        return (playerIndex >= 0 && playerIndex < playerViewProperties.length);
    }

    public void showVisibleOnlyActualPlayer() {
        pagesManager.showVisibleOnlyActualPlayer(actualPlayerID);
    }
}
