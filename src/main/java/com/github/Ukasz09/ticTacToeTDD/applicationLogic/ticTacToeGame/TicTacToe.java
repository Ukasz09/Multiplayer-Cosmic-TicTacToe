package com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame;

import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.IncorrectBoardSizeException;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.IncorrectFieldException;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.IncorrectPlayerException;

import java.util.*;

public class TicTacToe {
    public static final String NO_WINNER_MSG = "Game over. No winner";
    public static final String DRAW_MSG = "Game over. It's draw";
    public static final String WINNER_MSG_PREFIX = "Game over. Winner is player: ";

    public static final int DEFAULT_BOARD_SIZE = 3;
    public static final char[] DEFAULT_PLAYERS_IDENTIFIERS = {'X', 'O'};
    private static final char EMPTY_BOARD_MARK = '\0';

    private int actualPlayerOffset = 1;
    private List<Player> players;
    private int boardSize = DEFAULT_BOARD_SIZE;
    private char[][] board;

    public TicTacToe() throws IncorrectBoardSizeException, IncorrectPlayerException {
        this(DEFAULT_BOARD_SIZE);
    }

    public TicTacToe(int boardSize) throws IncorrectBoardSizeException, IncorrectPlayerException {
        this(boardSize, new Player[]{
                new Player(DEFAULT_PLAYERS_IDENTIFIERS[0]),
                new Player(DEFAULT_PLAYERS_IDENTIFIERS[1])
        });
    }

    public TicTacToe(int boardSize, Player[] playersToInitialize) throws IncorrectBoardSizeException, IncorrectPlayerException {
        initializeBoard(boardSize);
        initializePlayers(playersToInitialize);
    }

    private void initializeBoard(int boardSize) throws IncorrectBoardSizeException {
        if (boardSize < DEFAULT_BOARD_SIZE)
            throw new IncorrectBoardSizeException();

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

    public String markField(int x, int y) throws IncorrectFieldException {
        changePlayerToNext();
        checkAxisIsCorrect(x);
        checkAxisIsCorrect(y);
        checkFieldIsNotMarked(x, y);
        setBox(x, y, (players.get(actualPlayerOffset)).getIdentifier());
        return getWinner(x, y);
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

    private String getWinner(int lastX, int lastY) {
        if (isWin(lastX, lastY))
            return (WINNER_MSG_PREFIX + (players.get(actualPlayerOffset)).getIdentifier());
        return cantDoAnyMove() ? DRAW_MSG : NO_WINNER_MSG;
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
        while (tmpOffset < DEFAULT_BOARD_SIZE) {
            boolean isMarkedByPlayer;
            if (horizontal)
                isMarkedByPlayer = board[lastOffset][tmpOffset] == byWhichPlayer;
            else isMarkedByPlayer = board[tmpOffset][lastOffset] == byWhichPlayer;

            if (isMarkedByPlayer)
                actualPlayerMarkCount += 1;
            else actualPlayerMarkCount = 0;

            if (actualPlayerMarkCount >= 3)
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

        return actualPlayerMarkCount >= 3;
    }

    private int markedQtyInDiagonalFromGivenFields(boolean down, boolean right, int startedOffsetX, int startedOffsetY, char byWhichPlayer) {
        int actualPlayerMarkCount = 0;
        while (actualPlayerMarkCount < 3 && !pointIsOutsideTheBoard(startedOffsetX, startedOffsetY)) {
            if (board[startedOffsetX][startedOffsetY] != byWhichPlayer)
                return actualPlayerMarkCount;
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
}
