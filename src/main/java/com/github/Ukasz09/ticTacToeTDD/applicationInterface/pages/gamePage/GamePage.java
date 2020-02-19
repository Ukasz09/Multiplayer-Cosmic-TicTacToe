package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.gamePage;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.ChoosePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.WinnerGamePane;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.IncorrectBoardSizeException;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

//todo: usuwac view z animacji po skonczeniu
public class GamePage extends ChoosePage {
    private static final String GAME_HEADER_TEXT = "Tic-Tac-Toe";
    private static final String WINNER_HEADER_TEXT_PREFIX = "Winner: ";

    private GameBoard gameBoard;
    private PlayerInfoPage[] playerInfoPane;
    private WinnerGamePane winnerGamePane = null;

    //-----------------------------------------------------------------------------------------------------------------//
    public GamePage() {
        super(new ImageGameBackground(DEFAULT_BACKGROUND, null), GAME_HEADER_TEXT);
        playerInfoPane = new PlayerInfoPage[2];
        initializeGameBoard();
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void initializeGameBoard() {
        gameBoard = new GameBoard(getHeaderPaneHeight());
        gameBoard.setVisible(false);
    }

    public void initializeGameGrid(int boardSize, IEventKindObserver observer) {
        try {
            gameBoard.initializeGameGrid(boardSize, observer);
        } catch (IncorrectBoardSizeException e) {
            //unchecked
        }
    }

    public void initializePlayerInfoPage(ImageView avatar, String nick, ImageSheetProperty signSheetProperty, boolean left) {
        double pageWidth = (manager.getRightFrameBorder() - gameBoard.getWidth()) / 2;
        if (left) {
            playerInfoPane[0] = new PlayerInfoPage(pageWidth, getHeaderPaneHeight(), 0);
            setLeft(playerInfoPane[0]);
            playerInfoPane[0].initialize(avatar, nick, signSheetProperty);
        } else {
            playerInfoPane[1] = new PlayerInfoPage(pageWidth, getHeaderPaneHeight(), manager.getRightFrameBorder() - pageWidth);
            setRight(playerInfoPane[1]);
            playerInfoPane[1].initialize(avatar, nick, signSheetProperty);
        }

    }

    @Override
    public void setSceneVisible(boolean value) {
        super.setSceneVisible(value);
        showGameBoard(value);
    }

    public void showGameBoard(boolean value) {
        setVisible(true);
        gameBoard.setVisible(value);
    }

    @Override
    public void update() {
        gameBoard.update();
        updatePlayerInfoPage();
        updateWinnerGamePage();
    }

    private void updatePlayerInfoPage() {
        for (PlayerInfoPage playerInfoPage : playerInfoPane)
            playerInfoPage.update();
    }

    private void updateWinnerGamePage() {
        if (winnerGamePane != null)
            winnerGamePane.update();
    }

    @Override
    public void render() {
        super.render();
        gameBoard.render();
        renderWinnerGamePage();
        renderPlayerInfoPage();
    }

    private void renderPlayerInfoPage() {
        for (PlayerInfoPage playerInfoPage : playerInfoPane)
            playerInfoPage.render();
    }

    private void renderWinnerGamePage() {
        if (winnerGamePane != null)
            winnerGamePane.render();
    }

    public Point2D getLastChosenBoxCoords() {
        return gameBoard.getLastChosenBoxCoords();
    }

    public void addSignToBox(int rowIndex, int columnIndex, ImageSheetProperty signSheetProperty) {
        gameBoard.addSignToBox(rowIndex, columnIndex, signSheetProperty);
    }

    public void showVisibleOnlyActualPlayer(int playerIndex) {
        for (int i = 0; i < playerInfoPane.length; i++)
            playerInfoPane[i].disablePage(playerIndex != i);
    }

    public void changeGridBoxState(SpriteStates state, int coordsX, int coordsY) {
        gameBoard.changeGridBoxState(state, coordsX, coordsY);
    }

    public void addWinnerGamePage(int winningPlayerIndex) {
        int nextPlayerInfoPaneIndex = (winningPlayerIndex + 1) % playerInfoPane.length;
        playerInfoPane[nextPlayerInfoPaneIndex].setSignVisible(false);
        playerInfoPane[nextPlayerInfoPaneIndex].removeSignSpriteFromRoot();
        playerInfoPane[nextPlayerInfoPaneIndex].setVisible(false);

        winnerGamePane = new WinnerGamePane(playerInfoPane[winningPlayerIndex].getWidth(),
                getHeaderPaneHeight(), playerInfoPane[nextPlayerInfoPaneIndex].getPagePositionX(), playerInfoPane[winningPlayerIndex].getPagePositionX());
        playerInfoPane[winningPlayerIndex].setSignVisible(false);
        playerInfoPane[winningPlayerIndex].removeSignSpriteFromRoot();
        playerInfoPane[winningPlayerIndex].addWinButtons();
        playerInfoPane[winningPlayerIndex].addConfetti(manager.getRightFrameBorder(), manager.getBottomFrameBorder());

        if (playerInfoPane[nextPlayerInfoPaneIndex].getPagePositionX() > 0)
            setRight(winnerGamePane);
        else setLeft(winnerGamePane);
    }

    public void setWinnerHeaderText(String player) {
        String headerText = WINNER_HEADER_TEXT_PREFIX + player;
        setHeaderText(headerText);
    }

}
