package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.entities.CommandSettings;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;

public interface ICommand {

    void onCommand(GuildMessageReceivedEvent event, String[] args, String label);

    CommandSettings getSettings();

    String getInfo();



}
