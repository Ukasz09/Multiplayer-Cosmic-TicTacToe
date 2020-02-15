package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.gamePage;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.control.buttons.GameBoxButtonSprite;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.*;

import javafx.scene.layout.GridPane;

public class GameBoard implements IDrawingGraphic {
    private static final int DEFAULT_BOARD_SIZE = 3;

    private ViewManager manager;
    private GameBoxButtonSprite[] boxButtonSprite;

    //-----------------------------------------------------------------------------------------------------------------//
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

    //todo: refactor zrobic
    //-----------------------------------------------------------------------------------------------------------------//
    private void initializeGameGrid(int boardSize) throws IncorrectBoardSizeException {
        boxButtonSprite = new GameBoxButtonSprite[boardSize * boardSize];
        if (boardSize < DEFAULT_BOARD_SIZE)
            throw new IncorrectBoardSizeException();
        double buttonWidth = manager.getScaledWidth(GameBoxButtonSprite.SIZE_PROPORTION);
        double startedPositionX = getFirstButtonXPositionToCenterWithOthers(boardSize, 0, buttonWidth);
        double actualPositionX;
        double actualPositionY = 0;
        int offset = 0;
        for (int row = 0; row < boardSize; row++) {
            actualPositionX = startedPositionX;
            actualPositionY += buttonWidth;
            for (int column = 0; column < boardSize; column++) {
                GameBoxButtonSprite box = new GameBoxButtonSprite();
                box.setPositionX(actualPositionX);
                box.setPositionY(actualPositionY);
                boxButtonSprite[offset] = box;

                actualPositionX += buttonWidth;
                offset++;
            }
        }
    }

    public void setVisible(boolean value) {
        for (GameBoxButtonSprite box: boxButtonSprite)
            box.setImageViewVisible(value);
    }

    @Override
    public void render() {
        for (GameBoxButtonSprite box: boxButtonSprite)
            box.render();
    }

    @Override
    public void update() {
        for (GameBoxButtonSprite box: boxButtonSprite)
            box.update();
    }

    protected double getFirstButtonXPositionToCenterWithOthers(int buttonsQty, double buttonsPadding, double buttonWidth) {
        return (manager.getRightFrameBorder() - buttonsQty * buttonWidth - (buttonsQty - 1) * buttonsPadding) / 2;
    }
}
