package com.github.Ukasz09.ticTacToe;

import com.github.Ukasz09.ticTacToe.controller.ClientController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientMain extends Application {
    private ClientController clientController;

    //----------------------------------------------------------------------------------------------------------------//
    public ClientMain() {
        clientController = new ClientController();
    }

    @Override
    public void start(Stage primaryStage) {
        clientController.startServerPage();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
