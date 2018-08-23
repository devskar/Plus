package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;
import java.util.List;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 22.08.2018
 */

public class Google implements ICommand{

    private static final String REPLACEMENT = "%2B";
    private static final String DEFAULT_URL = "http://lmgtfy.com/?iie=1&q=";

    public Google()
    {

    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        if (args.length < 1)
        {
            channel.sendMessage("You need to insert some text!").queue();
            return;
        }

        List<String> arguments = Arrays.asList(args);

        arguments.forEach(arg -> {
            arg.replaceAll("\\+", REPLACEMENT)
                    .replaceAll("\\s", "")
                    .replaceAll("%20", "+")
                    .replaceAll("\\*", "%2A")
                    .replaceAll("/", "%2F")
                    .replaceAll("@", "%40");
        });

        channel.sendMessage(DEFAULT_URL + String.join("+", arguments).replaceFirst("\\s", "")).queue();
    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.PUBLIC, Permission.MESSAGE_WRITE, "google", "g", "lmgtfy");
    }

    @Override
    public String getInfo()
    {
        return "Sends a link for people who don't know how to google.";
    }
}
