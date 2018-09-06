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

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 05.09.2018
 */

public class ChuckNorris implements ICommand {

    private static final List<String> categories = Arrays.asList("explicit","dev","movie","food","celebrity","science","sport","political","religion","animal","history","music","travel","career","money","fashion");

    private  String api_url = "https://api.chucknorris.io/jokes/random";

    public ChuckNorris()
    {

    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {
        if (args.length > 1)
        {
            if(categories.stream().anyMatch(args[1]::equalsIgnoreCase))
            {
                api_url += "?category=" + args[1].toLowerCase();
            }
        }

        Request request = new Request.Builder()
                .url(api_url).build();

        String joke = "", url = "", icon_url = "";

        try
        {
            Response response = new OkHttpClient().newCall(request).execute();

            JSONObject object = new JSONObject(response.body().string());

            joke = object.getString("value");
            url = object.getString("url");
            icon_url = object.getString("icon_url");

        } catch (IOException e)
        {
            channel.sendMessage("Something went wrong!").queue();
            e.printStackTrace();
        }

        channel.sendMessage(new EmbedBuilder().setColor(event.getGuild().getSelfMember().getRoles().get(0).getColor()).setAuthor("Chuck Norris Joke", url, null).setDescription(joke).setThumbnail(icon_url).build()).queue();

    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.PUBLIC, Permission.MESSAGE_WRITE, "ChuckNorris", "Chuck", "Norris");
    }

    @Override
    public String getInfo()
    {
        return "Tells you a funny ChuckNorris joke";
    }
}
