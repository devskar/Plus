package com.github.oskardevkappa.plus.core;

import com.github.oskardevkappa.plus.commands.*;
import com.github.oskardevkappa.plus.commands.apis.ChuckNorris;
import com.github.oskardevkappa.plus.commands.apis.TrumpQuote;
import com.github.oskardevkappa.plus.listeners.DatabaseListener;
import com.github.oskardevkappa.plus.utils.TagHandler;
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
    private final Database database;
    private final TagHandler tagHandler;

    private JDABuilder builder;
    private final CommandManager commandManager;

    public Bot(Config config, Database database, TagHandler tagHandler, TagHandler tagHandler1)
    {
        this.database = database;
        this.config = config;
        this.tagHandler = tagHandler;
        this.commandManager = new CommandManager(config, database);
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
        builder.addEventListener(new DatabaseListener(database));
    }

    private void addCommands()
    {
        Set<ICommand> commands = new HashSet<>();

        commands.add(new Google());
        commands.add(new Ping());
        commands.add(new Eval(database));
        commands.add(new Test(database));
        commands.add(new Help(commandManager));
        commands.add(new Profile(database));
        commands.add(new Block(database));
        commands.add(new Fix(database));
        commands.add(new YesNo());
        commands.add(new Tag(tagHandler));
        commands.add(new Tags(tagHandler));
        commands.add(new ChuckNorris());
        commands.add(new TrumpQuote());

        commandManager.setCommands(commands);
    }
}
