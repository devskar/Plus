package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.core.Database;
import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import com.mongodb.client.model.Filters;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;

import javax.print.Doc;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 29.08.2018
 */

public class Profile implements ICommand{

    private final Database database;

    public Profile(Database database)
    {
        this.database = database;
    }


    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        List<Document> documents = new ArrayList<>();
        database.getCollection("user").find(Filters.regex("ID", member.getUser().getId())).into(documents);

        Document document = documents.get(0);

        Boolean premium = document.getBoolean("premium");
        CommandGroup group = CommandGroup.values()[(int) document.get("group")];

        Member target = event.getMember();

        EmbedBuilder eb = new EmbedBuilder();

        eb
                .setColor(member.getColor())
                .setThumbnail(member.getUser().getEffectiveAvatarUrl())
                .setTitle("Here is your profile!")
                .addField("Premium", String.valueOf(premium), false)
                .addField("Group", group.toString().toLowerCase(), false)
                .setFooter(target.getEffectiveName(), target.getUser().getEffectiveAvatarUrl())
                .setTimestamp(Instant.now());

        channel.sendMessage(eb.build()).queue();
    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.PUBLIC, Permission.MESSAGE_WRITE, "profile");
    }

    @Override
    public String getInfo()
    {
        return null;
    }
}
