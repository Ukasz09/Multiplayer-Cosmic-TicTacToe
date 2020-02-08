package com.github.Ukasz09.ticTacToeTDD.applicationInterface;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.Background;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.GameBackground;
import javafx.animation.AnimationTimer;

public class GameView {
    private final static String APPLICATION_TITLE = "Tic-Tac-Toe game";

    private Background ticTacToeBoard;
    private ViewManager manager;

    class GameAnimationTimer extends AnimationTimer {
        @Override
        public void handle(long currentNanoTime) {
            ticTacToeBoard.render();
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
        ticTacToeBoard = new GameBackground();
        new GameAnimationTimer().start();
        ticTacToeBoard.playBackgroundSound();
    }
}
