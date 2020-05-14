package unitTests.com.github.Ukasz09.ticTacToe.logic;

import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeBean;
import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeDatabase;
import com.github.Ukasz09.ticTacToe.logic.game.GameLogic;
import com.github.Ukasz09.ticTacToe.logic.game.GameResults;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.IncorrectFieldException;
import org.junit.jupiter.api.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

public class LogicCustomBoardSizeSpec {
    private GameLogic gameLogic;

    //----------------------------------------------------------------------------------------------------------------//
    @BeforeEach
    final void initializeLogicWith7x7Board() throws Exception {
        TicTacToeDatabase database = mock(TicTacToeDatabase.class);
        doReturn(true).when(database).drop();
        doReturn(true).when(database).saveMove(any(TicTacToeBean.class));
        gameLogic = new GameLogic(7, GameLogic.DEFAULT_MARKS_QTY_FOR_WIN, database);
    }

    @Nested
    class WinnerTest {
        @Test
        void whenHorizontalLineThenWinner() throws IncorrectFieldException {
            gameLogic.play(3, 3); //X
            gameLogic.play(4, 4); //O
            gameLogic.play(3, 4); //X
            gameLogic.play(5, 4); //O
            GameResults winner = gameLogic.play(3, 5); //X
            assertEquals(GameResults.WIN_PLAYER_0, winner);
        }

        @Test
        void whenVerticalLineThenWinner() throws IncorrectFieldException {
            gameLogic.play(4, 4); //X
            gameLogic.play(5, 5); //O
            gameLogic.play(4, 6); //X
            gameLogic.play(6, 5); //O
            gameLogic.play(5, 4); //X
            GameResults winner = gameLogic.play(4, 5); //O
            assertEquals(GameResults.WIN_PLAYER_1, winner);
        }

        @Test
        void whenRightDiagonalLineThenWinner() throws IncorrectFieldException {
            gameLogic.play(4, 4); //X
            gameLogic.play(5, 4); //O
            gameLogic.play(5, 5); //X
            gameLogic.play(6, 5); //O
            GameResults winner = gameLogic.play(6, 6); //X
            assertEquals(GameResults.WIN_PLAYER_0, winner);
        }

        @Test
        void whenLeftDiagonalLineThenWinner() throws IncorrectFieldException {
            gameLogic.play(4, 5); //X
            gameLogic.play(4, 6); //O
            gameLogic.play(5, 6); //X
            gameLogic.play(5, 5); //O
            gameLogic.play(6, 5); //X
            GameResults winner = gameLogic.play(6, 4); //O
            assertEquals(GameResults.WIN_PLAYER_1, winner);
        }

        @Test
        void whenNoFullLineThenNoWinner() throws IncorrectFieldException {
            gameLogic.play(4, 4); //X
            gameLogic.play(4, 5); //O
            gameLogic.play(4, 6); //X
            gameLogic.play(5, 6); //O
            gameLogic.play(5, 4); //X
            gameLogic.play(5, 5); //O
            GameResults winner = gameLogic.play(6, 5); //X
            assertEquals(GameResults.GAME_NOT_FINISHED, winner);
        }
    }

    @Nested
    class WinnerMarksTest {
        @Test
        void whenHorizontalLineWithWinThenCorrectWinningCordsArr() throws IncorrectFieldException {
            gameLogic.play(3, 3); //X
            gameLogic.play(4, 4); //O
            gameLogic.play(3, 4); //X
            gameLogic.play(5, 4); //O
            gameLogic.play(3, 5); //X
            Point[] winningCoordsArr = {
                    new Point(3, 3),
                    new Point(3, 4),
                    new Point(3, 5),
            };
            java.util.List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
            java.util.List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
            assertTrue(expectedWinningCoords.size() == actualWinningCoords.size() &&
                    actualWinningCoords.containsAll(expectedWinningCoords) && expectedWinningCoords.containsAll(actualWinningCoords));
        }

        @Test
        void whenVerticalLineWithWinThenCorrectWinningCordsArr() throws IncorrectFieldException {
            gameLogic.play(2, 2); //X
            gameLogic.play(3, 3); //O
            gameLogic.play(2, 4); //X
            gameLogic.play(4, 3); //O
            gameLogic.play(3, 2); //X
            gameLogic.play(2, 3); //O

            Point[] winningCoordsArr = {
                    new Point(2, 3),
                    new Point(4, 3),
                    new Point(3, 3),
            };
            java.util.List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
            java.util.List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
            assertTrue(expectedWinningCoords.size() == actualWinningCoords.size() &&
                    actualWinningCoords.containsAll(expectedWinningCoords) && expectedWinningCoords.containsAll(actualWinningCoords));
        }

        @Test
        void whenRightDiagonalLineWithWinThenCorrectWinningCordsArr() throws IncorrectFieldException {
            gameLogic.play(1, 1); //X
            gameLogic.play(2, 1); //O
            gameLogic.play(2, 2); //X
            gameLogic.play(3, 2); //O
            gameLogic.play(3, 3); //X

            Point[] winningCoordsArr = {
                    new Point(1, 1),
                    new Point(2, 2),
                    new Point(3, 3),
            };

            java.util.List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
            java.util.List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
            assertTrue(expectedWinningCoords.size() == actualWinningCoords.size() &&
                    actualWinningCoords.containsAll(expectedWinningCoords) && expectedWinningCoords.containsAll(actualWinningCoords));
        }

        @Test
        void whenLeftDiagonalLineWithWinThenCorrectWinningCordsArr() throws IncorrectFieldException {
            gameLogic.play(4, 5); //X
            gameLogic.play(4, 6); //O
            gameLogic.play(5, 6); //X
            gameLogic.play(5, 5); //O
            gameLogic.play(6, 5); //X
            gameLogic.play(6, 4); //O

            Point[] winningCoordsArr = {
                    new Point(4, 6),
                    new Point(5, 5),
                    new Point(6, 4),
            };
            java.util.List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
            List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
            assertTrue(expectedWinningCoords.size() == actualWinningCoords.size() &&
                    actualWinningCoords.containsAll(expectedWinningCoords) && expectedWinningCoords.containsAll(actualWinningCoords));
        }
    }

    @Test
    void WhenAllBoxesFiledAndNoWinnerThenDraw() throws Exception {
        TicTacToeDatabase database = mock(TicTacToeDatabase.class);
        doReturn(true).when(database).saveMove(any(TicTacToeBean.class));
        doReturn(true).when(database).drop();
        gameLogic = new GameLogic(3, database);
        gameLogic.play(0, 0); //X
        gameLogic.play(0, 1); //O
        gameLogic.play(0, 2); //X
        gameLogic.play(1, 2); //O
        gameLogic.play(1, 0); //X
        gameLogic.play(1, 1); //O
        gameLogic.play(2, 1); //X
        gameLogic.play(2, 0); //O
        GameResults lastTurn = gameLogic.play(2, 2); //X
        assertEquals(GameResults.DRAW, lastTurn);
    }
}
