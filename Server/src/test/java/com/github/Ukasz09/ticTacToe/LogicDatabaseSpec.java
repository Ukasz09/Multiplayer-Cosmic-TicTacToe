package com.github.Ukasz09.ticTacToe;

import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeBean;
import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeDatabase;
import com.github.Ukasz09.ticTacToe.logic.game.GameLogic;
import com.mongodb.MongoException;
import org.junit.jupiter.api.*;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class LogicDatabaseSpec {
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
    public void whenInstantiatedThenSetDatabase() {
        assertNotNull(gameLogic.getDatabase());
    }

    @Test
    public void whenPlayThenSaveMoveIsInvoked() throws Exception {
        TicTacToeBean bean = new TicTacToeBean(1, 2, 2, GameLogic.PLAYERS_ID[0]);
        gameLogic.play(bean.getCordX(), bean.getCordY());
        verify(database, times(1)).saveMove(bean);
    }

    @Test
    public void whenPlayAndSaveFalseThenException() {
        doReturn(false).when(database).saveMove(any(TicTacToeBean.class));
        assertThrows(MongoException.class, () -> gameLogic.play(1, 2));
    }
}
