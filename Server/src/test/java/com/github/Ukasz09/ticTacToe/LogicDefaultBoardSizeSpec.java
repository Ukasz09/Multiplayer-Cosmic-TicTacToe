package com.github.Ukasz09.ticTacToe;

import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeBean;
import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeDatabase;
import com.github.Ukasz09.ticTacToe.logic.game.GameLogic;
import com.github.Ukasz09.ticTacToe.logic.game.GameResults;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.*;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LogicDefaultBoardSizeSpec {
    private GameLogic gameLogic;
    private TicTacToeDatabase database;

    //----------------------------------------------------------------------------------------------------------------//
    @BeforeEach
    final void initializeDefaultGameLogic() throws Exception {
        database = mock(TicTacToeDatabase.class);
        doReturn(true).when(database).saveMove(any(TicTacToeBean.class));
        gameLogic = new GameLogic(database);
    }

    @Test
    void whenIncorrectBoardSizeThenIncorrectBoardException() {
        database = mock(TicTacToeDatabase.class);
        assertThrows(IncorrectBoardSizeException.class, () -> gameLogic = new GameLogic(2, database));
    }

    @Nested
    class WinnerTest {
        @Test
        void whenHorizontalLineThenWinner() throws IncorrectFieldException {
            gameLogic.play(0, 0); //X
            gameLogic.play(1, 1); //O
            gameLogic.play(0, 1); //X
            gameLogic.play(2, 1); //O
            GameResults winner = gameLogic.play(0, 2); //X
            assertEquals(GameResults.WIN_PLAYER_0, winner);
        }

        @Test
        void whenVerticalLineThenWinner() throws IncorrectFieldException {
            gameLogic.play(0, 0); //X
            gameLogic.play(1, 1); //O
            gameLogic.play(0, 2); //X
            gameLogic.play(2, 1); //O
            gameLogic.play(1, 0); //X
            GameResults winner = gameLogic.play(0, 1); //O
            assertEquals(GameResults.WIN_PLAYER_1, winner);
        }

        @Test
        void whenRightDiagonalLineThenWinner() throws IncorrectFieldException {
            gameLogic.play(0, 0); //X
            gameLogic.play(1, 0); //O
            gameLogic.play(1, 1); //X
            gameLogic.play(2, 1); //O
            GameResults winner = gameLogic.play(2, 2); //X
            assertEquals(GameResults.WIN_PLAYER_0, winner);
        }

        @Test
        void whenLeftDiagonalLineThenWinner() throws IncorrectFieldException {
            gameLogic.play(0, 1); //X
            gameLogic.play(0, 2); //O
            gameLogic.play(1, 2); //X
            gameLogic.play(1, 1); //O
            gameLogic.play(2, 1); //X
            GameResults winner = gameLogic.play(2, 0); //O
            assertEquals(GameResults.WIN_PLAYER_1, winner);
        }

        @Test
        void whenNoFullLineThenNoWinner() throws IncorrectFieldException {
            gameLogic.play(0, 0); //X
            gameLogic.play(0, 1); //O
            gameLogic.play(0, 2); //X
            gameLogic.play(1, 2); //O
            gameLogic.play(1, 0); //X
            gameLogic.play(1, 1); //O
            GameResults winner = gameLogic.play(2, 1); //X
            assertEquals(GameResults.GAME_NOT_FINISHED, winner);
        }

        @Test
        void whenAllBoxesFiledAndIsWinnerThenWinner() throws IncorrectFieldException {
            gameLogic.play(0, 0); //X
            gameLogic.play(0, 1); //O
            gameLogic.play(0, 2); //X
            gameLogic.play(1, 2); //O
            gameLogic.play(1, 0); //X
            gameLogic.play(1, 1); //O
            gameLogic.play(2, 1); //X
            gameLogic.play(2, 2); //O
            GameResults winner = gameLogic.play(2, 0); //X
            assertEquals(GameResults.WIN_PLAYER_0, winner);
        }
    }

    @Nested
    class WinnerMarksTest {
        @Test
        void whenHorizontalLineWithWinThenCorrectWinningCordsArr() throws IncorrectFieldException {
            gameLogic.play(0, 0); //X
            gameLogic.play(1, 1); //O
            gameLogic.play(0, 1); //X
            gameLogic.play(2, 1); //O
            gameLogic.play(0, 2); //X
            Point[] winningCoordsArr = {
                    new Point(0, 0),
                    new Point(0, 1),
                    new Point(0, 2),
            };
            java.util.List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
            java.util.List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
            assertTrue(expectedWinningCoords.size() == actualWinningCoords.size() &&
                    actualWinningCoords.containsAll(expectedWinningCoords) && expectedWinningCoords.containsAll(actualWinningCoords));
        }

        @Test
        void whenVerticalLineWithWinThenCorrectWinningCordsArr() throws IncorrectFieldException {
            gameLogic.play(0, 0); //X
            gameLogic.play(1, 1); //O
            gameLogic.play(0, 2); //X
            gameLogic.play(2, 1); //O
            gameLogic.play(1, 0); //X
            gameLogic.play(0, 1); //O

            Point[] winningCoordsArr = {
                    new Point(1, 1),
                    new Point(2, 1),
                    new Point(0, 1),
            };
            java.util.List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
            java.util.List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
            assertTrue(expectedWinningCoords.size() == actualWinningCoords.size() &&
                    actualWinningCoords.containsAll(expectedWinningCoords) && expectedWinningCoords.containsAll(actualWinningCoords));
        }

        @Test
        void whenRightDiagonalLineWithWinThenCorrectWinningCordsArr() throws IncorrectFieldException {
            gameLogic.play(0, 0); //X
            gameLogic.play(1, 0); //O
            gameLogic.play(1, 1); //X
            gameLogic.play(2, 1); //O
            gameLogic.play(2, 2); //X

            Point[] winningCoordsArr = {
                    new Point(0, 0),
                    new Point(1, 1),
                    new Point(2, 2),
            };

            java.util.List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
            java.util.List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
            assertTrue(expectedWinningCoords.size() == actualWinningCoords.size() &&
                    actualWinningCoords.containsAll(expectedWinningCoords) && expectedWinningCoords.containsAll(actualWinningCoords));
        }

        @Test
        void whenLeftDiagonalLineWithWinThenCorrectWinningCordsArr() throws IncorrectFieldException {
            gameLogic.play(0, 1); //X
            gameLogic.play(0, 2); //O
            gameLogic.play(1, 2); //X
            gameLogic.play(1, 1); //O
            gameLogic.play(2, 1); //X
            gameLogic.play(2, 0); //O

            Point[] winningCoordsArr = {
                    new Point(0, 2),
                    new Point(1, 1),
                    new Point(2, 0),
            };
            java.util.List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
            java.util.List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
            assertTrue(expectedWinningCoords.size() == actualWinningCoords.size() &&
                    actualWinningCoords.containsAll(expectedWinningCoords) && expectedWinningCoords.containsAll(actualWinningCoords));
        }

        @Test
        void whenAllBoxesFiledAndIsWinThenCorrectWinningCordsArr() throws IncorrectFieldException {
            gameLogic.play(0, 0); //X
            gameLogic.play(0, 1); //O
            gameLogic.play(0, 2); //X
            gameLogic.play(1, 2); //O
            gameLogic.play(1, 0); //X
            gameLogic.play(1, 1); //O
            gameLogic.play(2, 1); //X
            gameLogic.play(2, 2); //O
            gameLogic.play(2, 0); //X

            Point[] winningCoordsArr = {
                    new Point(2, 0),
                    new Point(0, 0),
                    new Point(1, 0),
            };
            java.util.List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
            List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
            assertTrue(expectedWinningCoords.size() == actualWinningCoords.size() &&
                    actualWinningCoords.containsAll(expectedWinningCoords) && expectedWinningCoords.containsAll(actualWinningCoords));
        }
    }
}
