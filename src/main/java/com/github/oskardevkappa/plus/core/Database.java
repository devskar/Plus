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

    public Database connect(final String databaseName)
    {

        this.clientURI = new MongoClientURI(config.getDb_ip());

        this.client = new MongoClient(clientURI);

        this.database = client.getDatabase(databaseName);

        return this;
    }

    public MongoCollection getCollection(final String collectionName)
    {
        return database.getCollection(collectionName);
    }

    public Database write(final MongoCollection collection, final  Document document)
    {

        collection.insertOne(document);

        return this;
    }

    public boolean entryExists(final MongoCollection collection, final String field, final String value)
    {

        List<Document> entries = new ArrayList<>();

        collection.find(Filters.regex(field, value)).into(entries);

        return entries.size() != 0;
    }

    public boolean memberExists(final Member member)
    {
        List<Document> memberDocs = new ArrayList<>();

        this.getCollection("member").find(Filters.regex("ID", member.getUser().getId())).into(memberDocs);

        if (memberDocs.isEmpty())
            return false;

        for (Document document : memberDocs)
        {
            String docGuildID = (String) document.get("guildID");

            if (docGuildID.equals(member.getGuild().getId()))
            {
                return true;
            }
        }
        return false;
    }

    public boolean memberExists(final String memberID, final String guildID)
    {
        List<Document> memberDocs = new ArrayList<>();

        this.getCollection("member").find(Filters.regex("ID", memberID)).into(memberDocs);

        if (memberDocs.isEmpty())
            return false;

        for (Document document : memberDocs)
        {

            String docGuildID = (String) document.get("guildID");

            if (docGuildID.equals(guildID))
                return true;
        }

        return false;
    }

    public boolean userExists(final User user)
    {

        List<Document> userDocs = new ArrayList<>();

        this.getCollection("user").find(Filters.regex("ID", user.getId())).into(userDocs);

        return !userDocs.isEmpty();
    }

    public boolean userExists(final String userID)
    {
        List<Document> userDocs = new ArrayList<>();

        this.getCollection("user").find(Filters.regex("ID", userID)).into(userDocs);

        return !userDocs.isEmpty();
    }

    public Database newMember(final Member member)
    {

        if (this.memberExists(member))
            return this;

        System.out.println(member.getEffectiveName()    );

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

        if (this.userExists(user))
            return this;


        Document document = new Document();

        document
                .append("ID", user.getId())
                .append("group", CommandGroup.PUBLIC.getNum())
                .append("premium", false);

        this.write(this.getCollection("user"), document);

        return this;
    }

    public Document getMemberDoc(Member member)
    {
        List<Document> memberDocs = new ArrayList<>();

        this.getCollection("member").find(Filters.regex("ID", member.getUser().getId())).into(memberDocs);

        for (Document doc : memberDocs)
        {
            String guildID = (String) doc.get("guildID");

            if (guildID.equals(member.getGuild().getId()))
                return doc;
        }

        return null;
    }

    public Document getMemberDoc(String memberID, String guildID)
    {
        List<Document> memberDocs = new ArrayList<>();

        this.getCollection("member").find(Filters.regex("ID", memberID)).into(memberDocs);

        for (Document doc : memberDocs)
        {
            String docGuildID = (String) doc.get("guildID");

            if (guildID.equals(docGuildID))
                return doc;
        }

        return null;
    }

    public Document getUserDoc(String ID)
    {
        List<Document> userDocs = new ArrayList<>();

        this.getCollection("user").find(Filters.regex("ID", ID)).into(userDocs);

        return userDocs.get(0);
    }

    public Document getUserDoc(User user)
    {
        List<Document> userDocs = new ArrayList<>();

        this.getCollection("user").find(Filters.regex("ID", user.getId())).into(userDocs);

        return userDocs.get(0);
    }

    public CommandGroup getCommandGroup(final Member member)
    {
        return this.getCommandGroup(member.getUser());
    }

    public CommandGroup getCommandGroup(final User user)
    {
        Document userDoc = this.getUserDoc(user);

        if (config.getOwnerID().equals(user.getId()))
            return CommandGroup.OWNER;

        return CommandGroup.values()[(int) userDoc.get("group")];
    }

    public MongoDatabase getDatabase()
    {
        return database;
    }
}
