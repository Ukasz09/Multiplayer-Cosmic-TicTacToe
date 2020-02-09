package com.github.Ukasz09.ticTacToeTDD.applicationInterface;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesAbstraction.IDrawingGraphic;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.GameBoard;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.backgrounds.MyBackground;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.spritesImplementation.panels.SignChoosePanel;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.*;

import javafx.animation.AnimationTimer;

public class GameView {
    private final static String APPLICATION_TITLE = "Tic-Tac-Toe game";

    private MyBackground gameBackground;
    private GameBoard gameBoard;

    private ViewManager manager;
    private IDrawingGraphic actualScene;

    class GameAnimationTimer extends AnimationTimer {
        @Override
        public void handle(long currentNanoTime) {
//            gameBackground.render();
//            gameBoard.render();
//            gameBoard.update();
            actualScene.render();
            actualScene.update();
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
//        gameBackground = new GameBackground();
//        //todo: tmp
//        try {
//            makeNewGameBoard(3);
//        } catch (TicTacToeExceptions e){
//            Logger.logError(getClass(),"Incorrect size");
//        }
//        gameBackground.playBackgroundSound();

        setSceneToNewGame();
        new GameAnimationTimer().start();
    }

    private void setSceneToNewGame(){
        actualScene=new SignChoosePanel();
    }


    public void makeNewGameBoard(int boardSize) throws IncorrectBoardSizeException {
        gameBoard=new GameBoard(boardSize);
    }
}
