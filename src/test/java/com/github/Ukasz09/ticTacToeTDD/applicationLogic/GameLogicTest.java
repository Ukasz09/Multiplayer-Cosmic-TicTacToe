package com.github.Ukasz09.ticTacToeTDD.applicationLogic;

import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.Player;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.GameLogic;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.*;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {
    private GameLogic gameLogic;

    @BeforeEach
    final void initializeTicTac() throws IncorrectBoardSizeException, IncorrectPlayerException {
        gameLogic = new GameLogic();
    }

    @Nested
    class MarkFieldTest {
        @Test
        void whenXOutsideBoardThenException() {
            assertThrows(IncorrectFieldException.class, () -> gameLogic.markField(gameLogic.getBoardSize(), 2));
        }

        @Test
        void whenYOutsideBoardThenException() {
            assertThrows(IncorrectFieldException.class, () -> gameLogic.markField(1, gameLogic.getBoardSize()));
        }

        @Test
        void whenOccupiedThenException() throws IncorrectFieldException {
            gameLogic.markField(1, 2);
            assertThrows(IncorrectFieldException.class, () -> gameLogic.markField(1, 2));
        }
    }

    @Nested
    class NextPlayerChoiceTest {
        @Test
        void givenFirstTurnWhenNextPlayerThenX() throws IncorrectFieldException {
            gameLogic.markField(0, 0); //X
            assertEquals('X', gameLogic.getLastPlayerId());
        }

        @Test
        void givenWasXWhenNextPlayerThenO() throws IncorrectFieldException {
            gameLogic.markField(1, 1); //X
            gameLogic.markField(1, 2); //O
            assertEquals('O', gameLogic.getLastPlayerId());
        }

        @Test
        void givenWasOWhenNextPlayerThenX() throws IncorrectFieldException {
            gameLogic.markField(1, 1); //X
            gameLogic.markField(0, 0); //O
            gameLogic.markField(0, 2); //X
            assertEquals('X', gameLogic.getLastPlayerId());
        }
    }

    @Nested
    class WinnerTest {
        @Test
        void whenFirstTurnThenNoWinner() throws IncorrectFieldException {
            String winner = gameLogic.markField(0, 0);
            assertEquals(GameLogic.NO_WINNER_MSG, winner);
        }

        @Test
        void whenHorizontalLineThenWinner() throws IncorrectFieldException {
            gameLogic.markField(0, 0); //X
            gameLogic.markField(1, 1); //O
            gameLogic.markField(0, 1); //X
            gameLogic.markField(2, 1); //O
            String winner = gameLogic.markField(0, 2); //X
            assertEquals(GameLogic.WINNER_MSG_PREFIX + "X", winner);
        }

        @Test
        void whenVerticalLineThenWinner() throws IncorrectFieldException {
            gameLogic.markField(0, 0); //X
            gameLogic.markField(1, 1); //O
            gameLogic.markField(0, 2); //X
            gameLogic.markField(2, 1); //O
            gameLogic.markField(1, 0); //X
            String winner = gameLogic.markField(0, 1); //O
            assertEquals(GameLogic.WINNER_MSG_PREFIX + "O", winner);
        }

        @Test
        void whenRightDiagonalLineThenWinner() throws IncorrectFieldException {
            gameLogic.markField(0, 0); //X
            gameLogic.markField(1, 0); //O
            gameLogic.markField(1, 1); //X
            gameLogic.markField(2, 1); //O
            String winner = gameLogic.markField(2, 2); //X
            assertEquals(GameLogic.WINNER_MSG_PREFIX + "X", winner);
        }


        @Test
        void whenLeftDiagonalLineThenWinner() throws IncorrectFieldException {
            gameLogic.markField(0, 1); //X
            gameLogic.markField(0, 2); //O
            gameLogic.markField(1, 2); //X
            gameLogic.markField(1, 1); //O
            gameLogic.markField(2, 1); //X
            String winner = gameLogic.markField(2, 0); //O
            assertEquals(GameLogic.WINNER_MSG_PREFIX + "O", winner);
        }

        @Test
        void whenNoFullLineThenNoWinner() throws IncorrectFieldException {
            gameLogic.markField(0, 0); //X
            gameLogic.markField(0, 1); //O
            gameLogic.markField(0, 2); //X
            gameLogic.markField(1, 2); //O
            gameLogic.markField(1, 0); //X
            gameLogic.markField(1, 1); //O
            String winner = gameLogic.markField(2, 1); //X
            assertEquals(GameLogic.NO_WINNER_MSG, winner);
        }

        @Test
        void givenBoardSize3WhenAllBoxesFiledAndNoWinnerThenDraw() throws IncorrectBoardSizeException, IncorrectFieldException, IncorrectPlayerException {
            gameLogic = new GameLogic(3);
            gameLogic.markField(0, 0); //X
            gameLogic.markField(0, 1); //O
            gameLogic.markField(0, 2); //X
            gameLogic.markField(1, 2); //O
            gameLogic.markField(1, 0); //X
            gameLogic.markField(1, 1); //O
            gameLogic.markField(2, 1); //X
            gameLogic.markField(2, 0); //O
            String lastTurn = gameLogic.markField(2, 2); //X
            assertEquals(GameLogic.DRAW_MSG, lastTurn);
        }

        @Test
        void whenAllBoxesFiledAndIsWinnerThenWinner() throws IncorrectFieldException {
            gameLogic.markField(0, 0); //X
            gameLogic.markField(0, 1); //O
            gameLogic.markField(0, 2); //X
            gameLogic.markField(1, 2); //O
            gameLogic.markField(1, 0); //X
            gameLogic.markField(1, 1); //O
            gameLogic.markField(2, 1); //X
            gameLogic.markField(2, 2); //O
            String winner = gameLogic.markField(2, 0); //X
            assertEquals(GameLogic.WINNER_MSG_PREFIX + "X", winner);
        }
    }

    @Nested
    class InitializationTest {
        @Test
        void whenIncorrectBoardSizeThenIncorrectBoardException() {
            assertThrows(IncorrectBoardSizeException.class, () -> gameLogic = new GameLogic(2));
        }

        @Test
        void whenAddedPlayerWithTheSameIdentifierThenFalse() {
            gameLogic.addPlayer('W');
            assertFalse(gameLogic.addPlayer('W'));
        }

        @Test
        void whenAdded2PlayersWithDifferentIdentifierThenTrueAndProperSize() {
            gameLogic.addPlayer('W');
            assertTrue(gameLogic.addPlayer('Z'));
            assertEquals(GameLogic.DEFAULT_PLAYERS_IDENTIFIERS.length + 2, gameLogic.getPlayersQty());
        }

        @Test
        void givenConstructorWithPlayersWhenPlayerWithTheSameIdentifierThenException() {
            Player[] players = {
                    new Player('X'),
                    new Player('O'),
                    new Player('X')
            };
            assertThrows(IncorrectPlayerException.class, () -> gameLogic = new GameLogic(3, players));
        }
    }
}
