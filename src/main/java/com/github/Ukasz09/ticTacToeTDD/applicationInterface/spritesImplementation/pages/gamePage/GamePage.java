package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.gamePage;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages.LabelPane;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages.Page;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;

public class GamePage extends Page {
    private GameBoard gameBoard;

    public GamePage(MyBackground background) {
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
