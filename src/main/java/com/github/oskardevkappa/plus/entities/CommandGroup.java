package com.github.oskardevkappa.plus.entities;

public enum CommandGroup {

    NUll(0),
    PUBLIC(1),
    MODERATOR(2),
    OWNER(3);

    private final int i;

    CommandGroup(int i)
    {
        this.i = i;
    }

    public int getNum()
    {
        return i;
    }

/*    public CommandGroup get(int i)
    {

    }*/
}
