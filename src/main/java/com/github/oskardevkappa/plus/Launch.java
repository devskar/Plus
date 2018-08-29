package com.github.oskardevkappa.plus;

import com.github.oskardevkappa.plus.core.Bot;
import com.github.oskardevkappa.plus.core.Config;
import com.github.oskardevkappa.plus.core.Database;

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

        Database database = new Database(config);
        database.connect("plus");

        Bot bot = new Bot(config, database);
        bot.launch();
    }

}
