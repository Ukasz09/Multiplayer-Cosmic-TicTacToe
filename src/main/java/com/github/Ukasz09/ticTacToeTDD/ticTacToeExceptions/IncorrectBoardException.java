package com.github.Ukasz09.ticTacToeTDD.ticTacToeExceptions;

public class IncorrectBoardException extends Exception {
    private static final String DEFAULT_MSG = "Incorrect board size";

    public IncorrectBoardException() {
        super(DEFAULT_MSG);
    }

    public IncorrectBoardException(String message) {
        super(message);
    }
}
