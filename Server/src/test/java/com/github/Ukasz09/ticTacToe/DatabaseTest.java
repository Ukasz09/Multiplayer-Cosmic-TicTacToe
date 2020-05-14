package com.github.Ukasz09.ticTacToe;

import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeBean;
import com.github.Ukasz09.ticTacToe.logic.databaseConnection.TicTacToeDatabase;
import com.github.Ukasz09.ticTacToe.logic.game.GameLogic;
import com.mongodb.MongoException;
import org.jongo.MongoCollection;
import org.junit.jupiter.api.*;

import java.net.UnknownHostException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class DatabaseTest {
    private TicTacToeDatabase gameDatabase;
    private MongoCollection mongoCollectionMock;
    private TicTacToeBean bean;

    //----------------------------------------------------------------------------------------------------------------//
    @BeforeEach
    public void initTicTacToeCollection() throws UnknownHostException {
        gameDatabase = spy(new TicTacToeDatabase());
        mongoCollectionMock = mock(MongoCollection.class);
        bean = new TicTacToeBean(3, 2, 1, GameLogic.PLAYERS_ID[0]);
    }

    @Test
    public void whenInstantiatedThenMongoDBHasProperName() {
        String properName = TicTacToeDatabase.DATABASE_NAME;
        assertEquals(properName, gameDatabase.getMongoCollection().getDBCollection().getDB().getName());
    }

    @Test
    public void whenInstantiatedTheMongoCollectionHasProperName() {
        String properName = TicTacToeDatabase.COLLECTION_NAME;
        assertEquals(properName, gameDatabase.getMongoCollection().getName());
    }

    @Test
    public void whenSaveMoveThenInvokeMongoCollectionSave() {
        doReturn(mongoCollectionMock).when(gameDatabase).getMongoCollection();
        gameDatabase.saveMove(bean);
        verify(mongoCollectionMock, times(1)).save(bean);
    }

    @Test
    public void whenSaveMoveThenTrue() {
        doReturn(mongoCollectionMock).when(gameDatabase).getMongoCollection();
        assertTrue(gameDatabase.saveMove(bean));
    }

    @Test
    public void givenExceptionWhenSaveMoveThenReturnFalse() {
        doThrow(new MongoException("exception")).when(mongoCollectionMock).save(any(TicTacToeBean.class));
        doReturn(mongoCollectionMock).when(gameDatabase).getMongoCollection();
        assertFalse(gameDatabase.saveMove(bean));
    }

    @Test
    public void whenDropThenInvokeMongoCollectionDrop() {
        doReturn(mongoCollectionMock).when(gameDatabase).getMongoCollection();
        gameDatabase.drop();
        verify(mongoCollectionMock).drop();
    }

    @Test
    public void whenDropThenTrue() {
        doReturn(mongoCollectionMock).when(gameDatabase).getMongoCollection();
        assertTrue(gameDatabase.drop());
    }

    @Test
    public void givenExceptionWhenDropThenFalse() {
        doThrow(new MongoException("exception")).when(mongoCollectionMock).drop();
        doReturn(mongoCollectionMock).when(gameDatabase).getMongoCollection();
        assertFalse(gameDatabase.drop());
    }
}
