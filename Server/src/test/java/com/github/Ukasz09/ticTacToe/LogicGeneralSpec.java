package com.github.Ukasz09.ticTacToe;

import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeBean;
import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeDatabase;
import com.github.Ukasz09.ticTacToe.logic.game.GameLogic;
import com.github.Ukasz09.ticTacToe.logic.game.GameResults;
import com.github.Ukasz09.ticTacToe.logic.game.exceptions.IncorrectFieldException;
import org.junit.jupiter.api.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class LogicGeneralSpec {
    private GameLogic gameLogic;

    //----------------------------------------------------------------------------------------------------------------//
    @BeforeEach
    final void initializeDefaultGameLogic() throws Exception {
        TicTacToeDatabase database = mock(TicTacToeDatabase.class);
        doReturn(true).when(database).saveMove(any(TicTacToeBean.class));
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
