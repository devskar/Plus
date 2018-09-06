package com.github.oskardevkappa.plus.utils;

import com.github.oskardevkappa.plus.core.Database;
import com.github.oskardevkappa.plus.entities.Tag;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 05.09.2018
 */

public class TagHandler {

    private List<Tag> tags;
    private Database database;

    public TagHandler()
    {
        tags = new ArrayList<>();
    }

    public void add(Tag tag)
    {
        tags.add(tag);
    }

    public List<Tag> getTags()
    {
        return tags;
    }

    public void setTags(List<Tag> tags)
    {
        this.tags = tags;
    }

    public List<Tag> getTagsByGuild(String guildId)
    {
        return this.tags.stream().filter(tag -> tag.getGuildId().equals(guildId)).collect(Collectors.toList());
    }

    public List<Tag> getTagsByGuild(Guild guild)
    {
        return getTagsByGuild(guild.getId());
    }

    public List<Tag> getTagsByMember(String memberId)
    {
        return this.tags.stream().filter(tag -> tag.getMemberId().equals(memberId)).collect(Collectors.toList());
    }

    public List<Tag> getTagsByMember(Member member)
    {
        return this.getTagsByMember(member.getUser().getId());
    }

    public List<Tag> getTagsByMemberOnGuild(String memberId, String guildId)
    {
        return this.getTagsByGuild(guildId).stream().filter(tag -> tag.getMemberId().equals(memberId)).collect(Collectors.toList());
    }

    public List<Tag> getTagsByMemberOnGuild(Member member, Guild guild)
    {
        return getTagsByMemberOnGuild(member.getUser().getId(), guild.getId());
    }

    public Tag getTagByName(String name, String guildId)
    {
        List<Tag> tags = this.getTagsByGuild(guildId).stream().filter(tag -> tag.getName().equals(name)).collect(Collectors.toList());

        return tags.isEmpty() ? null : tags.get(0);
    }

    public void createTag(Tag tag)
    {
        database.createTag(tag);
    }

    public void removeTag(Tag tag)
    {
        database.removeTag(tag);
    }

    public void setDatabase(Database database)
    {
        this.database = database;
    }
}
