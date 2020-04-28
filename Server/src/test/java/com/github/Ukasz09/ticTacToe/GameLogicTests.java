package com.github.Ukasz09.ticTacToe;

import com.github.Ukasz09.ticTacToe.logic.game.GameLogic;
import com.github.Ukasz09.ticTacToe.logic.game.GameResults;
import com.github.Ukasz09.ticTacToe.logic.game.Player;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.IncorrectBoardSizeException;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.IncorrectFieldException;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.IncorrectPlayerException;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

import static com.github.Ukasz09.ticTacToe.logic.game.GameLogic.DEFAULT_PLAYERS_IDENTIFIERS;
import static org.junit.jupiter.api.Assertions.*;

class GameLogicTests {
    private GameLogic gameLogic;

    @Nested
    class TestsForAnyBoardAndMarkQty {
        @BeforeEach
        final void initializeDefaultTicTacGame() throws IncorrectBoardSizeException, IncorrectPlayerException {
            gameLogic = new GameLogic();
        }

        @Nested
        class MarkFieldTest {
            @Test
            void whenFirstTurnThenNoWinner() throws IncorrectFieldException {
                GameResults winner = gameLogic.markField(0, 0);
                assertEquals(GameResults.GAME_NOT_FINISHED, winner);
            }

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
                assertEquals('X', gameLogic.getLastPlayerSignId());
            }

            @Test
            void givenWasXWhenNextPlayerThenO() throws IncorrectFieldException {
                gameLogic.markField(1, 1); //X
                gameLogic.markField(1, 2); //O
                assertEquals('O', gameLogic.getLastPlayerSignId());
            }

            @Test
            void givenWasOWhenNextPlayerThenX() throws IncorrectFieldException {
                gameLogic.markField(1, 1); //X
                gameLogic.markField(0, 0); //O
                gameLogic.markField(0, 2); //X
                assertEquals('X', gameLogic.getLastPlayerSignId());
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
                assertEquals(DEFAULT_PLAYERS_IDENTIFIERS.length + 2, gameLogic.getPlayersQty());
            }

            @Test
            void givenConstructorWithPlayersWhenPlayerWithTheSameIdentifierThenException() {
                Player[] players = {
                        new Player('X'),
                        new Player('O'),
                        new Player('X')
                };
                assertThrows(IncorrectPlayerException.class, () -> gameLogic = new GameLogic(3, GameLogic.DEFAULT_MARKS_QTY_FOR_WIN, players));
            }
        }
    }

    @Nested
    class TestsForDefaultBoardAndMarkQty {
        @BeforeEach
        final void initializeDefaultTicTacGame() throws IncorrectBoardSizeException, IncorrectPlayerException {
            gameLogic = new GameLogic();
        }

        @Nested
        class WinnerTest {
            @Test
            void whenHorizontalLineThenWinner() throws IncorrectFieldException {
                gameLogic.markField(0, 0); //X
                gameLogic.markField(1, 1); //O
                gameLogic.markField(0, 1); //X
                gameLogic.markField(2, 1); //O
                GameResults winner = gameLogic.markField(0, 2); //X
                assertEquals(GameResults.WIN_PLAYER_0, winner);
            }

            @Test
            void whenVerticalLineThenWinner() throws IncorrectFieldException {
                gameLogic.markField(0, 0); //X
                gameLogic.markField(1, 1); //O
                gameLogic.markField(0, 2); //X
                gameLogic.markField(2, 1); //O
                gameLogic.markField(1, 0); //X
                GameResults winner = gameLogic.markField(0, 1); //O
                assertEquals(GameResults.WIN_PLAYER_1, winner);
            }

            @Test
            void whenRightDiagonalLineThenWinner() throws IncorrectFieldException {
                gameLogic.markField(0, 0); //X
                gameLogic.markField(1, 0); //O
                gameLogic.markField(1, 1); //X
                gameLogic.markField(2, 1); //O
                GameResults winner = gameLogic.markField(2, 2); //X
                assertEquals(GameResults.WIN_PLAYER_0, winner);
            }


            @Test
            void whenLeftDiagonalLineThenWinner() throws IncorrectFieldException {
                gameLogic.markField(0, 1); //X
                gameLogic.markField(0, 2); //O
                gameLogic.markField(1, 2); //X
                gameLogic.markField(1, 1); //O
                gameLogic.markField(2, 1); //X
                GameResults winner = gameLogic.markField(2, 0); //O
                assertEquals(GameResults.WIN_PLAYER_1, winner);
            }

            @Test
            void whenNoFullLineThenNoWinner() throws IncorrectFieldException {
                gameLogic.markField(0, 0); //X
                gameLogic.markField(0, 1); //O
                gameLogic.markField(0, 2); //X
                gameLogic.markField(1, 2); //O
                gameLogic.markField(1, 0); //X
                gameLogic.markField(1, 1); //O
                GameResults winner = gameLogic.markField(2, 1); //X
                assertEquals(GameResults.GAME_NOT_FINISHED, winner);
            }

            @Test
            void WhenAllBoxesFiledAndNoWinnerThenDraw() throws IncorrectBoardSizeException, IncorrectFieldException, IncorrectPlayerException {
                gameLogic = new GameLogic(3);
                gameLogic.markField(0, 0); //X
                gameLogic.markField(0, 1); //O
                gameLogic.markField(0, 2); //X
                gameLogic.markField(1, 2); //O
                gameLogic.markField(1, 0); //X
                gameLogic.markField(1, 1); //O
                gameLogic.markField(2, 1); //X
                gameLogic.markField(2, 0); //O
                GameResults lastTurn = gameLogic.markField(2, 2); //X
                assertEquals(GameResults.DRAW, lastTurn);
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
                GameResults winner = gameLogic.markField(2, 0); //X
                assertEquals(GameResults.WIN_PLAYER_0, winner);
            }
        }

        @Nested
        class WinnerMarksTest {
            @Test
            void whenHorizontalLineWithWinThenCorrectWinningCordsArr() throws IncorrectFieldException {
                gameLogic.markField(0, 0); //X
                gameLogic.markField(1, 1); //O
                gameLogic.markField(0, 1); //X
                gameLogic.markField(2, 1); //O
                gameLogic.markField(0, 2); //X
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
                gameLogic.markField(0, 0); //X
                gameLogic.markField(1, 1); //O
                gameLogic.markField(0, 2); //X
                gameLogic.markField(2, 1); //O
                gameLogic.markField(1, 0); //X
                gameLogic.markField(0, 1); //O

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
                gameLogic.markField(0, 0); //X
                gameLogic.markField(1, 0); //O
                gameLogic.markField(1, 1); //X
                gameLogic.markField(2, 1); //O
                gameLogic.markField(2, 2); //X

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
                gameLogic.markField(0, 1); //X
                gameLogic.markField(0, 2); //O
                gameLogic.markField(1, 2); //X
                gameLogic.markField(1, 1); //O
                gameLogic.markField(2, 1); //X
                gameLogic.markField(2, 0); //O

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
                gameLogic.markField(0, 0); //X
                gameLogic.markField(0, 1); //O
                gameLogic.markField(0, 2); //X
                gameLogic.markField(1, 2); //O
                gameLogic.markField(1, 0); //X
                gameLogic.markField(1, 1); //O
                gameLogic.markField(2, 1); //X
                gameLogic.markField(2, 2); //O
                gameLogic.markField(2, 0); //X

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
        final void initializeCustomTicTac() throws IncorrectBoardSizeException, IncorrectPlayerException {
            gameLogic = new GameLogic(7, GameLogic.DEFAULT_MARKS_QTY_FOR_WIN, new Player[]{
                    new Player(DEFAULT_PLAYERS_IDENTIFIERS[0]),
                    new Player(DEFAULT_PLAYERS_IDENTIFIERS[1])
            });
        }

        @Nested
        class WinnerTest {
            @Test
            void whenHorizontalLineThenWinner() throws IncorrectFieldException {
                gameLogic.markField(3, 3); //X
                gameLogic.markField(4, 4); //O
                gameLogic.markField(3, 4); //X
                gameLogic.markField(5, 4); //O
                GameResults winner = gameLogic.markField(3, 5); //X
                assertEquals(GameResults.WIN_PLAYER_0, winner);
            }

            @Test
            void whenVerticalLineThenWinner() throws IncorrectFieldException {
                gameLogic.markField(4, 4); //X
                gameLogic.markField(5, 5); //O
                gameLogic.markField(4, 6); //X
                gameLogic.markField(6, 5); //O
                gameLogic.markField(5, 4); //X
                GameResults winner = gameLogic.markField(4, 5); //O
                assertEquals(GameResults.WIN_PLAYER_1, winner);
            }

            @Test
            void whenRightDiagonalLineThenWinner() throws IncorrectFieldException {
                gameLogic.markField(4, 4); //X
                gameLogic.markField(5, 4); //O
                gameLogic.markField(5, 5); //X
                gameLogic.markField(6, 5); //O
                GameResults winner = gameLogic.markField(6, 6); //X
                assertEquals(GameResults.WIN_PLAYER_0, winner);
            }


            @Test
            void whenLeftDiagonalLineThenWinner() throws IncorrectFieldException {
                gameLogic.markField(4, 5); //X
                gameLogic.markField(4, 6); //O
                gameLogic.markField(5, 6); //X
                gameLogic.markField(5, 5); //O
                gameLogic.markField(6, 5); //X
                GameResults winner = gameLogic.markField(6, 4); //O
                assertEquals(GameResults.WIN_PLAYER_1, winner);
            }

            @Test
            void whenNoFullLineThenNoWinner() throws IncorrectFieldException {
                gameLogic.markField(4, 4); //X
                gameLogic.markField(4, 5); //O
                gameLogic.markField(4, 6); //X
                gameLogic.markField(5, 6); //O
                gameLogic.markField(5, 4); //X
                gameLogic.markField(5, 5); //O
                GameResults winner = gameLogic.markField(6, 5); //X
                assertEquals(GameResults.GAME_NOT_FINISHED, winner);
            }
        }

        @Nested
        class WinnerMarksTest {
            @Test
            void whenHorizontalLineWithWinThenCorrectWinningCordsArr() throws IncorrectFieldException {
                gameLogic.markField(3, 3); //X
                gameLogic.markField(4, 4); //O
                gameLogic.markField(3, 4); //X
                gameLogic.markField(5, 4); //O
                gameLogic.markField(3, 5); //X
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
                gameLogic.markField(2, 2); //X
                gameLogic.markField(3, 3); //O
                gameLogic.markField(2, 4); //X
                gameLogic.markField(4, 3); //O
                gameLogic.markField(3, 2); //X
                gameLogic.markField(2, 3); //O

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
                gameLogic.markField(1, 1); //X
                gameLogic.markField(2, 1); //O
                gameLogic.markField(2, 2); //X
                gameLogic.markField(3, 2); //O
                gameLogic.markField(3, 3); //X

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
                gameLogic.markField(4, 5); //X
                gameLogic.markField(4, 6); //O
                gameLogic.markField(5, 6); //X
                gameLogic.markField(5, 5); //O
                gameLogic.markField(6, 5); //X
                gameLogic.markField(6, 4); //O

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
}
