package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.core.Database;
import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 27.08.2018
 */

public class Block implements ICommand {

    private final Database database;

    public Block(Database database)
    {
        this.database = database;
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        Document memberDoc = database.getMemberDoc(member);
        MongoCollection collection = database.getCollection("member");

        Member target = event.getMessage().getMentionedMembers().get(0);

        if (memberDoc.getBoolean("blocked"))
        {
            channel.sendMessage("This user is already blocked!").queue();
            return;
        }

        Document newDoc = memberDoc;

        memberDoc.replace("blocked", true);

        collection.replaceOne(memberDoc, newDoc);

        channel.sendMessage("Member got blocked!").queue();


    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.PUBLIC, Permission.MANAGE_PERMISSIONS, "block", "deny");
    }

    @Override
    public String getInfo()
    {
        return "Blocks an user to use commands!";
    }
}
