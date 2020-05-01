package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.ui.Gui;
import com.github.Ukasz09.ticTacToe.ui.scenes.panes.GameResultPane;
import com.github.Ukasz09.ticTacToe.ui.scenes.GameBoard;
import com.github.Ukasz09.ticTacToe.ui.scenes.panes.PlayerInfoPane;
import com.github.Ukasz09.ticTacToe.ui.scenes.panes.WinGameResultPane;
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
    private static final String WINNER_HEADER_TEXT_PREFIX = "Winner: ";
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

    public void sceneToWinResultPage(int winningPlayerNumber, String playerNick) {
        int nextPlayerPaneIndex = Gui.getNextPlayerNumber(winningPlayerNumber);
        changePaneToGameResult(nextPlayerPaneIndex);
        addGameOverButtonsToPane(winningPlayerNumber);
        setWinnerHeaderText(playerNick);
        soundsPlayer = SoundsProperties.winEffect(SOUND_VOLUME); //to prevent from garbage collector destroy before sound end
        soundsPlayer.playSound();
    }

    private void changePaneToGameResult(int paneIndex) {
        playerInfoPanes[paneIndex].setPaneVisible(false);
        initWinGameResultPane(paneIndex);
        addGameResultPane(paneIndex);
    }

    private void initWinGameResultPane(int paneIndex) {
        double paneWidth = playerInfoPanes[paneIndex].getWidth();
        double positionX = playerInfoPanes[paneIndex].getLayoutX();
        double positionY = playerInfoPanes[paneIndex].getLayoutY();
        gameResultPane = new WinGameResultPane(paneWidth, positionX, positionY);
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

    private void setWinnerHeaderText(String player) {
        String headerText = WINNER_HEADER_TEXT_PREFIX + player;
        setHeaderText(headerText);
    }

    public void changeSceneToDrawResultPage() {
        playerInfoPanes[0].setSignVisible(false);
        playerInfoPanes[0].removeAvatarNode();
        playerInfoPanes[0].removeNickFieldNode();
        playerInfoPanes[0].centerButtonInPane();
        addGameOverButtonsToPane(0);
        changePaneToGameResult(1);
        setHeaderText(DRAW_HEADER_TEXT);
        soundsPlayer = SoundsProperties.drawEffect(SOUND_VOLUME); //to prevent from garbage collector destroy before sound end
        soundsPlayer.playSound();
    }

    @Override
    public void updateGuiObserver(GuiEvents guiEvents) {
        notifyObservers(guiEvents);
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
