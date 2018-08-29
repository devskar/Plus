package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 27.08.2018
 */

public class Block implements ICommand {

    public Block()
    {

    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {
        event.getGuild().getMemberById("").getUser().openPrivateChannel().complete().sendMessage("Spam").queue();
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
