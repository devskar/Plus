package com.github.oskardevkappa.plus.core;

import com.github.oskardevkappa.plus.commands.ICommand;
import com.github.oskardevkappa.plus.entities.CommandGroup;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.util.Arrays;
import java.util.List;
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
    private final Database database;

    private Set<ICommand> commands;

    public CommandManager(Config config, Database database)
    {
        this.config = config;
        this.database = database;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event)
    {

        final String prefix = String.format("\\%s", config.getPrefix());
        String raw = "\\" + event.getMessage().getContentRaw();

        database.newMember(event.getMember());

        if (!raw.startsWith(prefix))
            return;

        if (event.getMember() == event.getGuild().getSelfMember())
            return;


        final  String content = event.getMessage().getContentRaw().replaceFirst(prefix, "");


        for (ICommand command : commands)
        {
            for (String label : command.getSettings().getLabels())
            {
                if (content.split("\\s")[0].equalsIgnoreCase(label))
                {
                    String string = content.replaceFirst(label, "");

                    while(string.startsWith("\\s"))
                        string.replaceFirst("\\s", "");

                    String[] args = string.split("\\s");

                    if (args[0].equals(""))
                        args = Arrays.copyOfRange(args, 1, args.length);

                    Member member = event.getMember();
                    TextChannel channel = event.getChannel();

                    CommandGroup group;

                    if (member.getUser().getId().equals(config.getOwnerID()))
                        group = CommandGroup.OWNER;
                    else
                        group = CommandGroup.PUBLIC;

                    if (command.getSettings().getGroup().getNum() < group.getNum())
                    {
                        command.onCommand(event, channel, member, args, label);
                    }
                    else if (command.getSettings().getGroup().getNum() == group.getNum())
                    {
                        if (!member.hasPermission(channel, command.getSettings().getPermission()))
                        {
                            channel.sendMessage("Missing permission: " + command.getSettings().getPermission().getName()).queue();
                            return;
                        }
                        command.onCommand(event, channel, member, args, label);
                    }
                    else
                    {
                        channel.sendMessage("Missing permission!").queue();
                    }
                    break;
                }
            }
        }
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
