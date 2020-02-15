package com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.gamePage;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.sprites.properties.ImagesProperties;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.ImageGameBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.pages.Page;
import javafx.scene.image.Image;

public class GamePage extends Page {
    private static final Image BACKGROUND = ImagesProperties.cosmic1Background();
    private GameBoard gameBoard;

    public GamePage() {
        super(new ImageGameBackground(BACKGROUND, null));
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

    @Override
    public void render() {
        super.render();
        gameBoard.render();
    }
}
