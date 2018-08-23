package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import java.awt.*;
import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 22.08.2018
 */

public class Eval implements ICommand {

    public Eval()
    {

    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        Binding bindings = new Binding();
        bindings.setVariable("jda", event.getJDA());
        bindings.setVariable("channel", channel);
        bindings.setVariable("guild", event.getGuild());
        bindings.setVariable("bot", event.getGuild().getSelfMember());

        GroovyShell shell = new GroovyShell(bindings);

        Object result = null;

        try
        {
            result = shell.evaluate(Arrays.toString(args).replaceAll("#", "()."));
        }catch (Exception e)
        {
            channel.sendMessage(new EmbedBuilder().setColor(Color.red).setTitle("A wild error appears").setDescription(e.toString()).build()).queue();
        }


        ArrayList<Object> list = (ArrayList<Object>) result;

        channel.sendMessage(list.stream().map(Object::toString).collect(Collectors.joining(" "))).queue();

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
