package com.github.oskardevkappa.plus.commands;

import com.github.oskardevkappa.plus.entities.CommandSettings;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;


public interface ICommand {

    void onCommand(GuildMessageReceivedEvent event, TextChannel channel, Member member, String[] args, String label);

    CommandSettings getSettings();

    String getInfo();

}
