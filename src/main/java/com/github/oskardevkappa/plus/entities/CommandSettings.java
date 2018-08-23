package com.github.oskardevkappa.plus.entities;

import net.dv8tion.jda.core.Permission;

import java.util.*;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 19.08.2018
 */

public class CommandSettings {

    private final CommandGroup group;
    private final List<String> labels;
    private final Permission permission;

    public CommandSettings(CommandGroup group, Permission permission, String... labels)
    {
        this.permission = permission;
        this.group = group;
        this.labels = Arrays.asList(labels);
    }

    public CommandGroup getGroup()
    {
        return group;
    }

    public List<String> getLabels()
    {
        return labels;
    }

    public Permission getPermission()
    {
        return permission;
    }
}
