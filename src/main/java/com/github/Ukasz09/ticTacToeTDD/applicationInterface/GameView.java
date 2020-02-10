package com.github.Ukasz09.ticTacToeTDD.applicationInterface;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.others.PlayerViewProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.PagesManager;

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

    public boolean updateNextPlayerSignSheet(ImageSheetProperty signSheetProperties) {
        playerViewProperties[actualInitializedPlayerID].setSignSheetProperty(signSheetProperties);
        return changeIdOfInitializedPlayerToNext();
    }

    public PagesManager getPagesManager() {
        return pagesManager;
    }

    private boolean changeIdOfInitializedPlayerToNext() {
        actualInitializedPlayerID++;
        if (actualInitializedPlayerID >= playersQty) {
            actualInitializedPlayerID = 0;
            return false;
        }
        return true;
    }
}
