package com.github.oskardevkappa.plus.entities;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 05.09.2018
 */

public class Tag {

    private String guildId, memberId, name, content;

    public Tag(String guildId, String memberId, String name, String content)
    {
        this.guildId = guildId;
        this.memberId = memberId;
        this.name = name;
        this.content = content;
    }

    public String getGuildId()
    {
        return guildId;
    }

    public void setGuildId(String guildId)
    {
        this.guildId = guildId;
    }

    public String getMemberId()
    {
        return memberId;
    }

    public void setMemberId(String memberId)
    {
        this.memberId = memberId;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }
}
