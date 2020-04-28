package com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions;

public class IncorrectFieldException extends TicTacToeExceptions {
    private static final String DEFAULT_MSG = "Incorrect field";
    public IncorrectFieldException() {
        super(DEFAULT_MSG);
    }

    public IncorrectFieldException(String message) {
        super(message);
    }
}
