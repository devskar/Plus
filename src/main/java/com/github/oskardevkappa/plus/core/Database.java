package com.github.oskardevkappa.plus.core;

import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 23.08.2018
 */

public class Database {

    private final Config config;

    private MongoClientURI clientURI;
    private MongoClient client;
    private MongoDatabase database;

    public Database(Config config)
    {
        this.config = config;
    }

    public Database connect(String databaseName)
    {

        System.out.println(config.getDb_ip());

        this.clientURI = new MongoClientURI(config.getDb_ip());

        this.client = new MongoClient(clientURI);

        this.database = client.getDatabase(databaseName);

        return this;
    }

    public MongoCollection getCollection(String collectionName)
    {
        return database.getCollection(collectionName);
    }

    public Database write(MongoCollection collection, Document document)
    {

        collection.insertOne(document);

        return this;
    }

    public Database cotains(MongoCollection collection, Document document)
    {

        DBCollection test = null;
        DBCursor cursor = test.find();

        return this;
    }


}
