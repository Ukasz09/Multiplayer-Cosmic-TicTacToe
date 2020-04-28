package com.github.Ukasz09.ticTacToe.logic.gameExceptions;

public class IncorrectBoardSizeException extends TicTacToeExceptions {
    private static final String DEFAULT_MSG = "Incorrect board size";

    public IncorrectBoardSizeException() {
        super(DEFAULT_MSG);
    }
}
