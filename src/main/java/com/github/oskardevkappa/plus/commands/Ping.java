package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.List;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 22.08.2018
 */

public class Ping implements ICommand {

    public Ping()
    {

    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        event.getChannel().sendMessage(String.valueOf(event.getJDA().getPing() + "ms")).queue();

    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.PUBLIC, Permission.MESSAGE_WRITE, "ping");
    }

    @Override
    public String getInfo()
    {
        return "Checks the bot's latency.";
    }
}
