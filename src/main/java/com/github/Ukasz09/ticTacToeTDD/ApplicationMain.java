package com.github.Ukasz09.ticTacToeTDD;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.ViewManager;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.Background;
import com.github.Ukasz09.ticTacToeTDD.applicationInterface.backgrounds.GameBackground;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationMain extends Application {
    private final static String APPLICATION_TITLE = "Tic-Tac-Toe game";

    private ViewManager manager;
    private Background ticTacToeBoard; //todo: tmp

    public ApplicationMain() {
        manager = ViewManager.getInstance();
        manager.initialize(APPLICATION_TITLE, true);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage = manager.getMainStage();
        initializeApplication();

        class gameAnimationTimer extends AnimationTimer {
            @Override
            public void handle(long currentNanoTime) {
                ticTacToeBoard.render();
            }
        }
        new gameAnimationTimer().start();
        primaryStage.show();
    }

    private void initializeApplication() {
        ticTacToeBoard = new GameBackground();
        ticTacToeBoard.playBackgroundSound();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
