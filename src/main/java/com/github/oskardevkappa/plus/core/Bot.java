package com.github.oskardevkappa.plus.core;

import com.github.oskardevkappa.plus.commands.ICommand;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 19.08.2018
 */

public class Bot {

    private JDA jda;
    private final Config config;
    private JDABuilder builder;
    private final CommandManager commandManager;

    public Bot(Config config)
    {
        this.config = config;
        this.commandManager = new CommandManager(config);
    }

    public void launch()
    {
        this.builder = new JDABuilder(AccountType.BOT);
        try
        {
            this.addListener();
            this.addCommands();

            this.jda = builder.setToken(config.getToken()).build().awaitReady();

        } catch (InterruptedException | LoginException e)
        {
            e.printStackTrace();
        }
    }

    private void addListener()
    {
        builder.addEventListener(commandManager);
    }

    private void addCommands()
    {
        Set<ICommand> commands = new HashSet<>();

        commands.add(new Avatar());

        commandManager.setCommands(commands);
    }
}
