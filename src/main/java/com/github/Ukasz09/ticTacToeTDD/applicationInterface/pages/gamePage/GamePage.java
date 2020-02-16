package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.gamePage;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.GameBoxButtonSprite;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.SignButtonSprite;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.choosePages.ChoosePage;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImageSheetProperty;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.Page;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.eventObservers.IEventKindObserver;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.IncorrectBoardSizeException;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

public class GamePage extends ChoosePage {
    private static final String HEADER_TEXT = "Tic-Tac-Toe";

    private GameBoard gameBoard;

    //-----------------------------------------------------------------------------------------------------------------//
    public GamePage() {
        super(new ImageGameBackground(DEFAULT_BACKGROUND, null), HEADER_TEXT);
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
    }

    @Override
    public void render() {
        super.render();
        gameBoard.render();
    }

    public Point2D getLastChosenBoxCoords() {
        return gameBoard.getLastChosenBoxCoords();
    }

    public void addSignToBox(int rowIndex, int columnIndex, ImageSheetProperty signSheetProperty) {
        gameBoard.addSignToBox(rowIndex, columnIndex, signSheetProperty);
    }
}