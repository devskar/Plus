package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.entities.CommandGroup;
import com.github.oskardevkappa.plus.entities.CommandSettings;
import com.github.oskardevkappa.plus.utils.Util;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.Webhook;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.webhook.WebhookClient;
import net.dv8tion.jda.webhook.WebhookMessage;
import net.dv8tion.jda.webhook.WebhookMessageBuilder;

import javax.xml.bind.annotation.XmlType;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 22.08.2018
 */

public class Google implements ICommand{

    private static final String REPLACEMENT = "%2B";
    private static final String DEFAULT_URL = "http://lmgtfy.com/?iie=1&q=";
    private static final String DEFAULT_WEBHOOK_NAME = "Plus";

    public Google()
    {

    }

    @Override
    public void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label)
    {

        if (args.length < 1)
        {
            channel.sendMessage("You need to insert some text!").queue();
            return;
        }

        List<String> arguments = Arrays.asList(args);

        Map<String, String> replacements = new HashMap<>();

        replacements.put("\\+", "%2B");
        replacements.put("\\/", "%2F");
        replacements.put("\\@", "%40");
        replacements.put("\\*", "%2F");

        for (String arg : arguments)
        {
            for (String key : replacements.keySet())
            {
                arg = arg.replaceAll(key, replacements.get(key));
            }
        }

        if (event.getGuild().getSelfMember().hasPermission(channel, Permission.MESSAGE_MANAGE))
            event.getMessage().delete().queue();

        if(!event.getGuild().getSelfMember().hasPermission(channel, Permission.MANAGE_WEBHOOKS))
        {
            channel.sendMessage("Hey you should take a look at: " + DEFAULT_URL + String.join("+", arguments).replaceFirst("\\s", "")).queue();
            return;
        }

        WebhookMessage message = new WebhookMessageBuilder()
                .setAvatarUrl(member.getUser().getEffectiveAvatarUrl())
                .setUsername(member.getEffectiveName())
                .setContent("Hey you should take a look at: " + DEFAULT_URL + String.join("+", arguments).replaceFirst("\\s", "").replaceAll("@", "(at)"))
                .build();


        channel.getWebhooks().queue(webhooks -> {

            boolean exists = false;

            for (Webhook webhook: webhooks)
            {
                if (webhook.getName().equals(DEFAULT_WEBHOOK_NAME))
                {
                    WebhookClient client = webhook.newClient().build();

                    client.send(message);
                    client.close();

                    exists = true;
                    break;
                }
            }

            if(!exists)
            {
                if (event.getGuild().getSelfMember().hasPermission(channel, Permission.MANAGE_WEBHOOKS))
                {
                    channel.createWebhook(DEFAULT_WEBHOOK_NAME)
                            .setAvatar(Util.getIcon(event.getJDA().getSelfUser().getEffectiveAvatarUrl()))
                            .setName(DEFAULT_WEBHOOK_NAME).queue(webhook1 -> {

                        WebhookClient client = webhook1.newClient().build();

                        client.send(message);
                        client.close();
                    });
                }else
                {
                    channel.sendMessage("Hey you should take a look at: " + DEFAULT_URL + String.join("+", arguments).replaceFirst("\\s", "").replaceAll("@", "(at)")).queue();
                }
            }

        });

    }

    @Override
    public CommandSettings getSettings()
    {
        return new CommandSettings(CommandGroup.PUBLIC, Permission.MESSAGE_WRITE, "google", "g", "lmgtfy");
    }

    @Override
    public String getInfo()
    {
        return "Sends a link for people who don't know how to google.";
    }
}
