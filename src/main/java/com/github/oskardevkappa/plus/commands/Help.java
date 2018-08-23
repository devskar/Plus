package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.core.CommandManager;
import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Formatter;
import java.util.List;
import java.util.Locale;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 22.08.2018
 */

public class Help implements ICommand{

    private final CommandManager manager;

    public Help(CommandManager manager)
    {
        this.manager = manager;
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        StringBuilder stringBuilder = new StringBuilder("**Current available commands!**\n\n");

        manager.getCommands().forEach(command -> {
            stringBuilder.append(command.getSettings().getLabels().get(0)).append(" \t-> *").append(command.getInfo()).append("*\n");
        });

        event.getChannel().sendMessage(stringBuilder.toString()).queue();

    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.PUBLIC, Permission.MESSAGE_WRITE, "help");
    }

    @Override
    public String getInfo()
    {
        return null;
    }
}
