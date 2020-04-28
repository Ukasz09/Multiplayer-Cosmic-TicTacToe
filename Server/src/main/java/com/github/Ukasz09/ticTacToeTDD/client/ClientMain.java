package com.github.Ukasz09.ticTacToeTDD.client;

import com.github.Ukasz09.ticTacToeTDD.applicationInterface.Gui;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.Logger;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientMain extends Application {
    private ClientController clientController;

    //----------------------------------------------------------------------------------------------------------------//
    public ClientMain() {
        clientController = new ClientController(new Gui());
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            clientController.startGame();
        } catch (IOException e) {
            Logger.logError(getClass(), "Critical game runtime error!");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
