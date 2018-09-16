package com.github.oskardevkappa.plus.manager;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 16.09.2018
 */

public class GameManager {

    private final JDA jda;
    private final ScheduledExecutorService service;

    public GameManager(final JDA jda)
    {
        this.jda = jda;
        this.service = Executors.newScheduledThreadPool(1);
    }

    public void run()
    {
        Runnable runnable = () -> {

            int numb1 = ThreadLocalRandom.current().nextInt(10);
            int numb2 = ThreadLocalRandom.current().nextInt(10);

            String status = numb1 + "+" + numb2 + "=" + (numb1+numb2);

            jda.getPresence().setGame(Game.playing(status));

        };
        service.scheduleAtFixedRate(runnable, 10, 120, TimeUnit.SECONDS);
    }

}
