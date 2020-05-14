package com.github.Ukasz09.ticTacToe.logic.databaseConnection;

import org.jongo.marshall.jackson.oid.Id;

import java.util.Objects;

public class TicTacToeBean {
    @Id
    private int turn;
    private int cordX;
    private int cordY;
    private char player;

    //----------------------------------------------------------------------------------------------------------------//
    public TicTacToeBean(int turn, int cordX, int cordY, char player) {
        this.turn = turn;
        this.cordX = cordX;
        this.cordY = cordY;
        this.player = player;
    }

    //----------------------------------------------------------------------------------------------------------------//
    public int getTurn() {
        return turn;
    }

    public int getCordX() {
        return cordX;
    }

    public int getCordY() {
        return cordY;
    }

    public char getPlayer() {
        return player;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicTacToeBean that = (TicTacToeBean) o;
        return turn == that.turn &&
                cordX == that.cordX &&
                cordY == that.cordY &&
                player == that.player;
    }

    @Override
    public int hashCode() {
        return Objects.hash(turn, cordX, cordY, player);
    }

    @Override
    public String toString() {
        return "TicTacToeBean{" +
                "turn=" + turn +
                ", cordX=" + cordX +
                ", cordY=" + cordY +
                ", player=" + player +
                '}';
    }
}
