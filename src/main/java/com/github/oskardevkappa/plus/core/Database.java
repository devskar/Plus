package com.github.oskardevkappa.plus.core;

import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

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

    public boolean entryExists(MongoCollection collection, String field, String value)
    {

        List<Document> entries = new ArrayList<>();

        collection.find(Filters.regex(field, value)).into(entries);

        return entries.size() != 0;
    }

    public Database newMember(Member member)
    {

        Document document = new Document();

        document
                .append("ID", member.getUser().getId())
                .append("guildID", member.getGuild().getId())
                .append("blocked", false);

        this.write(this.getCollection("member"), document);
        return this;
    }

    public Database newUser(User user)
    {
        Document document = new Document();

        document
                .append("ID", user.getId())
                .append("group", CommandGroup.PUBLIC.getNum())
                .append("premium", false);

        this.write(this.getCollection("user"), document);

        return this;
    }

    public void insert(Member member)
    {
        MongoCollection collection = database.getCollection("member");

        if (!this.entryExists(collection, "ID", member.getUser().getId()))
        {
            this.newMember(member);
        } else
        {
            List<Document> entries = new ArrayList<>();

            collection.find(Filters.regex("ID", member.getUser().getId())).into(entries);

            boolean contains = false;

            for (Document entry : entries)
            {
                if (entry.get("guildID").equals(member.getGuild().getId()))
                    contains = true;
            }

            if (!contains)
                this.newMember(member);
        }
    }

    public void insert(User user)
    {

        MongoCollection collection = database.getCollection("user");

        if (!this.entryExists(collection, "ID", user.getId()))
            this.newUser(user);
    }

    public void insert(Member member, User user)
    {
        this.insert(member);
        this.insert(user);
    }

    public void insert(User user, Member member)
    {
        this.insert(member);
        this.insert(user);
    }

    public MongoDatabase getDatabase()
    {
        return database;
    }
}
