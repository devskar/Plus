package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

import static com.github.oskardevkappa.plus.utils.Const.*;

import java.awt.*;
import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 01.09.2018
 *
 * inspired by https://github.com/Biospheere/c0debaseBot
 */

public class YesNo implements ICommand {

    public YesNo()
    {

    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        if (args.length < 1)
        {
            channel.sendMessage("You need to submit a question").queue();
            return;
        }

        String question = String.join(" ", args);

        Random random = new Random();

        boolean bool = random.nextBoolean();

        String answer = bool ? yesAnswers.get(random.nextInt(yesAnswers.size())) : noAnswers.get(random.nextInt(noAnswers.size()));

        EmbedBuilder response = new EmbedBuilder()
                .addField("Q:", question, false)
                .addField("A:", answer, false)
                .setImage(bool ? yesGifs.get(random.nextInt(yesGifs.size())) : noGifs.get(random.nextInt(noGifs.size())))
                .setColor(bool ? Color.green : Color.red);

        channel.sendMessage(response.build()).queue();
    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.PUBLIC, Permission.MESSAGE_WRITE, "yesno", "yn", "qa");
    }

    @Override
    public String getInfo()
    {
        return "Gives you an answer to a yes/no question!";
    }
}
