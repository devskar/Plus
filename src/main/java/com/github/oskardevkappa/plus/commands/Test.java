package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.core.Database;
import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;


/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 23.08.2018
 */

public class Test implements ICommand {

    private final Database database;

    public Test(Database database)
    {

        this.database = database;
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        Document document = new Document(args[0], args[1]);

        document.append("oksar", "ist toll");

        database.getCollection("test").insertOne(document);

        database.cotains(database.getCollection("test"), document);

    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.OWNER, Permission.MESSAGE_WRITE, "test");
    }

    @Override
    public String getInfo()
    {
        return null;
    }
}
