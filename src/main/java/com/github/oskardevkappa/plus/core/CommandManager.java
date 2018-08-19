package com.github.oskardevkappa.plus.core;

import com.github.oskardevkappa.plus.commands.ICommand;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Set;
import java.util.regex.Pattern;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 19.08.2018
 */

public class CommandManager extends ListenerAdapter {

    private final Config config;

    private Set<ICommand> commands;

    public CommandManager(Config config)
    {
        this.config = config;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {

        final String prefix = String.format("\\%s", config.getPrefix());
        String raw = "\\" + event.getMessage().getContentRaw();

        if (!raw.startsWith(prefix))
        {
            return;
        }

        final  String content = event.getMessage().getContentRaw().replaceFirst(prefix, "");
        commands.forEach(command -> {

            command.getSettings().getLabels().forEach(label -> {
                if (content.startsWith(label))
                {
                    command.onCommand(event, content.replaceFirst(label, "").split(" "), label);
                    return;
                }
            });

        });

    }

    public Config getConfig()
    {
        return config;
    }

    public Set<ICommand> getCommands()
    {
        return commands;
    }

    public void setCommands(Set<ICommand> commands)
    {
        this.commands = commands;
    }
}
