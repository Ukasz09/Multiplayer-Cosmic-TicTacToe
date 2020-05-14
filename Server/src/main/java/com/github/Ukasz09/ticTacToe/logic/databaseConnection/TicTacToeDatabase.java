package com.github.Ukasz09.ticTacToe.logic.databaseConnection;

import com.github.Ukasz09.ticTacToe.controller.Logger;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import org.jongo.Jongo;
import org.jongo.MongoCollection;

import java.net.UnknownHostException;

public class TicTacToeDatabase {
    public static final String DATABASE_NAME = "TicTacToe";
    public static final String COLLECTION_NAME = "Game";

    private MongoCollection mongoCollection;

    //----------------------------------------------------------------------------------------------------------------//
    public TicTacToeDatabase() throws UnknownHostException {
        DB db = new MongoClient().getDB(DATABASE_NAME);
        mongoCollection = new Jongo(db).getCollection(COLLECTION_NAME);
    }

    //----------------------------------------------------------------------------------------------------------------//
    public MongoCollection getMongoCollection() {
        return mongoCollection;
    }

    public boolean saveMove(TicTacToeBean bean) {
        try {
            getMongoCollection().save(bean);
            return true;
        } catch (MongoException e) {
            Logger.logError(getClass(), e.getMessage());
            return false;
        }
    }

    public boolean drop() {
        try {
            getMongoCollection().drop();
            return true;
        } catch (MongoException e) {
            Logger.logError(getClass(), e.getMessage());
            return false;
        }
    }
}
