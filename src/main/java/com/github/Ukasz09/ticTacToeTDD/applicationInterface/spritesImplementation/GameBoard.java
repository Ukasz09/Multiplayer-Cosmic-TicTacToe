package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.*;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class GameBoard implements IDrawingGraphic {
    private static final int DEFAULT_BOARD_SIZE = 3;

    private ViewManager manager;
    private GridPane gameGrid;

    public GameBoard(int boardSize) throws IncorrectBoardSizeException {
        manager = ViewManager.getInstance();
        initializeGameGrid(boardSize);
    }

    public GameBoard() {
        manager = ViewManager.getInstance();
        try {
            initializeGameGrid(DEFAULT_BOARD_SIZE);
        } catch (IncorrectBoardSizeException e) {
            //unchecked
        }
    }

    private void initializeGameGrid(int boardSize) throws IncorrectBoardSizeException {
        if (boardSize < DEFAULT_BOARD_SIZE)
            throw new IncorrectBoardSizeException();
        gameGrid = new GridPane();
        for (int row = 0; row < boardSize; row++)
            for (int column = 0; column < boardSize; column++)
                gameGrid.add(new Button(), column, row);
        manager.addNode(gameGrid);
    }

    public void setVisible(boolean value){
        gameGrid.setVisible(value);
    }

    @Override
    public void render() {
        //todo
    }

    @Override
    public void update() {
        //todo
    }
}
