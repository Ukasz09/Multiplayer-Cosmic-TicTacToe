package com.github.Ukasz09.ticTacToeTDD.applicationLogic.game;

import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.*;

import java.awt.*;
import java.util.*;
import java.util.List;

public class GameLogic {
    public static final int DEFAULT_MARKS_QTY_FOR_WIN = 3;
    public static final int DEFAULT_BOARD_SIZE = 3;
    public static final char[] DEFAULT_PLAYERS_IDENTIFIERS = {'X', 'O'};
    private static final char EMPTY_BOARD_MARK = '\0';

    private int actualPlayerOffset = 1;
    private List<Player> players;
    private int boardSize = DEFAULT_BOARD_SIZE;
    private char[][] board;
    private int marksQtyForWin = DEFAULT_MARKS_QTY_FOR_WIN;
    private Point[] winningCoords;
    private int actualOffsetInWinningCoordsArr = 0;

    public GameLogic() throws IncorrectPlayerException, IncorrectBoardSizeException {
        this(DEFAULT_BOARD_SIZE);
    }

    public GameLogic(int boardSize) throws IncorrectBoardSizeException, IncorrectPlayerException {
        this(boardSize, DEFAULT_MARKS_QTY_FOR_WIN, new Player[]{
                new Player(DEFAULT_PLAYERS_IDENTIFIERS[0]),
                new Player(DEFAULT_PLAYERS_IDENTIFIERS[1])
        });
    }

    public GameLogic(int boardSize, int marksQtyForWin, Player[] playersToInitialize) throws IncorrectBoardSizeException, IncorrectPlayerException {
        resetBoard(boardSize, marksQtyForWin);
        initializePlayers(playersToInitialize);
        initializeWinningCoords();
    }

    //----------------------------------------------------------------------------------------------------------------//
    private void initializeWinningCoords() {
        winningCoords = new Point[marksQtyForWin];
        for (int i = 0; i < marksQtyForWin; i++)
            winningCoords[i] = new Point(-1, -1);
    }

    public void resetBoard(int boardSize) throws IncorrectBoardSizeException {
        resetBoard(boardSize, DEFAULT_MARKS_QTY_FOR_WIN);
    }

    public void resetBoard(int boardSize, int marksQtyForWin) throws IncorrectBoardSizeException {
        if (boardSize < DEFAULT_BOARD_SIZE)
            throw new IncorrectBoardSizeException();
        this.marksQtyForWin = Math.max(marksQtyForWin, DEFAULT_MARKS_QTY_FOR_WIN);


        this.boardSize = boardSize;
        board = new char[boardSize][boardSize];
        for (char[] chars : board)
            Arrays.fill(chars, EMPTY_BOARD_MARK);
    }

    private void initializePlayers(Player[] playersToInitialize) throws IncorrectPlayerException {
        players = new ArrayList<>();
        for (Player player : playersToInitialize) {
            if (players.contains(player))
                throw new IncorrectPlayerException();
            players.add(player);
        }
    }

    public boolean addPlayer(char identifier) {
        Player newPlayer = new Player(identifier);
        if (players.contains(newPlayer))
            return false;
        players.add(newPlayer);
        return true;
    }

    public GameResult markField(int x, int y) throws IncorrectFieldException {
        changePlayerToNext();
        checkAxisIsCorrect(x);
        checkAxisIsCorrect(y);
        checkFieldIsNotMarked(x, y);
        setBox(x, y, (players.get(actualPlayerOffset)).getIdentifier());
        return getResult(x, y);
    }

    public void changePlayerToNext() {
        actualPlayerOffset = ((actualPlayerOffset + 1) % players.size());
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

    private GameResult getResult(int lastX, int lastY) {
        if (isWin(lastX, lastY))
            return actualPlayerOffset == 0 ? GameResult.WIN_PLAYER_0 : GameResult.WIN_PLAYER_1;
        return cantDoAnyMove() ? GameResult.DRAW : GameResult.GAME_NOT_FINISHED;
    }

    private boolean isWin(int lastX, int lastY) {
        return checkHorizontalLineFilled(lastX) || checkVerticalLineFilled(lastY) || checkDiagonalLineFilled(lastX, lastY, (players.get(actualPlayerOffset)).getIdentifier());
    }

    private boolean checkHorizontalLineFilled(int lastX) {
        return straightLineIsFilled(true, lastX, (players.get(actualPlayerOffset)).getIdentifier());
    }

    private boolean checkVerticalLineFilled(int lastY) {
        return straightLineIsFilled(false, lastY, (players.get(actualPlayerOffset)).getIdentifier());
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

            if (isMarkedByPlayer) {
                actualPlayerMarkCount += 1;
            } else actualPlayerMarkCount = 0;

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
        int actualPlayerMarkCount = markedQtyInDiagonalFromGivenFields(true, right, lastX, lastY, byWhichPlayer);
        int startedOffsetY = right ? lastY - 1 : lastY + 1;
        actualPlayerMarkCount += markedQtyInDiagonalFromGivenFields(false, right, lastX - 1, startedOffsetY, byWhichPlayer);

        return actualPlayerMarkCount >= marksQtyForWin;
    }

    private int markedQtyInDiagonalFromGivenFields(boolean down, boolean right, int startedOffsetX, int startedOffsetY, char byWhichPlayer) {
        int actualPlayerMarkCount = 0;
        while (actualPlayerMarkCount < marksQtyForWin && !pointIsOutsideTheBoard(startedOffsetX, startedOffsetY)) {
            if (board[startedOffsetX][startedOffsetY] != byWhichPlayer)
                return actualPlayerMarkCount;
            addPointToWinningArr(startedOffsetX, startedOffsetY);
            actualPlayerMarkCount += 1;
            if (down) {
                startedOffsetX += 1;
                startedOffsetY += right ? 1 : (-1);
            } else {
                startedOffsetX -= 1;
                startedOffsetY += right ? -1 : (+1);
            }
        }
        return actualPlayerMarkCount;
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

    public char getLastPlayerId() {
        return (players.get(actualPlayerOffset)).getIdentifier();
    }

    public int getPlayersQty() {
        return players.size();
    }

    public Point[] getWinningCoords() {
        return winningCoords;
    }

    private void addPointToWinningArr(int coordsX, int coordsY) {
        winningCoords[actualOffsetInWinningCoordsArr % marksQtyForWin].setLocation(coordsX, coordsY);
        actualOffsetInWinningCoordsArr++;
    }
}
