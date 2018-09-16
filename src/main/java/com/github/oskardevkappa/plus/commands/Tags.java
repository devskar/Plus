package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import com.github.oskardevkappa.plus.entities.Tag;
import com.github.oskardevkappa.plus.manager.TagManager;
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

public class Tags implements ICommand {

    private final TagManager tagManager;

    public Tags(TagManager tagManager)
    {
        this.tagManager = tagManager;
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        if (args.length < 1)
        {
            channel.sendMessage("nope").queue();
            return;
        }

        String cmd = args[0];
        String tagName = args[1];
        String restlicherContent = String.join(" ", args).replaceFirst(cmd, "").replaceFirst(tagName, "").replaceFirst("\\s", "");

        switch(args[0])
        {
            case "create":
                tagManager.createTag(new Tag(event.getGuild().getId(), member.getUser().getId(), args[1], restlicherContent));
                channel.sendMessage("new tag has been created").queue();
                break;

            case "delete":
                case "remove":

                    Tag tag = tagManager.getTagByName(tagName, event.getGuild().getId());

                    if (tag.getMemberId().equals(member.getUser().getId()))
                    {
                        tagManager.removeTag(tag);
                        channel.sendMessage(String.format("Tag %s has been removed!", tag.getName())).queue();
                    }else
                    {
                        channel.sendMessage("You are not the owner of this tag").queue();
                    }
                break;
        }

    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.PUBLIC, Permission.MESSAGE_WRITE, "tags");
    }

    @Override
    public String getInfo()
    {
        return null;
    }
}
