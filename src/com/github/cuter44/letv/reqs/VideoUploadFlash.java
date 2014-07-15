package com.github.cuter44.letv.reqs;

import java.util.List;
import java.util.Properties;
import java.util.Arrays;

public class VideoUploadFlash extends RequestBase
{
    public static final String KEY_CLIENT_IP = "client_id";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "api",
        "client_ip",
        "flash_height",
        "flash_width",
        "format",
        "js_callback",
        "timestamp",
        "user_unique",
        "ver",
        "video_name"
    );

    public VideoUploadFlash(Properties prop)
    {
        super(prop);

        this.setProperty(KEY_API, "video.upload.flash");

        return;
    }

  // BUILD
    /**
     * build() invoke this method
     */
    public VideoUploadFlash setClientIp(String clientIp)
    {
        this.setProperty(KEY_CLIENT_IP, clientIp);

        return(this);
    }

    @Override
    public VideoUploadFlash build()
    {
        super.build();

        return(this);
    }

  // SIGN
    @Override
    public VideoUploadFlash sign()
    {
        this.setProperty(
            KEY_SIGN,
            super.sign(KEYS_PARAM_NAME)
        );

        return(this);
    }

  // TO_URL
    @Override
    public String toURL()
    {
        return(
            super.toSignedURL(KEYS_PARAM_NAME)
        );
    }

  // EXECUTE
    // use super.execute()
}
