package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.core.Database;
import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.awt.*;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 22.08.2018
 */

public class Eval implements ICommand {

    private final Database database;

    public Eval(Database database)
    {
        this.database = database;
    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        ScriptEngine engine = new ScriptEngineManager().getEngineByName("nashorn");

        String script = String.join(" ", args);

        script = script.replaceAll("#", "().");

        engine.put("guild", event.getGuild());
        engine.put("channel", channel);
        engine.put("member", member);
        engine.put("jda", event.getJDA());
        engine.put("database", database);

        try
        {
            channel.sendMessage(String.valueOf(engine.eval(script))).queue();
        } catch (Exception e)
        {
            channel.sendMessage(new EmbedBuilder().setColor(Color.red).setTitle("An error occured!").setDescription(e.toString()).build()).queue();
        }
    }


    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.OWNER, Permission.MESSAGE_WRITE, "eval");
    }

    @Override
    public String getInfo()
    {
        return "Eval some code!";
    }
}
