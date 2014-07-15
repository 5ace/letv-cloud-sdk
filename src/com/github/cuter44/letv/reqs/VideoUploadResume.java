package com.github.cuter44.letv.reqs;

import java.util.List;
import java.util.Properties;
import java.util.Arrays;

/**
 * video upload can be resume in 24h
 */
public class VideoUploadResume extends RequestBase
{
    public static final String KEY_TOKEN = "token";

    public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        "api",
        "format",
        "timestamp",
        "token",
        "user_unique",
        "ver"
    );

    public VideoUploadResume(Properties prop)
    {
        super(prop);

        this.setProperty(KEY_API, "video.upload.resume");

        return;
    }

  // BUILD
    public VideoUploadResume setToken(String token)
    {
        this.setProperty(KEY_TOKEN, token);

        return(this);
    }

    @Override
    public VideoUploadResume build()
    {
        super.build();

        return(this);
    }

  // SIGN
    @Override
    public VideoUploadResume sign()
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
