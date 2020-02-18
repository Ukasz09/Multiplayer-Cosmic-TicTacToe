package com.github.Ukasz09.ticTacToeTDD.applicationLogic;

import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.GameResult;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.Player;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.GameLogic;
import com.github.Ukasz09.ticTacToeTDD.applicationLogic.game.gameExceptions.*;
import org.junit.jupiter.api.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameLogicTest {
    private GameLogic gameLogic;

    @Nested
    class DefaultBoardAndMarkQtyTest {

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
                GameResult winner = gameLogic.markField(0, 0);
                assertEquals(GameResult.GAME_NOT_FINISHED, winner);
            }

            @Test
            void whenHorizontalLineThenWinner() throws IncorrectFieldException {
                gameLogic.markField(0, 0); //X
                gameLogic.markField(1, 1); //O
                gameLogic.markField(0, 1); //X
                gameLogic.markField(2, 1); //O
                GameResult winner = gameLogic.markField(0, 2); //X
                assertEquals(GameResult.WIN_PLAYER_0, winner);
            }

            @Test
            void whenVerticalLineThenWinner() throws IncorrectFieldException {
                gameLogic.markField(0, 0); //X
                gameLogic.markField(1, 1); //O
                gameLogic.markField(0, 2); //X
                gameLogic.markField(2, 1); //O
                gameLogic.markField(1, 0); //X
                GameResult winner = gameLogic.markField(0, 1); //O
                assertEquals(GameResult.WIN_PLAYER_1, winner);
            }

            @Test
            void whenRightDiagonalLineThenWinner() throws IncorrectFieldException {
                gameLogic.markField(0, 0); //X
                gameLogic.markField(1, 0); //O
                gameLogic.markField(1, 1); //X
                gameLogic.markField(2, 1); //O
                GameResult winner = gameLogic.markField(2, 2); //X
                assertEquals(GameResult.WIN_PLAYER_0, winner);
            }


            @Test
            void whenLeftDiagonalLineThenWinner() throws IncorrectFieldException {
                gameLogic.markField(0, 1); //X
                gameLogic.markField(0, 2); //O
                gameLogic.markField(1, 2); //X
                gameLogic.markField(1, 1); //O
                gameLogic.markField(2, 1); //X
                GameResult winner = gameLogic.markField(2, 0); //O
                assertEquals(GameResult.WIN_PLAYER_1, winner);
            }

            @Test
            void whenNoFullLineThenNoWinner() throws IncorrectFieldException {
                gameLogic.markField(0, 0); //X
                gameLogic.markField(0, 1); //O
                gameLogic.markField(0, 2); //X
                gameLogic.markField(1, 2); //O
                gameLogic.markField(1, 0); //X
                gameLogic.markField(1, 1); //O
                GameResult winner = gameLogic.markField(2, 1); //X
                assertEquals(GameResult.GAME_NOT_FINISHED, winner);
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
                GameResult lastTurn = gameLogic.markField(2, 2); //X
                assertEquals(GameResult.DRAW, lastTurn);
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
                GameResult winner = gameLogic.markField(2, 0); //X
                assertEquals(GameResult.WIN_PLAYER_0, winner);
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
                assertThrows(IncorrectPlayerException.class, () -> gameLogic = new GameLogic(3, GameLogic.DEFAULT_MARKS_QTY_FOR_WIN, players));
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
}
