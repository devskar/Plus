package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import com.github.oskardevkappa.plus.utils.TagHandler;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 05.09.2018
 */

public class Tag implements ICommand {

    private final TagHandler tagHandler;

    public Tag(TagHandler tagHandler)
    {
        this.tagHandler = tagHandler;
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        if (args.length < 1)
        {
            channel.sendMessage("nope").queue();
            return;
        }

        if (tagHandler.getTagByName(args[0], event.getGuild().getId()) == null)
        {
            channel.sendMessage("no tags found").queue();
            return;
        }

        com.github.oskardevkappa.plus.entities.Tag tag = tagHandler.getTagByName(args[0], event.getGuild().getId());

        channel.sendMessage(tag.getContent()).queue();
    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.PUBLIC, Permission.MESSAGE_WRITE, "tag", "t");
    }

    @Override
    public String getInfo()
    {
        return null;
    }
}
