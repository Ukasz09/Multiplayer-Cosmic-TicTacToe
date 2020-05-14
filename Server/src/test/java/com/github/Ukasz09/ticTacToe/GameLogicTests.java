package com.github.Ukasz09.ticTacToe;

import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeBean;
import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeDatabase;
import com.github.Ukasz09.ticTacToe.logic.game.GameLogic;
import com.github.Ukasz09.ticTacToe.logic.game.GameResults;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.IncorrectBoardSizeException;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.IncorrectFieldException;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GameLogicTests {
    private GameLogic gameLogic;
    private TicTacToeDatabase database;

    @Nested
    class TestsForAnyBoardAndMarkQty {
        @BeforeEach
        final void initializeDefaultTicTacGame() throws Exception {
            database = mock(TicTacToeDatabase.class);
            gameLogic = new GameLogic(database);
        }

        @Nested
        class MarkFieldTest {
            @Test
            void whenFirstTurnThenNoWinner() throws IncorrectFieldException {
                GameResults winner = gameLogic.play(0, 0);
                assertEquals(GameResults.GAME_NOT_FINISHED, winner);
            }

            @Test
            void whenXOutsideBoardThenException() {
                assertThrows(IncorrectFieldException.class, () -> gameLogic.play(gameLogic.getBoardSize(), 2));
            }

            @Test
            void whenYOutsideBoardThenException() {
                assertThrows(IncorrectFieldException.class, () -> gameLogic.play(1, gameLogic.getBoardSize()));
            }

            @Test
            void whenOccupiedThenException() throws IncorrectFieldException {
                gameLogic.play(1, 2);
                assertThrows(IncorrectFieldException.class, () -> gameLogic.play(1, 2));
            }
        }

        @Nested
        class NextPlayerChoiceTest {
            @Test
            void givenFirstTurnWhenNextPlayerThenX() throws IncorrectFieldException {
                gameLogic.play(0, 0); //X
                assertEquals('X', gameLogic.getLastPlayerSignId());
            }

            @Test
            void givenWasXWhenNextPlayerThenO() throws IncorrectFieldException {
                gameLogic.play(1, 1); //X
                gameLogic.play(1, 2); //O
                assertEquals('O', gameLogic.getLastPlayerSignId());
            }

            @Test
            void givenWasOWhenNextPlayerThenX() throws IncorrectFieldException {
                gameLogic.play(1, 1); //X
                gameLogic.play(0, 0); //O
                gameLogic.play(0, 2); //X
                assertEquals('X', gameLogic.getLastPlayerSignId());
            }
        }
    }

    @Nested
    class TestsForDefaultBoardAndMarkQty {
        @BeforeEach
        final void initializeDefaultTicTacGame() throws Exception {
            database = mock(TicTacToeDatabase.class);
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
            void WhenAllBoxesFiledAndNoWinnerThenDraw() throws Exception {
                database = mock(TicTacToeDatabase.class);
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
                List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
                List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
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
                List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
                List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
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

                List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
                List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
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
                List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
                List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
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
                List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
                List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
                assertTrue(expectedWinningCoords.size() == actualWinningCoords.size() &&
                        actualWinningCoords.containsAll(expectedWinningCoords) && expectedWinningCoords.containsAll(actualWinningCoords));
            }
        }
    }

    @Nested
    class TestsFor7x7BoardAndDefaultMarkQty {
        @BeforeEach
        final void initializeCustomTicTac() throws Exception {
            database = mock(TicTacToeDatabase.class);
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
                List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
                List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
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
                List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
                List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
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

                List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
                List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
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
                List<Point> expectedWinningCoords = Arrays.asList(winningCoordsArr);
                List<Point> actualWinningCoords = Arrays.asList(gameLogic.getWinningCoords());
                assertTrue(expectedWinningCoords.size() == actualWinningCoords.size() &&
                        actualWinningCoords.containsAll(expectedWinningCoords) && expectedWinningCoords.containsAll(actualWinningCoords));
            }
        }
    }

    @Nested
    class DatabaseCommunication {
        @BeforeEach
        final void initializeDefaultTicTacGame() throws Exception {
            database = mock(TicTacToeDatabase.class);
            gameLogic = new GameLogic(database);
        }

        @Test
        public void whenInstantiatedThenSetDatabase() {
            assertNotNull(gameLogic.getDatabase());
        }

        @Test
        public void whenPlayThenSaveMoveIsInvoked() throws Exception {
            TicTacToeBean bean = new TicTacToeBean(1, 2, 2, GameLogic.PLAYERS_ID[0]);
            gameLogic.play(bean.getCordX(), bean.getCordY());
            verify(database, times(1)).saveMove(bean);
        }
    }
}
