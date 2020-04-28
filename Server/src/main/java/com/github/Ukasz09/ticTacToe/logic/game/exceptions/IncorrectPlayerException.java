package com.github.Ukasz09.ticTacToe.logic.game.exceptions;

public class IncorrectPlayerException extends TicTacToeExceptions{
    private static final String DEFAULT_MSG = "Incorrect player appeared";

    public IncorrectPlayerException() {
        super(DEFAULT_MSG);
    }
}
