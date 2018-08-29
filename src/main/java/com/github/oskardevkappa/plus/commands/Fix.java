package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.entities.CommandSettings;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 29.08.2018
 */

public class Fix implements ICommand{

    public Fix()
    {

    }


    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

    }

    @Override
    public CommandSettings getSettings()
    {
        return null;
    }

    @Override
    public String getInfo()
    {
        return null;
    }
}
