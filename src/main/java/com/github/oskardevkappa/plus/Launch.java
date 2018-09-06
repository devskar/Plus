package com.github.oskardevkappa.plus;

import com.github.oskardevkappa.plus.core.Bot;
import com.github.oskardevkappa.plus.core.Config;
import com.github.oskardevkappa.plus.core.Database;
import com.github.oskardevkappa.plus.utils.TagHandler;

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

        TagHandler tagHandler = new TagHandler();

        Database database = new Database(config, tagHandler);
        database.connect("plus");

        Bot bot = new Bot(config, database, tagHandler, tagHandler);
        bot.launch();

        tagHandler.setDatabase(database);

        tagHandler.setTags(database.getTags());
    }
}
