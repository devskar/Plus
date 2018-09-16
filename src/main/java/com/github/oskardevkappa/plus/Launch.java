package com.github.oskardevkappa.plus;

import com.github.oskardevkappa.plus.core.Bot;
import com.github.oskardevkappa.plus.core.Config;
import com.github.oskardevkappa.plus.core.Database;
import com.github.oskardevkappa.plus.manager.TagManager;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 19.08.2018
 */

public class Launch {

    public static void main(String args[])
    {
        Config config = new Config();

        TagManager tagManager = new TagManager();

        Database database = new Database(config, tagManager);
        database.connect("plus");

        Bot bot = new Bot(config, database, tagManager, tagManager);
        bot.launch();

        tagManager.setDatabase(database);

        tagManager.setTags(database.getTags());
    }
}
