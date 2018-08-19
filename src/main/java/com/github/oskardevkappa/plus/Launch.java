package com.github.oskardevkappa.plus;

import com.github.oskardevkappa.plus.core.Bot;
import com.github.oskardevkappa.plus.core.Config;

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

        Bot bot = new Bot(config);
        bot.launch();
    }

}
