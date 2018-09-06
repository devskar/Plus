package com.github.oskardevkappa.plus.commands.apis;

import com.github.oskardevkappa.plus.commands.ICommand;
import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONObject;

import java.io.IOException;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 05.09.2018
 */

public class TrumpQuote implements ICommand {

    private final String api_url = "https://api.whatdoestrumpthink.com/api/";
    private final String api_random = "v1/quotes/random/";

    public TrumpQuote()
    {

    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        Request request = new Request.Builder()
                .url(api_url + api_random).build();

        try
        {
            Response response = new OkHttpClient().newCall(request).execute();

            JSONObject object = new JSONObject(response.body().string());

            String message = object.getString("message");

            channel.sendMessage(message).queue();

        } catch (IOException e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.PUBLIC, Permission.MESSAGE_WRITE, "TrumpQuote", "Trump");
    }

    @Override
    public String getInfo()
    {
        return null;
    }
}
