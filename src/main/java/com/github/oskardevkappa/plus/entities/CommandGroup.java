package com.github.oskardevkappa.plus.entities;

public enum CommandGroup {

    OWNER(3),
    MODERATOR(2),
    PUBLIC(1),
    NUll(0);

    private final int i;

    CommandGroup(int i)
    {
        this.i = i;
    }

    public int getNum()
    {
        return i;
    }
}
