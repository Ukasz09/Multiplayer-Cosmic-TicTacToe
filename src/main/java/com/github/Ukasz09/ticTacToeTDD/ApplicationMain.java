package com.github.Ukasz09.ticTacToeTDD;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.GameView;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.Logger;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.GameLogic;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.TicTacToeExceptions;
import com.github.Ukasz09.ticTacToeTDD.controller.GameController;
import javafx.application.Application;
import javafx.stage.Stage;

public class ApplicationMain extends Application {
    private GameController gameController;
    GameView gameView;
    GameLogic gameLogic;

    public ApplicationMain() {
        initializeGameController();
    }

    private void initializeGameController() {
        gameView = new GameView();
        try {
            gameLogic = new GameLogic();
        } catch (TicTacToeExceptions err) {
            Logger.logError(getClass(), "Critical game logic implementation error!");
            System.exit(-1);
        }
        gameController = new GameController(gameView, gameLogic);
    }

    @Override
    public void start(Stage primaryStage) {
        gameController.startGame();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
