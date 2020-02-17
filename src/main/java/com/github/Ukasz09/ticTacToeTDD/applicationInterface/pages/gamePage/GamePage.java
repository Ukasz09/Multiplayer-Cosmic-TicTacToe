package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.gamePage;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.ChoosePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.IncorrectBoardSizeException;
import javafx.geometry.Point2D;
import javafx.scene.image.ImageView;

//todo: usuwac view z animacji po skonczeniu
public class GamePage extends ChoosePage {
    private static final String HEADER_TEXT = "Tic-Tac-Toe";

    private GameBoard gameBoard;
    private PlayerInfoPage[] playerInfoPage;

    //-----------------------------------------------------------------------------------------------------------------//
    public GamePage() {
        super(new ImageGameBackground(DEFAULT_BACKGROUND, null), HEADER_TEXT);
        playerInfoPage = new PlayerInfoPage[2];
        initializeGameBoard();
    }

    //-----------------------------------------------------------------------------------------------------------------//
    private void initializeGameBoard() {
        gameBoard = new GameBoard(getLabelPaneHeight());
        gameBoard.setVisible(false);
    }

    public void initializeGameGrid(int boardSize, IEventKindObserver observer) {
        try {
            gameBoard.initializeGameGrid(boardSize, observer);
        } catch (IncorrectBoardSizeException e) {
            //unchecked
        }
    }

    public void initializePlayerInfoPage(ImageView avatar, ImageSheetProperty signSheetProperty, boolean left) {
        double pageWidth = (manager.getRightFrameBorder()-gameBoard.getWidth())/2;
        if (left) {
            playerInfoPage[0] = new PlayerInfoPage(pageWidth, getLabelPaneHeight(), 0);
            setLeft(playerInfoPage[0]);
            playerInfoPage[0].initialize(avatar, signSheetProperty);
        } else {
            playerInfoPage[1] = new PlayerInfoPage(pageWidth, getLabelPaneHeight(), manager.getRightFrameBorder() - pageWidth);
            setRight(playerInfoPage[1]);
            playerInfoPage[1].initialize(avatar, signSheetProperty);
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
    }

    private void updatePlayerInfoPage() {
        for (PlayerInfoPage playerInfoPage : playerInfoPage)
            playerInfoPage.update();
    }

    @Override
    public void render() {
        super.render();
        gameBoard.render();
        renderPlayerInfoPage();
    }

    private void renderPlayerInfoPage() {
        for (PlayerInfoPage playerInfoPage : playerInfoPage)
            playerInfoPage.render();
    }


    public Point2D getLastChosenBoxCoords() {
        return gameBoard.getLastChosenBoxCoords();
    }

    public void addSignToBox(int rowIndex, int columnIndex, ImageSheetProperty signSheetProperty) {
        gameBoard.addSignToBox(rowIndex, columnIndex, signSheetProperty);
    }
}
