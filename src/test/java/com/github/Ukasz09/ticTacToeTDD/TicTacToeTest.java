package com.github.Ukasz09.ticTacToeTDD;

import com.github.Ukasz09.ticTacToeTDD.ticTacToeExceptions.IncorrectBoardException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTest {
    private TicTacToe ticTacToe;

    @BeforeEach
    final void initializeTicTac() throws IncorrectBoardException {
        ticTacToe = new TicTacToe();
    }

    @Nested
    class MarkFieldTest {
        @Test
        void whenXOutsideBoardThenRuntimeException() {
            assertThrows(RuntimeException.class, () -> ticTacToe.markField(6, 2));
        }

        @Test
        void whenYOutsideBoardThenRuntimeException() {
            assertThrows(RuntimeException.class, () -> ticTacToe.markField(1, 3));
        }

        @Test
        void whenOccupiedThenRuntimeException() {
            ticTacToe.markField(1, 2);
            assertThrows(RuntimeException.class, () -> ticTacToe.markField(1, 2));
        }
    }

    @Nested
    class NextPlayerChoiceTest {
        @Test
        void givenFirstTurnWhenNextPlayerThenX() {
            assertEquals('X', ticTacToe.nextPlayer());
        }

        @Test
        void givenLastTurnWasXWhenNextPlayerThenO() {
            ticTacToe.markField(1, 1);
            assertEquals('O', ticTacToe.nextPlayer());
        }

        @Test
        void givenLastTurnWasOWhenNextPlayerThenX() {
            ticTacToe.markField(1, 1);
            ticTacToe.markField(0, 0);
            assertEquals('X', ticTacToe.nextPlayer());
        }
    }

    @Nested
    class WinnerTest {
        @Test
        void whenFirstTurnThenNoWinner() {
            String winner = ticTacToe.markField(0, 0);
            assertEquals(TicTacToe.NO_WINNER_MSG, winner);
        }

        @Test
        void whenHorizontalLineThenWinner() {
            ticTacToe.markField(0, 0); //X
            ticTacToe.markField(1, 1); //O
            ticTacToe.markField(0, 1); //X
            ticTacToe.markField(2, 1); //O
            String winner = ticTacToe.markField(0, 2); //X
            assertEquals(TicTacToe.WINNER_MSG_PREFIX + "X", winner);
        }

        @Test
        void whenVerticalLineThenWinner() {
            ticTacToe.markField(0, 0); //X
            ticTacToe.markField(1, 1); //O
            ticTacToe.markField(0, 2); //X
            ticTacToe.markField(2, 1); //O
            ticTacToe.markField(1, 0); //X
            String winner = ticTacToe.markField(0, 1); //O
            assertEquals(TicTacToe.WINNER_MSG_PREFIX + "O", winner);
        }

        @Test
        void whenRightDiagonalLineThenWinner() {
            ticTacToe.markField(0, 0); //X
            ticTacToe.markField(1, 0); //O
            ticTacToe.markField(1, 1); //X
            ticTacToe.markField(2, 1); //O
            String winner = ticTacToe.markField(2, 2); //X
            assertEquals(TicTacToe.WINNER_MSG_PREFIX + "X", winner);
        }


        @Test
        void whenLeftDiagonalLineThenWinner() {
            ticTacToe.markField(0, 1); //X
            ticTacToe.markField(0, 2); //O
            ticTacToe.markField(1, 2); //X
            ticTacToe.markField(1, 1); //O
            ticTacToe.markField(2, 1); //X
            String winner = ticTacToe.markField(2, 0); //O
            assertEquals(TicTacToe.WINNER_MSG_PREFIX + "O", winner);
        }

        @Test
        void whenNoFullLineThenNoWinner() {
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
        void whenAllBoxesFiledAndNoWinnerThenDraw() {
            ticTacToe.markField(0, 0); //X
            ticTacToe.markField(0, 1); //O
            ticTacToe.markField(0, 2); //X
            ticTacToe.markField(1, 2); //O
            ticTacToe.markField(1, 0); //X
            ticTacToe.markField(1, 1); //O
            ticTacToe.markField(2, 1); //X
            ticTacToe.markField(2, 0); //O
            String winner = ticTacToe.markField(2, 2); //X
            assertEquals(TicTacToe.DRAW_MSG, winner);
        }

        @Test
        void whenAllBoxesFiledAndIsWinnerThenWinner() {
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
            assertThrows(IncorrectBoardException.class, () -> ticTacToe = new TicTacToe(2));
        }
    }
}
