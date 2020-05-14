// TODO: 14.05.2020 zmienic by drop by wykonywany dla gry z poszczegolnym id

package com.github.Ukasz09.ticTacToe.logic.game;

import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeBean;
import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeDatabase;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.IncorrectBoardSizeException;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.IncorrectFieldException;
import com.mongodb.MongoException;

import java.awt.*;
import java.util.*;

public class GameLogic {
    public static final int DEFAULT_MARKS_QTY_FOR_WIN = 3;
    public static final int DEFAULT_BOARD_SIZE = 3;
    public static final char[] PLAYERS_ID = {'X', 'O'};
    private static final char EMPTY_BOARD_MARK = '\0';
    private char actualPlayer = PLAYERS_ID[1];
    private int boardSize = DEFAULT_BOARD_SIZE;
    private char[][] board;
    private int marksQtyForWin = DEFAULT_MARKS_QTY_FOR_WIN;
    private Point[] winningCoords;
    private int actualOffsetInWinningCoordsArr = 0;
    private TicTacToeDatabase database;
    private int actualTurnNumber = 0;

    //----------------------------------------------------------------------------------------------------------------//
    public GameLogic(TicTacToeDatabase database) throws IncorrectBoardSizeException {
        this(DEFAULT_BOARD_SIZE, database);
    }

    public GameLogic(int boardSize, TicTacToeDatabase database) throws IncorrectBoardSizeException {
        this(boardSize, DEFAULT_MARKS_QTY_FOR_WIN, database);
    }

    public GameLogic(int boardSize, int marksQtyForWin, TicTacToeDatabase database) throws IncorrectBoardSizeException {
        this.database = database;
        resetBoard(boardSize, marksQtyForWin);
        if (!database.drop())
            throw new MongoException("Can't clear database");
    }

    //----------------------------------------------------------------------------------------------------------------//
    public void resetBoard(int boardSize) throws IncorrectBoardSizeException {
        resetBoard(boardSize, DEFAULT_MARKS_QTY_FOR_WIN);
    }

    public void resetBoard(int boardSize, int marksQtyForWin) throws IncorrectBoardSizeException {
        this.marksQtyForWin = Math.max(marksQtyForWin, DEFAULT_MARKS_QTY_FOR_WIN);
        initWinningCoords();
        if (boardSize < DEFAULT_BOARD_SIZE)
            throw new IncorrectBoardSizeException();

        this.boardSize = boardSize;
        board = new char[boardSize][boardSize];
        for (char[] chars : board)
            Arrays.fill(chars, EMPTY_BOARD_MARK);
    }

    private void initWinningCoords() {
        winningCoords = new Point[marksQtyForWin];
        for (int i = 0; i < marksQtyForWin; i++)
            winningCoords[i] = new Point(-1, -1);
    }

    public GameResults play(int x, int y) throws IncorrectFieldException {
        changePlayerToNext();
        checkAxisIsCorrect(x);
        checkAxisIsCorrect(y);
        checkFieldIsNotMarked(x, y);
        setBox(x, y, actualPlayer);
        if (!getDatabase().saveMove(new TicTacToeBean(++actualTurnNumber, x, y, actualPlayer)))
            throw new MongoException("Can't save data into database");
        return getResult(x, y);
    }

    public void changePlayerToNext() {
        actualPlayer = getNextPlayer(actualPlayer);
    }

    public char getNextPlayer(char actualPlayer) {
        return actualPlayer == PLAYERS_ID[0] ? PLAYERS_ID[1] : PLAYERS_ID[0];
    }

    public static int getPlayerId(char playerSign) {
        return playerSign == PLAYERS_ID[0] ? 0 : 1;
    }

    private void checkAxisIsCorrect(int offset) throws IncorrectFieldException {
        if (offset < 0 || offset > boardSize - 1)
            throw new IncorrectFieldException("Offset=" + offset + " is outside the board");
    }

    private void checkFieldIsNotMarked(int x, int y) throws IncorrectFieldException {
        if (board[x][y] != EMPTY_BOARD_MARK)
            throw new IncorrectFieldException("Field is already occupied");
    }

    private void setBox(int x, int y, char sign) {
        board[x][y] = sign;
    }

    private GameResults getResult(int lastX, int lastY) {
        if (isWin(lastX, lastY))
            return actualPlayer == PLAYERS_ID[0] ? GameResults.WIN_PLAYER_0 : GameResults.WIN_PLAYER_1;
        return cantDoAnyMove() ? GameResults.DRAW : GameResults.GAME_NOT_FINISHED;
    }

    private boolean isWin(int lastX, int lastY) {
        return checkHorizontalLineFilled(lastX) || checkVerticalLineFilled(lastY) || checkDiagonalLineFilled(lastX, lastY, actualPlayer);
    }

    private boolean checkHorizontalLineFilled(int lastX) {
        return straightLineIsFilled(true, lastX, actualPlayer);
    }

    private boolean checkVerticalLineFilled(int lastY) {
        return straightLineIsFilled(false, lastY, actualPlayer);
    }

    /**
     * @param byWhichPlayer -  which player filled line we check
     * @param lastOffset    - if horizontal then lastXOffset else lastYOffset
     */
    private boolean straightLineIsFilled(boolean horizontal, int lastOffset, char byWhichPlayer) {
        int tmpOffset = 0;
        int actualPlayerMarkCount = 0;
        while (tmpOffset < boardSize) {
            boolean isMarkedByPlayer;
            if (horizontal) {
                isMarkedByPlayer = board[lastOffset][tmpOffset] == byWhichPlayer;
                addPointToWinningArr(lastOffset, tmpOffset);
            } else {
                isMarkedByPlayer = board[tmpOffset][lastOffset] == byWhichPlayer;
                addPointToWinningArr(tmpOffset, lastOffset);
            }

            if (isMarkedByPlayer)
                actualPlayerMarkCount += 1;
            else actualPlayerMarkCount = 0;

            if (actualPlayerMarkCount >= marksQtyForWin)
                return true;
            tmpOffset += 1;
        }
        return false;
    }

    private boolean checkDiagonalLineFilled(int lastX, int lastY, char byWhichPlayer) {
        return diagonalIsFilled(true, lastX, lastY, byWhichPlayer) || diagonalIsFilled(false, lastX, lastY, byWhichPlayer);
    }

    private boolean diagonalIsFilled(boolean right, int lastX, int lastY, char byWhichPlayer) {
        int actualPlayerMarkCount = markedQtyInDiagonalFromGivenFields(0, true, right, lastX, lastY, byWhichPlayer);
        int startedOffsetY = right ? lastY - 1 : lastY + 1;
        actualPlayerMarkCount = markedQtyInDiagonalFromGivenFields(actualPlayerMarkCount, false, right, lastX - 1, startedOffsetY, byWhichPlayer);

        return actualPlayerMarkCount >= marksQtyForWin;
    }

    private int markedQtyInDiagonalFromGivenFields(int startedPlayerMarkCountQty, boolean down, boolean right, int startedOffsetX, int startedOffsetY, char byWhichPlayer) {
        while (startedPlayerMarkCountQty < marksQtyForWin && !pointIsOutsideTheBoard(startedOffsetX, startedOffsetY)) {
            if (board[startedOffsetX][startedOffsetY] != byWhichPlayer)
                return startedPlayerMarkCountQty;
            addPointToWinningArr(startedOffsetX, startedOffsetY);
            startedPlayerMarkCountQty += 1;
            if (down) {
                startedOffsetX += 1;
                startedOffsetY += right ? 1 : (-1);
            } else {
                startedOffsetX -= 1;
                startedOffsetY += right ? -1 : (+1);
            }
        }
        return startedPlayerMarkCountQty;
    }

    private boolean pointIsOutsideTheBoard(int offsetX, int offsetY) {
        try {
            checkAxisIsCorrect(offsetX);
        } catch (IncorrectFieldException e) {
            return true;
        }
        try {
            checkAxisIsCorrect(offsetY);
        } catch (IncorrectFieldException e) {
            return true;
        }
        return false;
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

    public char getLastPlayerSignId() {
        return actualPlayer;
    }

    public Point[] getWinningCoords() {
        return winningCoords;
    }

    private void addPointToWinningArr(int coordsX, int coordsY) {
        winningCoords[actualOffsetInWinningCoordsArr % marksQtyForWin].setLocation(coordsX, coordsY);
        actualOffsetInWinningCoordsArr++;
    }

    public int getActualPlayerIndex() {
        return actualPlayer == PLAYERS_ID[0] ? 0 : 1;
    }

    public void setActualPlayerID(char playerSign) {
        this.actualPlayer = playerSign;
    }

    public TicTacToeDatabase getDatabase() {
        return database;
    }
}
