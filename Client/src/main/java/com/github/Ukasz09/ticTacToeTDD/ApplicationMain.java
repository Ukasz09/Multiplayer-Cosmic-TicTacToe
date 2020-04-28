package com.github.Ukasz09.ticTacToeTDD;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.GameView;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.Logger;
import com.github.Ukasz09.ticTacToeTDD.client.ClientController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMain extends Application {
    private ClientController clientController;

    //----------------------------------------------------------------------------------------------------------------//
    public ApplicationMain() {
        clientController = new ClientController(new GameView());
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            clientController.startGame();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
