package com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.gamePage;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.pages.choosePages.Page;
import javafx.scene.image.Image;

public class GamePage extends Page {
    private static final Image BACKGROUND = ImagesProperties.cosmicBackground1();
    private GameBoard gameBoard;

    public GamePage() {
        super(new MyBackground(BACKGROUND, null));
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
