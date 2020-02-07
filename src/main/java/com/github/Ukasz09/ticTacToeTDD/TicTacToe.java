package com.github.Ukasz09.ticTacToeTDD;

import com.github.Ukasz09.ticTacToeTDD.ticTacToeExceptions.IncorrectBoardException;

import java.util.Arrays;

public class TicTacToe {
    public static final String NO_WINNER_MSG = "Game over. No winner";
    public static final String DRAW_MSG = "Game over. It's draw";
    public static final String WINNER_MSG_PREFIX = "Game over. Winner is player: ";
    public static final int DEFAULT_BOARD_SIZE = 3;
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

    private boolean checkHorizontalLineFilled(int lastX) {
        return straightLineIsFilled(true, lastX, playerInLastTurn);
    }

    private boolean checkVerticalLineFilled(int lastY) {
        return straightLineIsFilled(false, lastY, playerInLastTurn);
    }

    /**
     * @param byPlayer   -  which player filled line we check
     * @param lastOffset - if horizontal then lastXOffset else lastYOffset
     */
    private boolean straightLineIsFilled(boolean horizontal, int lastOffset, char byPlayer) {
        int tmpOffset = 0;
        int actualPlayerMarkCount = 0;

        while (tmpOffset < DEFAULT_BOARD_SIZE) {
            boolean isMarkedByPlayer;
            if (horizontal)
                isMarkedByPlayer = board[lastOffset][tmpOffset] == byPlayer;
            else isMarkedByPlayer = board[tmpOffset][lastOffset] == byPlayer;
            if (isMarkedByPlayer)
                actualPlayerMarkCount += 1;
            else actualPlayerMarkCount = 0;

            if (actualPlayerMarkCount >= 3)
                return true;

            tmpOffset += 1;
        }
        return false;
    }

    private boolean checkDiagonalLineFilled(int lastX, int lastY) {
        return diagonalIsFilled(true, lastX, lastY) || diagonalIsFilled(false, lastX, lastY);
    }

    private boolean diagonalIsFilled(boolean right, int lastX, int lastY) {
        int actualPlayerMarkCount = 0;
        int tmpX = lastX, tmpY = lastY;
        //down
        while (actualPlayerMarkCount < 3 && (tmpX < DEFAULT_BOARD_SIZE && tmpY < DEFAULT_BOARD_SIZE && tmpX >= 0 && tmpY >= 0)) {
            if (board[tmpX][tmpY] == playerInLastTurn)
                actualPlayerMarkCount += 1;
            else break;
            tmpX += 1;
            if (right)
                tmpY += 1;
            else tmpY -= 1;
        }

        //up
        tmpX = lastX - 1;
        if (right)
            tmpY = lastY - 1;
        else tmpY = lastY + 1;
        while (actualPlayerMarkCount < 3 && (tmpX < DEFAULT_BOARD_SIZE && tmpY < DEFAULT_BOARD_SIZE && tmpX >= 0 && tmpY >= 0)) {
            if (board[tmpX][tmpY] == playerInLastTurn)
                actualPlayerMarkCount += 1;
            else break;
            tmpX -= 1;
            if (right)
                tmpY -= 1;
            else tmpY += 1;
        }

        return actualPlayerMarkCount >= 3;
    }

    private boolean cantDoAnyMove() {
        for (int row = 0; row < board.length; row++)
            for (int column = 0; column < board[row].length; column++)
                if (board[row][column] == EMPTY_BOARD_MARK)
                    return false;
        return true;
    }

    public int getBoardSize() {
        return boardSize;
    }

    public char getPlayerInLastTurn() {
        return playerInLastTurn;
    }
}
