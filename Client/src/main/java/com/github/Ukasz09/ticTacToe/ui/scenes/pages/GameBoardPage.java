package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.ui.Gui;
import com.github.Ukasz09.ticTacToe.ui.scenes.panes.*;
import com.github.Ukasz09.ticTacToe.ui.scenes.GameBoard;
import com.github.Ukasz09.ticTacToe.ui.sounds.SoundsPlayer;
import com.github.Ukasz09.ticTacToe.ui.sounds.SoundsProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObserver;
import com.github.Ukasz09.ticTacToe.logic.gameExceptions.IncorrectBoardSizeException;
import javafx.geometry.Orientation;
import javafx.scene.image.ImageView;

public class GameBoardPage extends ChoosePage implements IGuiObserver {
    public static final String ACTUAL_PLAYER_MOVE_HEADER_TXT = "Your move";
    public static final String OPPONENT_MOVE_HEADER_TXT = "Opponent move";
    private static final String WIN_HEADER = "You win !";
    private static final String LOSE_HEADER = "You lose !";
    private static final String DRAW_HEADER_TEXT = "Unlucky. It's a draw! ";
    private static final double SOUND_VOLUME = 1;

    private GameBoard gameBoard;
    private PlayerInfoPane[] playerInfoPanes;
    private GameResultPane gameResultPane = null;
    private SoundsPlayer soundsPlayer;


    //-----------------------------------------------------------------------------------------------------------------//
    public GameBoardPage() {
        super(StartGamePage.GAME_BACKGROUND, ACTUAL_PLAYER_MOVE_HEADER_TXT, Orientation.HORIZONTAL, 0);
        playerInfoPanes = new PlayerInfoPane[2];
        gameBoard = new GameBoard(getHeaderPaneHeight());
    }

    //-----------------------------------------------------------------------------------------------------------------//
    public void initializeGameGrid(int boardSize, IGuiObserver observer) {
        try {
            gameBoard.initializeGameGrid(boardSize, observer);
        } catch (IncorrectBoardSizeException e) {
            //unchecked
        }
        gameBoard.setVisible(true);
    }

    public void initLeftPlayerPane(ImageView avatar, String nick, ImageSheetProperty signSheet, int playerNumber) {
        double infoPaneWidth = (manager.getRightFrameBorder() - gameBoard.getWidth()) / 2;
        playerInfoPanes[playerNumber] = new PlayerInfoPane(infoPaneWidth, getHeaderPaneHeight(), 0);
        playerInfoPanes[playerNumber].initialize(avatar, nick, signSheet);
        playerInfoPanes[playerNumber].attachObserver(this);
        setLeft(playerInfoPanes[playerNumber]);
    }

    public void initRightPlayerPane(ImageView avatar, String nick, ImageSheetProperty signSheet, int playerNumber) {
        double infoPaneWidth = (manager.getRightFrameBorder() - gameBoard.getWidth()) / 2;
        playerInfoPanes[playerNumber] = new PlayerInfoPane(infoPaneWidth, getHeaderPaneHeight(), manager.getRightFrameBorder() - infoPaneWidth);
        playerInfoPanes[playerNumber].initialize(avatar, nick, signSheet);
        playerInfoPanes[playerNumber].attachObserver(this);
        setRight(playerInfoPanes[playerNumber]);
    }

    @Override
    public void setSceneVisible(boolean value) {
        super.setSceneVisible(value);
        gameBoard.setVisible(value);
    }

    @Override
    public void update() {
        gameBoard.update();
        updatePlayerInfoPage();
        updateWinnerGamePage();
    }

    private void updatePlayerInfoPage() {
        for (PlayerInfoPane playerInfoPane : this.playerInfoPanes)
            playerInfoPane.update();
    }

    private void updateWinnerGamePage() {
        if (gameResultPane != null)
            gameResultPane.update();
    }

    @Override
    public void render() {
        super.render();
        gameBoard.render();
        renderWinnerGamePage();
        renderPlayerInfoPage();
    }

    private void renderPlayerInfoPage() {
        for (PlayerInfoPane playerInfoPane : this.playerInfoPanes)
            playerInfoPane.render();
    }

    private void renderWinnerGamePage() {
        if (gameResultPane != null)
            gameResultPane.render();
    }

    public void showVisibleOnlyActualPlayer(int playerIndex) {
        for (int i = 0; i < playerInfoPanes.length; i++)
            playerInfoPanes[i].disablePane(playerIndex != i);
    }

    public void sceneToWinResultPage(int winningPlayerNumber) {
        int nextPlayerPaneIndex = Gui.getNextPlayerNumber(winningPlayerNumber);
        initWinGameResultPane(nextPlayerPaneIndex);
        changePaneToGameResult(nextPlayerPaneIndex);
        addGameOverButtonsToPane(winningPlayerNumber);
        setHeaderText(WIN_HEADER);
        soundsPlayer = SoundsProperties.winEffect(SOUND_VOLUME); //to prevent from garbage collector destroy before sound end
        soundsPlayer.playSound();
    }

    public void sceneToLoseResultPage(int losePlayerNumber) {
        int nextPlayerPaneIndex = Gui.getNextPlayerNumber(losePlayerNumber);
        initLoseGameResultPane(nextPlayerPaneIndex);
        changePaneToGameResult(nextPlayerPaneIndex);
        addGameOverButtonsToPane(losePlayerNumber);
        setHeaderText(LOSE_HEADER);
        soundsPlayer = SoundsProperties.drawEffect(SOUND_VOLUME); //to prevent from garbage collector destroy before sound end
        soundsPlayer.playSound();
    }

    private void changePaneToGameResult(int paneIndex) {
        playerInfoPanes[paneIndex].setPaneVisible(false);
        addGameResultPane(paneIndex);
    }

    private void initWinGameResultPane(int paneIndex) {
        double paneWidth = playerInfoPanes[paneIndex].getWidth();
        double positionX = playerInfoPanes[paneIndex].getLayoutX();
        double positionY = playerInfoPanes[paneIndex].getLayoutY();
        if (playerInfoPanes[paneIndex].getLayoutX() <= 0)
            gameResultPane = new WinGameResultPane(paneWidth, positionX, positionY, false);
        else gameResultPane = new WinGameResultPane(paneWidth, positionX, positionY, true);
    }

    private void initLoseGameResultPane(int paneIndex) {
        double paneWidth = playerInfoPanes[paneIndex].getWidth();
        double positionX = playerInfoPanes[paneIndex].getLayoutX();
        double positionY = playerInfoPanes[paneIndex].getLayoutY();
        if (playerInfoPanes[paneIndex].getLayoutX() <= 0)
            gameResultPane = new LoseGameResultPane(paneWidth, positionX, positionY, true);
        else gameResultPane = new LoseGameResultPane(paneWidth, positionX, positionY, false);
    }

    private void initDrawResultPane(int paneIndex) {
        double paneWidth = playerInfoPanes[paneIndex].getWidth();
        double positionX = playerInfoPanes[paneIndex].getLayoutX();
        double positionY = playerInfoPanes[paneIndex].getLayoutY();
        gameResultPane = new DrawResultPane(paneWidth, positionX, positionY);
    }

    private void addGameResultPane(int paneIndex) {
        if (playerInfoPanes[paneIndex].getLayoutX() > 0)
            setRight(gameResultPane);
        else setLeft(gameResultPane);
    }

    private void addGameOverButtonsToPane(int paneIndex) {
        playerInfoPanes[paneIndex].setSignVisible(false);
        playerInfoPanes[paneIndex].addGameOverButtons();
    }

    public void changeSceneToDrawResultPage() {
        playerInfoPanes[0].setSignVisible(false);
        playerInfoPanes[0].removeAvatarNode();
        playerInfoPanes[0].removeNickFieldNode();
        playerInfoPanes[0].centerButtonInPane();
        addGameOverButtonsToPane(0);
        initDrawResultPane(1);
        changePaneToGameResult(1);
        setHeaderText(DRAW_HEADER_TEXT);
        soundsPlayer = SoundsProperties.drawEffect(SOUND_VOLUME); //to prevent from garbage collector destroy before sound end
        soundsPlayer.playSound();
    }

    public void animationIsEnable(boolean value) {
        gameBoard.animationIsEnable(value);
    }

    @Override
    public void updateGuiObserver(GuiEvents guiEvents) {
        notifyObservers(guiEvents);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
