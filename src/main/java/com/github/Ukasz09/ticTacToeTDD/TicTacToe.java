package com.github.Ukasz09.ticTacToeTDD;

public class TicTacToe {
    public static final String NO_WINNER_MSG = "No winner";
    public static final String WINNER_MSG_PREFIX = "Winner is player: ";
    private static final int BORDER_SIZE = 3;
    private char[][] board = {
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'},
            {'\0', '\0', '\0'}
    };
    private char playerInLastTurn = 'O';

    public String markField(int x, int y) {
        playerInLastTurn = nextPlayer();
        checkAxisIsCorrect(x, 'X');
        checkAxisIsCorrect(y, 'Y');
        checkFieldIsNotMarked(x, y);
        setBox(x, y, playerInLastTurn);
        return getWinner(x, y);
    }

    private void checkAxisIsCorrect(int axis, char axisDirection) {
        if (axis < 0 || axis > 2)
            throw new RuntimeException(axisDirection + "is outside the board");
    }

    private void checkFieldIsNotMarked(int x, int y) {
        if (board[x][y] != '\0')
            throw new RuntimeException("Field is already occupied");
    }

    private void setBox(int x, int y, char sign) {
        board[x][y] = sign;
    }

    public char nextPlayer() {
        return (playerInLastTurn == 'X') ? 'O' : 'X';
    }

    private String getWinner(int lastX, int lastY) {
        return isWin(lastX, lastY) ? (WINNER_MSG_PREFIX + playerInLastTurn) : NO_WINNER_MSG;
    }

    private boolean isWin(int lastX, int lastY) {
        return checkHorizontalLineFilled(lastX) || checkVerticalLineFilled(lastY)||checkDiagonalLineFilled(lastX,lastY);
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
        while (actualPlayerMarkCount < 3 && (tmpX < BORDER_SIZE && tmpY < BORDER_SIZE && tmpX >= 0 && tmpY >= 0)) {
           if(board[tmpX][tmpY]==playerInLastTurn)
               actualPlayerMarkCount+=1;
           tmpX+=1;
           tmpY+=1;
        }

        //up
        tmpX=lastX-1;
        tmpY=lastY-1;
        while (actualPlayerMarkCount < 3 && (tmpX < BORDER_SIZE && tmpY < BORDER_SIZE && tmpX >= 0 && tmpY >= 0)) {
            if(board[tmpX][tmpY]==playerInLastTurn)
                actualPlayerMarkCount+=1;
            tmpX-=1;
            tmpY-=1;
        }

        if(actualPlayerMarkCount>=3)
            return true;

        //lewy diagonal

        //down
        actualPlayerMarkCount=0;
        tmpX=lastX;
        tmpY=lastY;
        while (actualPlayerMarkCount < 3 && (tmpX < BORDER_SIZE && tmpY < BORDER_SIZE && tmpX >= 0 && tmpY >= 0)) {
            if(board[tmpX][tmpY]==playerInLastTurn)
                actualPlayerMarkCount+=1;
            tmpX+=1;
            tmpY-=1;
        }

        //up
        tmpX=lastX-1;
        tmpY=lastY+1;
        while (actualPlayerMarkCount < 3 && (tmpX < BORDER_SIZE && tmpY < BORDER_SIZE && tmpX >= 0 && tmpY >= 0)) {
            if(board[tmpX][tmpY]==playerInLastTurn)
                actualPlayerMarkCount+=1;
            tmpX-=1;
            tmpY+=1;
        }
        return actualPlayerMarkCount>=3;

    }
}
