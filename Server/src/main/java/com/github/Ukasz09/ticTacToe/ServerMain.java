package com.github.Ukasz09.ticTacToe;

import com.github.Ukasz09.ticTacToe.controller.ServerController;
import com.github.Ukasz09.ticTacToe.controller.Logger;
import com.github.Ukasz09.ticTacToe.logic.game.GameLogic;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.TicTacToeExceptions;

import java.io.IOException;

public class ServerMain {
    private ServerController serverController;

    //----------------------------------------------------------------------------------------------------------------//
    public ServerMain() {
        try {
            serverController = new ServerController(new GameLogic());
        } catch (TicTacToeExceptions e) {
            Logger.logError(getClass(), "Can't initialize server controller: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void start() {
        try {
            serverController.startGame();
        } catch (IOException e) {
            Logger.logError(getClass(), "Can't run server controller: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        }
    }

    //----------------------------------------------------------------------------------------------------------------//
    public static void main(String[] args) {
        new ServerMain().start();
    }
}
