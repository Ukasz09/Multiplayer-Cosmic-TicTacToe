package com.github.Ukasz09.ticTacToe;

import com.github.Ukasz09.ticTacToe.ui.Gui;
import com.github.Ukasz09.ticTacToe.controller.ClientController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ApplicationMain extends Application {
    private ClientController clientController;

    //----------------------------------------------------------------------------------------------------------------//
    public ApplicationMain() {
        clientController = new ClientController(new Gui());
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
