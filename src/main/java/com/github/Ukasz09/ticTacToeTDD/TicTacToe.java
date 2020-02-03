package com.github.Ukasz09.ticTacToeTDD;

import com.github.Ukasz09.ticTacToeTDD.ticTacToeExceptions.IncorrectBoardException;

import java.util.Arrays;

public class TicTacToe {
    public static final String NO_WINNER_MSG = "Game over. No winner";
    public static final String DRAW_MSG = "Game over. It's draw";
    public static final String WINNER_MSG_PREFIX = "Game over. Winner is player: ";
    private static final int DEFAULT_BOARD_SIZE = 3;
    private static final char EMPTY_BOARD_MARK = '\0';

    private char playerInLastTurn = 'O';
    private int boardSize = DEFAULT_BOARD_SIZE;
    private char[][] board;

    public TicTacToe() throws IncorrectBoardException {
        initializeBoard(DEFAULT_BOARD_SIZE);
    }

    public TicTacToe(int boardSize) throws IncorrectBoardException {
        initializeBoard(boardSize);
    }

    private void initializeBoard(int boardSize) throws IncorrectBoardException {
        if (boardSize < DEFAULT_BOARD_SIZE)
            throw new IncorrectBoardException();

        this.boardSize = boardSize;
        board = new char[boardSize][boardSize];
        for (char[] chars : board)
            Arrays.fill(chars, EMPTY_BOARD_MARK);
    }

    public String markField(int x, int y) {
        playerInLastTurn = nextPlayer();
        checkAxisIsCorrect(x, 'X');
        checkAxisIsCorrect(y, 'Y');
        checkFieldIsNotMarked(x, y);
        setBox(x, y, playerInLastTurn);
        return getWinner(x, y);
    }

    private void checkAxisIsCorrect(int axis, char axisDirection) {
        if (axis < 0 || axis > boardSize - 1)
            throw new RuntimeException(axisDirection + "is outside the board");
    }

    private void checkFieldIsNotMarked(int x, int y) {
        if (board[x][y] != EMPTY_BOARD_MARK)
            throw new RuntimeException("Field is already occupied");
    }

    private void setBox(int x, int y, char sign) {
        board[x][y] = sign;
    }

    //todo: dodac klase playera
    public char nextPlayer() {
        return (playerInLastTurn == 'X') ? 'O' : 'X';
    }

    private String getWinner(int lastX, int lastY) {
        if (isWin(lastX, lastY))
            return (WINNER_MSG_PREFIX + playerInLastTurn);
        return cantDoAnyMove() ? DRAW_MSG : NO_WINNER_MSG;
    }

    private boolean isWin(int lastX, int lastY) {
        return checkHorizontalLineFilled(lastX) || checkVerticalLineFilled(lastY) || checkDiagonalLineFilled(lastX, lastY);
    }

    //todo: przerobic zeby dzialalo dla wiekszych plansz
    private boolean checkHorizontalLineFilled(int lastX) {
        return (board[lastX][0] + board[lastX][1] + board[lastX][2]) == 3 * playerInLastTurn;
    }

    private boolean checkVerticalLineFilled(int lastY) {
        return (board[0][lastY] + board[1][lastY] + board[2][lastY]) == 3 * playerInLastTurn;
    }

    //todo: tmp. Refactor zrobic
    private boolean checkDiagonalLineFilled(int lastX, int lastY) {
        int actualPlayerMarkCount = 0;
        int tmpX = lastX, tmpY = lastY;

        //prawy diagonal

        //down
        while (actualPlayerMarkCount < 3 && (tmpX < DEFAULT_BOARD_SIZE && tmpY < DEFAULT_BOARD_SIZE && tmpX >= 0 && tmpY >= 0)) {
            if (board[tmpX][tmpY] == playerInLastTurn)
                actualPlayerMarkCount += 1;
            tmpX += 1;
            tmpY += 1;
        }

        //up
        tmpX = lastX - 1;
        tmpY = lastY - 1;
        while (actualPlayerMarkCount < 3 && (tmpX < DEFAULT_BOARD_SIZE && tmpY < DEFAULT_BOARD_SIZE && tmpX >= 0 && tmpY >= 0)) {
            if (board[tmpX][tmpY] == playerInLastTurn)
                actualPlayerMarkCount += 1;
            tmpX -= 1;
            tmpY -= 1;
        }

        if (actualPlayerMarkCount >= 3)
            return true;

        //lewy diagonal

        //down
        actualPlayerMarkCount = 0;
        tmpX = lastX;
        tmpY = lastY;
        while (actualPlayerMarkCount < 3 && (tmpX < DEFAULT_BOARD_SIZE && tmpY < DEFAULT_BOARD_SIZE && tmpX >= 0 && tmpY >= 0)) {
            if (board[tmpX][tmpY] == playerInLastTurn)
                actualPlayerMarkCount += 1;
            tmpX += 1;
            tmpY -= 1;
        }

        //up
        tmpX = lastX - 1;
        tmpY = lastY + 1;
        while (actualPlayerMarkCount < 3 && (tmpX < DEFAULT_BOARD_SIZE && tmpY < DEFAULT_BOARD_SIZE && tmpX >= 0 && tmpY >= 0)) {
            if (board[tmpX][tmpY] == playerInLastTurn)
                actualPlayerMarkCount += 1;
            tmpX -= 1;
            tmpY += 1;
        }
        return actualPlayerMarkCount >= 3;
    }

    private boolean cantDoAnyMove() {
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board[i].length; j++)
                if (board[i][j] == EMPTY_BOARD_MARK)
                    return false;
        return true;
    }
}
