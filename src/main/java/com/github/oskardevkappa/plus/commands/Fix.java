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
 * 29.08.2018
 */

public class Fix implements ICommand{

    private final Database database;

    public Fix(Database database)
    {
        this.database = database;
    }


    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        /*event.getGuild().getMembers().forEach(database::newMember);*/

        CommandGroup commandGroup = database.getCommandGroup(member);

        if (commandGroup.getNum() < CommandGroup.MODERATOR.getNum())
        {
            database.newMember(member);
            channel.sendMessage("You got fixed!").queue();
        }
        else
        {
            for(Member member1 : event.getGuild().getMembers())
            {
                database.newMember(member1).newUser(member1.getUser());
            }
            channel.sendMessage("Guild got fixed!").queue();
        }
    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.PUBLIC, Permission.MESSAGE_WRITE, "fix");
    }

    @Override
    public String getInfo()
    {
        return null;
    }
}
