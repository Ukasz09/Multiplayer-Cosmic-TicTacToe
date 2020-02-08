package com.github.Ukasz09.ticTacToeTDD.applicationInterface;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.GameBoard;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.GameMyBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.Logger;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.*;

import javafx.animation.AnimationTimer;

public class GameView {
    private final static String APPLICATION_TITLE = "Tic-Tac-Toe game";

    private MyBackground ticTacToeBoard;
    private GameBoard gameBoard;
    private ViewManager manager;

    class GameAnimationTimer extends AnimationTimer {
        @Override
        public void handle(long currentNanoTime) {
            ticTacToeBoard.render();
            gameBoard.render();
            gameBoard.update();
        }
    }

    public GameView() {
        manager = ViewManager.getInstance();
        manager.initialize(APPLICATION_TITLE, true);
    }

    public void startGame() {
        initializeGameApplication();
        manager.getMainStage().show();
    }

    private void initializeGameApplication() {
        ticTacToeBoard = new GameMyBackground();
        //todo: tmp
        try {
            makeNewGameBoard(3);
        } catch (TicTacToeExceptions e){
            Logger.logError(getClass(),"Incorrect size");
        }

        new GameAnimationTimer().start();
        ticTacToeBoard.playBackgroundSound();
    }

    public void makeNewGameBoard(int boardSize) throws IncorrectBoardSizeException {
        gameBoard=new GameBoard(boardSize);
    }
}
