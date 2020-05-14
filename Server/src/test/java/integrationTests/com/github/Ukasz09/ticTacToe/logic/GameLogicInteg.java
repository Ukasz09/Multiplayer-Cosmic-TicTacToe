package integrationTests.com.github.Ukasz09.ticTacToe.logic;

import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeDatabase;
import com.github.Ukasz09.ticTacToe.logic.game.GameLogic;
import com.github.Ukasz09.ticTacToe.logic.game.GameResults;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

public class GameLogicInteg {

    @Test
    public void givenRunningMongoDBWhenPlayThenNoException() throws Exception {
        GameLogic gameLogic = new GameLogic(new TicTacToeDatabase());
        assertEquals(GameResults.GAME_NOT_FINISHED, gameLogic.play(0, 0));
    }

}
