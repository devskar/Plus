package com.github.oskardevkappa.plus.listeners;

import com.github.oskardevkappa.plus.core.Database;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.GuildJoinEvent;
import net.dv8tion.jda.core.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 29.08.2018
 */

public class DatabaseListener extends ListenerAdapter {

    private final Database database;

    public DatabaseListener(Database database)
    {
        this.database = database;
    }

    @Override
    public void onGuildJoin(GuildJoinEvent event)
    {
        event.getGuild().getMembers().forEach(database::newMember);
        event.getGuild().getMembers().forEach(member -> database.newUser(member.getUser()));
    }

    @Override
    public void onGuildLeave(GuildLeaveEvent event)
    {
        super.onGuildLeave(event);
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {
        database.newUser(event.getAuthor()).newMember(event.getMember());
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event)
    {
        database.newUser(event.getAuthor());
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event)
    {
        database.newUser(event.getUser()).newMember(event.getMember());
    }

    @Override
    public void onGuildMemberLeave(GuildMemberLeaveEvent event)
    {
        super.onGuildMemberLeave(event);
    }
}
