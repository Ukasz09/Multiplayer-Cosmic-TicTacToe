package com.github.Ukasz09.ticTacToe.ui.scenes.pages;

import com.github.Ukasz09.ticTacToe.ui.scenes.panes.GameResultPane;
import com.github.Ukasz09.ticTacToe.ui.scenes.GameBoard;
import com.github.Ukasz09.ticTacToe.ui.scenes.panes.PlayerInfoPane;
import com.github.Ukasz09.ticTacToe.ui.sounds.SoundsPlayer;
import com.github.Ukasz09.ticTacToe.ui.sounds.SoundsProperties;
import com.github.Ukasz09.ticTacToe.ui.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToe.ui.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.GuiEvents;
import com.github.Ukasz09.ticTacToe.logic.guiObserver.IGuiObserver;
import com.github.Ukasz09.ticTacToe.logic.gameExceptions.IncorrectBoardSizeException;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

public class GameBoardPage extends ChoosePage implements IGuiObserver {
    private static final String GAME_HEADER_TEXT = "Tic-Tac-Toe";
    private static final String WINNER_HEADER_TEXT_PREFIX = "Winner: ";
    private static final String DRAW_HEADER_TEXT = "Unlucky. It's a draw! ";
    private static final double SOUND_VOLUME = 1;
    private static final SoundsPlayer DRAW_SOUND_EFFECT = SoundsProperties.drawEffect(SOUND_VOLUME);
    private static final SoundsPlayer WIN_SOUND_EFFECT = SoundsProperties.winEffect(SOUND_VOLUME);

    private int startedPlayerId = -1;
    private GameBoard gameBoard;
    private PlayerInfoPane[] playerInfoPanes;
    private GameResultPane gameResultPane = null;

    //-----------------------------------------------------------------------------------------------------------------//
    public GameBoardPage() {
        super(StartGamePage.GAME_BACKGROUND, GAME_HEADER_TEXT, Orientation.HORIZONTAL, 0);
        playerInfoPanes = new PlayerInfoPane[2];
        initializeGameBoard();
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void initializeGameBoard() {
        gameBoard = new GameBoard(getHeaderPaneHeight());
        gameBoard.setVisible(false);
    }

    public void initializeGameGrid(int boardSize, IGuiObserver observer) {
        try {
            gameBoard.initializeGameGrid(boardSize, observer);
        } catch (IncorrectBoardSizeException e) {
            //unchecked
        }
    }

    public boolean initializePlayerInfoPage(ImageView avatar, String nick, ImageSheetProperty signSheetProperty, int playerIndex) {
        if (playerIndex < 0)
            return false;
        double infoPageWidth = (manager.getRightFrameBorder() - gameBoard.getWidth()) / 2;
        if (playerIndex == 0) {
            playerInfoPanes[playerIndex] = new PlayerInfoPane(infoPageWidth, getHeaderPaneHeight(), 0);
            setLeft(playerInfoPanes[playerIndex]);
        } else {
            playerInfoPanes[playerIndex] = new PlayerInfoPane(infoPageWidth, getHeaderPaneHeight(), manager.getRightFrameBorder() - infoPageWidth);
            setRight(playerInfoPanes[playerIndex]);
        }
        playerInfoPanes[playerIndex].initialize(avatar, nick, signSheetProperty);
        playerInfoPanes[playerIndex].attachObserver(this);
        return true;
    }

    @Override
    public void setSceneVisible(boolean value) {
        super.setSceneVisible(value);
        showGameBoard(value);
    }

    public void showGameBoard(boolean value) {
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

    public Point2D getLastChosenBoxCoords() {
        return gameBoard.getLastChosenBoxCoords();
    }

    public void addPlayerSignToBox(int rowIndex, int columnIndex, ImageSheetProperty signSheetProperty) {
        gameBoard.addPlayerSignToBox(rowIndex, columnIndex, signSheetProperty);
    }

    public void showVisibleOnlyActualPlayer(int playerIndex) {
        for (int i = 0; i < playerInfoPanes.length; i++)
            playerInfoPanes[i].disablePage(playerIndex != i);
    }

    public void changeGridBoxState(SpriteStates state, int coordsX, int coordsY) {
        gameBoard.changeGridBoxState(state, coordsX, coordsY);
    }

    public void changeSceneToWinResultPage(int winningPlayerIndex, String playerNick) {
        changeSceneToGameResultPage(getPlayerNextIndex(winningPlayerIndex));
        gameResultPane.addOscarStatue();
        setWinnerHeaderText(playerNick);
        WIN_SOUND_EFFECT.playSound();
    }

    private void changeSceneToGameResultPage(int playerIndex) {
        int nextPlayerInfoPaneIndex = getPlayerNextIndex(playerIndex);
        changePlayerInfoPaneToGameResultPane(playerIndex);
        addGameOverButtonsToPlayerInfoPane(nextPlayerInfoPaneIndex);
    }

    public void setStartedPlayerId(int playerId) {
        startedPlayerId = playerId;
    }

    private int getPlayerIndexInPane(int playerIndex) {
        return playerIndex == startedPlayerId ? 0 : 1;
    }

    private int getPlayerNextIndex(int index) {
        return (index + 1) % playerInfoPanes.length;
    }

    private void changePlayerInfoPaneToGameResultPane(int playerInfoPaneIndex) {
        playerInfoPanes[playerInfoPaneIndex].setPaneVisible(false);
        initializeGameResultPane(playerInfoPaneIndex);
        addGameResultPane(playerInfoPaneIndex);
    }

    private void initializeGameResultPane(int playerInfoPaneIndex) {
        double paneWidth = playerInfoPanes[playerInfoPaneIndex].getWidth();
        double positionX = playerInfoPanes[playerInfoPaneIndex].getLayoutX();
        double positionY = playerInfoPanes[playerInfoPaneIndex].getLayoutY();
        gameResultPane = new GameResultPane(paneWidth, positionX, positionY);
    }

    private void addGameResultPane(int playerInfoPaneIndex) {
        if (playerInfoPanes[playerInfoPaneIndex].getLayoutX() > 0) setRight(gameResultPane);
        else setLeft(gameResultPane);
    }

    private void addGameOverButtonsToPlayerInfoPane(int playerInfoPaneIndex) {
        playerInfoPanes[playerInfoPaneIndex].setSignVisible(false);
        playerInfoPanes[playerInfoPaneIndex].addGameOverButtons();
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
        changeSceneToGameResultPage(1);
        setHeaderText(DRAW_HEADER_TEXT);
        DRAW_SOUND_EFFECT.playSound();
    }

    public void interactionWithAllBoxes(boolean allowed) {
        gameBoard.interactionWithAllBoxes(allowed);
    }

    @Override
    public void updateGuiObserver(GuiEvents guiEvents) {
        notifyObservers(guiEvents);
    }
}
