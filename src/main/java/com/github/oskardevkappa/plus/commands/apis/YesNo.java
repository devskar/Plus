package com.github.oskardevkappa.plus.commands.apis;

import com.github.oskardevkappa.plus.commands.ICommand;
import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import static com.github.oskardevkappa.plus.utils.Const.*;

import java.awt.*;
import java.io.IOException;
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

    private final String api_url = "https://yesno.wtf/api";

    private final Random random;

    public YesNo()
    {
        this.random = new Random();
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
        Request request = new Request.Builder().url(api_url).build();

        boolean bool = false;
        String gif = "";

        try
        {
            Response response = new OkHttpClient().newCall(request).execute();

            JSONObject object = new JSONObject(response.body().string());

            bool = object.getString("answer").equals("yes");
            gif = object.getString("image");

        } catch (IOException e)
        {
            e.printStackTrace();
        }

        EmbedBuilder response = new EmbedBuilder()
                .setAuthor("Yes-No Answer", api_url)
                .addField("Q:", question, false)
                .addField("A:", bool ? yesAnswers.get(random.nextInt(yesAnswers.size())) : noAnswers.get(random.nextInt(noAnswers.size())), false)
                .setImage(gif)
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
