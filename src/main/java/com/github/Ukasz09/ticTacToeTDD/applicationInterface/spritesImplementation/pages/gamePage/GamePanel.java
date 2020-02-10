package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.gamePage;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages.Panel;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;

public class GamePanel extends Panel {
    private GameBoard gameBoard;

    public GamePanel(MyBackground background) {
        super(background);
        initializeGameBoard();
    }

    private void initializeGameBoard() {
        gameBoard = new GameBoard();
        gameBoard.setVisible(false);
    }

    public void showGameBoard(boolean value) {
        setVisible(true);
        gameBoard.setVisible(value);
    }

    @Override
    public void update() {
        gameBoard.update();
    }
}
