package com.github.Ukasz09.ticTacToe;

import com.github.Ukasz09.ticTacToe.controller.ServerController;
import com.github.Ukasz09.ticTacToe.controller.Logger;
import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeDatabase;
import com.github.Ukasz09.ticTacToe.logic.game.GameLogic;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.TicTacToeExceptions;
import com.mongodb.MongoException;

import java.io.IOException;
import java.net.UnknownHostException;

public class ServerMain {
    private ServerController serverController;

    //----------------------------------------------------------------------------------------------------------------//
    public ServerMain() {
        try {
            serverController = new ServerController(new GameLogic(new TicTacToeDatabase()));
        } catch (TicTacToeExceptions e) {
            Logger.logError(getClass(), "Can't initialize server controller: " + e.getMessage());
            e.printStackTrace();
            System.exit(-1);
        } catch (UnknownHostException e) {
            Logger.logError(getClass(), "Can't connect with database: " + e.getMessage());
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
