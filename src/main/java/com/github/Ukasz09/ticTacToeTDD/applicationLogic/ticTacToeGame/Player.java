package com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame;

import java.util.Objects;

public class Player {
    private char identifier;

    public Player(char identifier) {
        this.identifier = identifier;
    }

    public char getIdentifier() {
        return identifier;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return identifier == player.identifier;
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
