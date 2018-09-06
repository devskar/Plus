package com.github.oskardevkappa.plus.utils;

import net.dv8tion.jda.core.entities.Icon;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.Webhook;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 01.09.2018
 */

public class Util {

    public Util()
    {

    }

    public static Icon getIcon(String url) {
        URLConnection con;
        Icon i = null;
        try {
            con = new URL(url).openConnection();
            con.addRequestProperty("User-Agent", "Mozilla/4.0");
            try (InputStream in = con.getInputStream()) {
                i = Icon.from(in);
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return i;
    }
}
