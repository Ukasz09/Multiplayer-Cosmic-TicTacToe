package com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions;

public class IncorrectBoardSizeException extends TicTacToeExceptions {
    private static final String DEFAULT_MSG = "Incorrect board size";

    public IncorrectBoardSizeException() {
        super(DEFAULT_MSG);
    }

    public IncorrectBoardSizeException(String message) {
        super(message);
    }
}
