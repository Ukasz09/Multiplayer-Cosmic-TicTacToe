package com.github.Ukasz09.ticTacToeTDD.applicationLogic;

import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.Player;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.TicTacToe;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.IncorrectBoardSizeException;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.IncorrectFieldException;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.ticTacToeGame.ticTacToeExceptions.IncorrectPlayerException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTest {
    private TicTacToe ticTacToe;

    @BeforeEach
    final void initializeTicTac() throws IncorrectBoardSizeException, IncorrectPlayerException {
        ticTacToe = new TicTacToe();
    }

    @Nested
    class MarkFieldTest {
        @Test
        void whenXOutsideBoardThenException() {
            assertThrows(IncorrectFieldException.class, () -> ticTacToe.markField(ticTacToe.getBoardSize(), 2));
        }

        @Test
        void whenYOutsideBoardThenException() {
            assertThrows(IncorrectFieldException.class, () -> ticTacToe.markField(1, ticTacToe.getBoardSize()));
        }

        @Test
        void whenOccupiedThenException() throws IncorrectFieldException {
            ticTacToe.markField(1, 2);
            assertThrows(IncorrectFieldException.class, () -> ticTacToe.markField(1, 2));
        }
    }

    @Nested
    class NextPlayerChoiceTest {
        @Test
        void givenFirstTurnWhenNextPlayerThenX() throws IncorrectFieldException {
            ticTacToe.markField(0,0); //X
            assertEquals('X', ticTacToe.getLastPlayerId());
        }

        @Test
        void givenWasXWhenNextPlayerThenO() throws IncorrectFieldException {
            ticTacToe.markField(1, 1); //X
            ticTacToe.markField(1, 2); //O
            assertEquals('O', ticTacToe.getLastPlayerId());
        }

        @Test
        void givenWasOWhenNextPlayerThenX() throws IncorrectFieldException {
            ticTacToe.markField(1, 1); //X
            ticTacToe.markField(0, 0); //O
            ticTacToe.markField(0, 2); //X
            assertEquals('X', ticTacToe.getLastPlayerId());
        }
    }

    @Nested
    class WinnerTest {
        @Test
        void whenFirstTurnThenNoWinner() throws IncorrectFieldException {
            String winner = ticTacToe.markField(0, 0);
            assertEquals(TicTacToe.NO_WINNER_MSG, winner);
        }

        @Test
        void whenHorizontalLineThenWinner() throws IncorrectFieldException {
            ticTacToe.markField(0, 0); //X
            ticTacToe.markField(1, 1); //O
            ticTacToe.markField(0, 1); //X
            ticTacToe.markField(2, 1); //O
            String winner = ticTacToe.markField(0, 2); //X
            assertEquals(TicTacToe.WINNER_MSG_PREFIX + "X", winner);
        }

        @Test
        void whenVerticalLineThenWinner() throws IncorrectFieldException {
            ticTacToe.markField(0, 0); //X
            ticTacToe.markField(1, 1); //O
            ticTacToe.markField(0, 2); //X
            ticTacToe.markField(2, 1); //O
            ticTacToe.markField(1, 0); //X
            String winner = ticTacToe.markField(0, 1); //O
            assertEquals(TicTacToe.WINNER_MSG_PREFIX + "O", winner);
        }

        @Test
        void whenRightDiagonalLineThenWinner() throws IncorrectFieldException {
            ticTacToe.markField(0, 0); //X
            ticTacToe.markField(1, 0); //O
            ticTacToe.markField(1, 1); //X
            ticTacToe.markField(2, 1); //O
            String winner = ticTacToe.markField(2, 2); //X
            assertEquals(TicTacToe.WINNER_MSG_PREFIX + "X", winner);
        }


        @Test
        void whenLeftDiagonalLineThenWinner() throws IncorrectFieldException {
            ticTacToe.markField(0, 1); //X
            ticTacToe.markField(0, 2); //O
            ticTacToe.markField(1, 2); //X
            ticTacToe.markField(1, 1); //O
            ticTacToe.markField(2, 1); //X
            String winner = ticTacToe.markField(2, 0); //O
            assertEquals(TicTacToe.WINNER_MSG_PREFIX + "O", winner);
        }

        @Test
        void whenNoFullLineThenNoWinner() throws IncorrectFieldException {
            ticTacToe.markField(0, 0); //X
            ticTacToe.markField(0, 1); //O
            ticTacToe.markField(0, 2); //X
            ticTacToe.markField(1, 2); //O
            ticTacToe.markField(1, 0); //X
            ticTacToe.markField(1, 1); //O
            String winner = ticTacToe.markField(2, 1); //X
            assertEquals(TicTacToe.NO_WINNER_MSG, winner);
        }

        @Test
        void givenBoardSize3WhenAllBoxesFiledAndNoWinnerThenDraw() throws IncorrectBoardSizeException, IncorrectFieldException, IncorrectPlayerException {
            ticTacToe = new TicTacToe(3);
            ticTacToe.markField(0, 0); //X
            ticTacToe.markField(0, 1); //O
            ticTacToe.markField(0, 2); //X
            ticTacToe.markField(1, 2); //O
            ticTacToe.markField(1, 0); //X
            ticTacToe.markField(1, 1); //O
            ticTacToe.markField(2, 1); //X
            ticTacToe.markField(2, 0); //O
            String lastTurn = ticTacToe.markField(2, 2); //X
            assertEquals(TicTacToe.DRAW_MSG, lastTurn);
        }

        @Test
        void whenAllBoxesFiledAndIsWinnerThenWinner() throws IncorrectFieldException {
            ticTacToe.markField(0, 0); //X
            ticTacToe.markField(0, 1); //O
            ticTacToe.markField(0, 2); //X
            ticTacToe.markField(1, 2); //O
            ticTacToe.markField(1, 0); //X
            ticTacToe.markField(1, 1); //O
            ticTacToe.markField(2, 1); //X
            ticTacToe.markField(2, 2); //O
            String winner = ticTacToe.markField(2, 0); //X
            assertEquals(TicTacToe.WINNER_MSG_PREFIX + "X", winner);
        }
    }

    @Nested
    class InitializationTest {
        @Test
        void whenIncorrectBoardSizeThenIncorrectBoardException() {
            assertThrows(IncorrectBoardSizeException.class, () -> ticTacToe = new TicTacToe(2));
        }

        @Test
        void whenAddedPlayerWithTheSameIdentifierThenFalse() {
            ticTacToe.addPlayer('W');
            assertFalse(ticTacToe.addPlayer('W'));
        }

        @Test
        void whenAdded2PlayersWithDifferentIdentifierThenTrueAndProperSize() {
            ticTacToe.addPlayer('W');
            assertTrue(ticTacToe.addPlayer('Z'));
            assertEquals(TicTacToe.DEFAULT_PLAYERS_IDENTIFIERS.length + 2, ticTacToe.getPlayersQty());
        }

        @Test
        void givenConstructorWithPlayersWhenPlayerWithTheSameIdentifierThenException() {
            Player[] players = {
                    new Player('X'),
                    new Player('O'),
                    new Player('X')
            };
            assertThrows(IncorrectPlayerException.class, () -> ticTacToe = new TicTacToe(3, players));
        }
    }

    @Nested
    class LineFilledTests {
        //todo:
    }
}
