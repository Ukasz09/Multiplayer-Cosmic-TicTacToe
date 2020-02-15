package com.github.Ukasz09.ticTacToeTDD.applicationInterface;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.PlayerViewProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.PagesManager;

import javafx.animation.AnimationTimer;

public class GameView {
    private final static String APPLICATION_TITLE = "Tic-Tac-Toe game";

    private int playersQty = 0;
    private int actualInitializedPlayerID = 0;
    private PlayerViewProperties[] playerViewProperties;
    private ViewManager manager;
    private PagesManager pagesManager;

    class GameAnimationTimer extends AnimationTimer {
        @Override
        public void handle(long currentNanoTime) {
            pagesManager.getActualScene().render();
            pagesManager.getActualScene().update();
        }
    }

    public GameView() {
        manager = ViewManager.getInstance();
        manager.initialize(APPLICATION_TITLE, false);
        pagesManager = new PagesManager();
    }

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
        playerViewProperties[actualInitializedPlayerID].setName(pagesManager.getLastChosenCorrectName());
        return changeIdOfInitializedPlayerToNext();
    }

    public void updateNextPlayerAvatar() {
        playerViewProperties[actualInitializedPlayerID].setAvatar(pagesManager.getLastChosenAvatar());
    }

    public void updatePlayerSignSheet() {
        playerViewProperties[actualInitializedPlayerID].setSignSheetProperty(pagesManager.getLastChosenSignSheet());
    }

    public boolean changeToNextPlayer() {
        boolean hasNextPlayer = changeIdOfInitializedPlayerToNext();
        pagesManager.setActualInitializedPlayerNick(playerViewProperties[actualInitializedPlayerID].getName());
        return hasNextPlayer;
    }

    private boolean changeIdOfInitializedPlayerToNext() {
        actualInitializedPlayerID++;
        if (actualInitializedPlayerID >= playersQty) {
            actualInitializedPlayerID = 0;
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
        pagesManager.showGamePage();
    }
}
