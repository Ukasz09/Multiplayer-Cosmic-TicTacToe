package com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions;

public class IncorrectPlayerException extends TicTacToeExceptions{
    private static final String DEFAULT_MSG = "Incorrect player appeared";

    public IncorrectPlayerException() {
        super(DEFAULT_MSG);
    }

    public IncorrectPlayerException(String message) {
        super(message);
    }
}