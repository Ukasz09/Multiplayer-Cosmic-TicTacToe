package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.gamePage;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.pages.ChoosePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.panes.OscarStatuePane;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.states.SpriteStates;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.EventKind;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.IncorrectBoardSizeException;
import javafx.geometry.Orientation;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

//todo: usuwac view z animacji po skonczeniu
public class GameBoardPage extends ChoosePage implements IEventKindObserver {
    private static final String GAME_HEADER_TEXT = "Tic-Tac-Toe";
    private static final String WINNER_HEADER_TEXT_PREFIX = "Winner: ";

    private GameBoard gameBoard;
    private PlayerInfoPage[] playerInfoPane;
    private OscarStatuePane oscarStatuePane = null;

    //-----------------------------------------------------------------------------------------------------------------//
    public GameBoardPage() {
        super(new ImageGameBackground(DEFAULT_BACKGROUND, null), GAME_HEADER_TEXT, Orientation.HORIZONTAL, 0);
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

    public boolean initializePlayerInfoPage(ImageView avatar, String nick, ImageSheetProperty signSheetProperty, int playerIndex) {
        if (playerIndex < 0)
            return false;
        double infoPageWidth = (manager.getRightFrameBorder() - gameBoard.getWidth()) / 2;
        if (playerIndex == 0) {
            playerInfoPane[playerIndex] = new PlayerInfoPage(infoPageWidth, getHeaderPaneHeight(), 0);
            setLeft(playerInfoPane[playerIndex]);
        } else {
            playerInfoPane[playerIndex] = new PlayerInfoPage(infoPageWidth, getHeaderPaneHeight(), manager.getRightFrameBorder() - infoPageWidth);
            setRight(playerInfoPane[playerIndex]);
        }
        playerInfoPane[playerIndex].initialize(avatar, nick, signSheetProperty);
        playerInfoPane[playerIndex].attachObserver(this);
        return true;
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
        if (oscarStatuePane != null)
            oscarStatuePane.update();
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
        if (oscarStatuePane != null)
            oscarStatuePane.render();
    }

    public Point2D getLastChosenBoxCoords() {
        return gameBoard.getLastChosenBoxCoords();
    }

    public void addPlayerSignToBox(int rowIndex, int columnIndex, ImageSheetProperty signSheetProperty) {
        gameBoard.addPlayerSignToBox(rowIndex, columnIndex, signSheetProperty);
    }

    public void showVisibleOnlyActualPlayer(int playerIndex) {
        for (int i = 0; i < playerInfoPane.length; i++)
            playerInfoPane[i].disablePage(playerIndex != i);
    }

    public void changeGridBoxState(SpriteStates state, int coordsX, int coordsY) {
        gameBoard.changeGridBoxState(state, coordsX, coordsY);
    }

    public void changeSceneToWinnerGamePage(int winningPlayerIndex, String playerNick) {
        int nextPlayerInfoPaneIndex = (winningPlayerIndex + 1) % playerInfoPane.length;
        playerInfoPane[nextPlayerInfoPaneIndex].setSignVisible(false);
        playerInfoPane[nextPlayerInfoPaneIndex].removeSignSpriteFromRoot();
        playerInfoPane[nextPlayerInfoPaneIndex].setVisible(false);

        oscarStatuePane = new OscarStatuePane
                (playerInfoPane[winningPlayerIndex].getWidth(), playerInfoPane[nextPlayerInfoPaneIndex].getLayoutX(), playerInfoPane[nextPlayerInfoPaneIndex].getLayoutY());
        playerInfoPane[winningPlayerIndex].setSignVisible(false);
        playerInfoPane[winningPlayerIndex].removeSignSpriteFromRoot();
        playerInfoPane[winningPlayerIndex].addWinButtons();


        if (playerInfoPane[nextPlayerInfoPaneIndex].getPagePositionX() > 0) {
            setRight(oscarStatuePane);
            System.out.println(playerInfoPane[1].getLayoutX());
        } else setLeft(oscarStatuePane);
        setWinnerHeaderText(playerNick);
    }

    private void setWinnerHeaderText(String player) {
        String headerText = WINNER_HEADER_TEXT_PREFIX + player;
        setHeaderText(headerText);
    }

    public void denyInteractionWithAllBoxes() {
        gameBoard.denyInteractionWithAllBoxes();
    }

    @Override
    public void updateObserver(EventKind eventKind) {
        notifyObservers(eventKind);
    }
}
