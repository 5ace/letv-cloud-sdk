package com.github.cuter44.letv;

import java.util.Properties;
import java.io.InputStreamReader;
import java.util.MissingResourceException;

import com.alibaba.fastjson.*;

import com.github.cuter44.letv.reqs.*;

public class LetvFactory
{
    public static final String RESOURCE_LETV_PROPERTIES = "/letv.properties";
    public static final String KEY_LETV_PROPERTIES = "com.github.cuter44.letv.letv_properties";

    protected Properties conf;

  // SINGLETON
    private static class Singleton
    {
        public static final LetvFactory instance = new LetvFactory();
    }

    /**
     * return instance constructed by default
     */
    public static LetvFactory getInstance()
    {
        return(Singleton.instance);
    }

  // CONSTRUCT
    public LetvFactory(Properties aConf) {
        this.conf = aConf;

        return;
    }

    /**
     * Default constructor which looking for a .properties resource in order below:
     * 1. System.getProperty("com.github.cuter44.letv.letv_json")
     * 2. LetvFactory.class.getResource("/letv.json")
     * json MUST stored in utf-8.
     */
    public LetvFactory()
        throws MissingResourceException
    {
        String res = "";

        try
        {
            res = System.getProperty(KEY_LETV_PROPERTIES);
            res = res!=null ? res : RESOURCE_LETV_PROPERTIES;

            this.conf = new Properties();
            this.conf.load(
                new InputStreamReader(
                    LetvFactory.class.getResourceAsStream(res),
                    "utf-8"
            ));
        }
        catch (Exception ex)
        {
            MissingResourceException mrex = new MissingResourceException(
                "Failed to load conf resource.",
                LetvFactory.class.getName(),
                res
            );
            mrex.initCause(ex);

            throw(mrex);
        }
    }

  // FACTORY
    public VideoUploadInit newVideoUploadInit()
    {
        return(
            new VideoUploadInit(
                new Properties(this.conf)
        ));
    }

    public VideoUploadInit newVideoUploadInit(Properties p)
    {
        return(
            new VideoUploadInit(
                buildConf(p, this.conf)
        ));
    }

  // AUX
    protected static Properties buildConf(Properties prop, Properties defaults)
    {
        Properties ret = new Properties(defaults);
        for (String key:prop.stringPropertyNames())
            ret.setProperty(key, prop.getProperty(key));

        return(ret);
    }


}
