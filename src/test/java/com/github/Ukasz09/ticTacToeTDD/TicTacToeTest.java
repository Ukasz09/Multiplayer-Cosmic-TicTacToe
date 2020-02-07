package com.github.Ukasz09.ticTacToeTDD;

import com.github.Ukasz09.ticTacToeTDD.ticTacToeExceptions.IncorrectBoardSizeException;
import com.github.Ukasz09.ticTacToeTDD.ticTacToeExceptions.IncorrectFieldException;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class TicTacToeTest {
    private TicTacToe ticTacToe;

    @BeforeEach
    final void initializeTicTac() throws IncorrectBoardSizeException {
        ticTacToe = new TicTacToe(3);
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
        void givenFirstTurnWhenNextPlayerThenX() {
            assertEquals('X', ticTacToe.nextPlayer());
        }

        @Test
        void givenLastTurnWasXWhenNextPlayerThenO() throws IncorrectFieldException {
            ticTacToe.markField(1, 1);
            assertEquals('O', ticTacToe.nextPlayer());
        }

        @Test
        void givenLastTurnWasOWhenNextPlayerThenX() throws IncorrectFieldException {
            ticTacToe.markField(1, 1);
            ticTacToe.markField(0, 0);
            assertEquals('X', ticTacToe.nextPlayer());
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
        void givenBoardSize3WhenAllBoxesFiledAndNoWinnerThenDraw() throws IncorrectBoardSizeException, IncorrectFieldException {
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
    }

    @Nested
    class LineFilledTests {
        //todo:
    }
}
