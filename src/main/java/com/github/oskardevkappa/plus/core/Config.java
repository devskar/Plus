package com.github.oskardevkappa.plus.core;

import java.io.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @author oskar
 * github.com/oskardevkappa/
 * <p>
 * 19.08.2018
 */

public class Config {

    private static final String FILE_NAME = "config.properties";
    private String token, prefix, ownerID, db_ip, db_user, db_pw;
    private final Properties properties;
    private final File file;
    private static final String[] keys = {"TOKEN", "PREFIX", "OWNER", "DATABASE_IP", "DATABASE_USER", "DATABASE_PW"};

    public Config()
    {
        this.properties = new Properties();
        this.file = new File(FILE_NAME);

        try
        {
            if(file.createNewFile())
            {
                Arrays.asList(keys)
                        .forEach(key ->
                                properties.setProperty(key, ""));
                properties.store(new FileOutputStream(file), "Insert the data below!");

               System.exit(1);
            }else
            {
                this.read();
            }
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void read()
    {
        try
        {
            properties.load(new FileReader(file));
        } catch (IOException e)
        {
            e.printStackTrace();
            System.exit(1);
        }

        this.token = properties.getProperty(keys[0]);
        this.prefix = properties.getProperty(keys[1]);
        this.ownerID = properties.getProperty(keys[2]);
        this.db_ip = properties.getProperty(keys[3]);
        this.db_user = properties.getProperty(keys[4]);
        this.db_pw = properties.getProperty(keys[5]);

    }

    public String getToken()
    {
        return token;
    }

    public String getPrefix()
    {
        return prefix;
    }

    public String getOwnerID()
    {
        return ownerID;
    }

    public String getDb_ip()
    {
        return db_ip;
    }

    public String getDb_user()
    {
        return db_user;
    }

    public String getDb_pw()
    {
        return db_pw;
    }
}
