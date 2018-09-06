package com.github.oskardevkappa.plus.core;

import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.Tag;
import com.github.oskardevkappa.plus.utils.TagHandler;
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
    private final TagHandler tagHandler;

    private MongoClientURI clientURI;
    private MongoClient client;
    private MongoDatabase database;

    public Database(Config config, TagHandler tagHandler)
    {
        this.config = config;
        this.tagHandler = tagHandler;
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

        if (member == null)
            return true;

        if (member.getUser().isFake())
            return true;

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

    public boolean tagExists(String name, String guildId)
    {

        for (Tag tag: tagHandler.getTags())
        {
            if (tag.getGuildId().equals(guildId) && tag.getName().equals(name))
                return true;
        }

        return false;
    }

    public boolean tagExists(Tag tag)
    {
        return tagExists(tag.getName(), tag.getGuildId());
    }

    public Database createTag(Tag tag)
    {

        if (this.tagExists(tag.getName(), tag.getGuildId()))
            return this;

        tagHandler.add(tag);

        Document document = new Document();

        document.append("guildId", tag.getGuildId())
                .append("memberId", tag.getMemberId())
                .append("name", tag.getName())
                .append("content", tag.getContent());

        this.write(this.getCollection("tag"), document);

        return this;
    }

    public Document getTagDoc(String guildId, String tagName)
    {
        List<Document> tagDocs = new ArrayList<>();

        this.getCollection("tag").find(Filters.regex("guildId", guildId)).into(tagDocs);

        for (Document doc : tagDocs)
        {
            String docTagName = (String) doc.get("name");

            if (docTagName.equals(tagName))
                return doc;
        }

        return null;
    }

    public Document getTagDoc(Tag tag)
    {
        return this.getTagDoc(tag.getGuildId(), tag.getName());
    }

    public List<Tag> getTags()
    {
        MongoCollection collection = this.getCollection("tag");
        MongoCursor cursor = collection.find().iterator();

        List<Tag> tags = new ArrayList<>();

        while(cursor.hasNext())
        {
            Document document = (Document) cursor.next();

            tags.add(new Tag((String) document.get("guildId"), (String) document.get("memberId"), (String) document.get("name"), (String) document.get("content")));
        }

        return tags;
    }

    public void removeTag(Tag tag)
    {
        if (this.tagExists(tag))
            this.getCollection("tag").deleteOne(this.getTagDoc(tag));

    }

    public MongoDatabase getDatabase()
    {
        return database;
    }
}
