package com.github.Ukasz09.ticTacToeTDD.server;

import com.github.Ukasz09.ticTacToeTDD.applicationLogic.Logger;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.GameLogic;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.TicTacToeExceptions;

import java.io.IOException;

public class ServerMain {
    private ServerController serverController;

    //----------------------------------------------------------------------------------------------------------------//
    public ServerMain() {
        try {
            serverController = new ServerController(new GameLogic());
        } catch (TicTacToeExceptions e) {
            Logger.logError(getClass(), "Critical game logic implementation error!");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public void start() {
        try {
            serverController.startGame();
        } catch (IOException e) {
            Logger.logError(getClass(), "Critical game runtime error!");
            e.printStackTrace();
            System.exit(-1);
        }
    }

    public static void main(String[] args) {
        new ServerMain().start();
    }
}
