package com.github.cuter44.letv.reqs;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.io.UnsupportedEncodingException;
import java.io.IOException;

import com.alibaba.fastjson.*;
import static com.github.cuter44.util.crypto.CryptoUtil.MD5Digest;
import static com.github.cuter44.util.crypto.CryptoUtil.byteToHex;
import com.github.cuter44.util.text.URLBuilder;
import org.apache.http.client.fluent.*;
import org.apache.http.*;

import com.github.cuter44.letv.LetvException;

public abstract class RequestBase
{
  // CONSTANT
    public static final String KEY_KEY = "KEY";
    public static final String KEY_GATEWAY = "GATEWAY";
    public static final String KEY_API = "api";
    public static final String KEY_TIMESTAMP = "timestamp";
    public static final String KEY_SIGN = "sign";
    public static final String KEY_RESP_CODE = "code";
    public static final String KEY_RESP_DATA = "data";
    public static final String KEY_RESP_MESSAGE = "message";
    //public static final List<String> KEYS_PARAM_NAME = Arrays.asList(
        //"api",
        //"format",
        //"timestamp",
        //"user_unique",
        //"ver"
    //);

  // CONSTRUCT
    public RequestBase(Properties aConf)
    {
        this.conf = aConf;
        return;
    }

  // CONFIG
    protected Properties conf;

    public String getProperty(String key)
    {
        return(
            this.conf.getProperty(key)
        );
    }

    /**
     * chain supported
     */
    public RequestBase setProperty(String key, String value)
    {
        this.conf.setProperty(key, value);
        return(this);
    }

    /**
     * batch setProperty
     */
    public RequestBase setProperties(Map aConf)
    {
        this.conf.putAll(aConf);
        return(this);
    }

  // BUILD
    protected RequestBase setTimestamp()
    {
        this.setTimestamp(
            System.currentTimeMillis()/1000
        );

        return(this);
    }

    protected RequestBase setTimestamp(long unixTimestamp)
    {
        this.setProperty(KEY_TIMESTAMP, String.valueOf(unixTimestamp));

        return(this);
    }

    /**
     * must use super.build() in sub-class
     */
    public RequestBase build()
    {
        this.setTimestamp();

        return(this);
    }

  // SIGN
    /** sign
     */
    public abstract RequestBase sign();
    //public RequestBase sign()
    //{
        //this.setProperty(
            //KEY_SIGN,
            //this.sign(KEYS_PARAM_NAME)
        //);

        //return(this);
    //}

    /**
     * @param paramNames key names to submit, in dictionary order
     */
    protected String sign(List<String> paramNames)
    {
        return(
            this.signMD5(paramNames)
        );
    }

    /**
     * experimental
     */
    protected String signMD5(List<String> paramNames)
    {
        try
        {
            StringBuilder sb = new StringBuilder();
            String value;

            for (String key:paramNames)
                if ((value=this.getProperty(key))!=null)
                    sb.append(key).append(value);
            sb.append(this.getProperty(KEY_KEY));

            String sign = byteToHex(
                MD5Digest(sb.toString().getBytes("utf-8"))
            );

            return(sign);
        }
        catch (UnsupportedEncodingException ex)
        {
            // never happen
            ex.printStackTrace();
            return(null);
        }
    }

  // TO_URL
    /** Provide query string to sign().
     */
    protected String toQueryString(List<String> paramNames)
    {
        URLBuilder ub = new URLBuilder();

        for (String key:paramNames)
        {
            String value = this.getProperty(key);

            if (value!=null)
                ub.appendParam(key, value);
        }

        return(ub.toString());
    }

    /** Generate full URL for execute
     * Auxiliary function to generate excat URL of the request, which can be executed correctly by a http-client.
     */
    public abstract String toURL();

    protected String toSignedURL(List<String> paramNames)
    {
        URLBuilder ub = new URLBuilder();

        ub.appendPath(this.getProperty(KEY_GATEWAY));

        for (String key:paramNames)
            ub.appendParamEncode(key, this.getProperty(key));

        if (!paramNames.contains(KEY_SIGN))
            ub.appendParamEncode(KEY_SIGN, this.getProperty(KEY_SIGN));

        return(ub.toString());
    }

  // EXECUTE
    /** Execute the constructed query
     * @return the <code>data</code> object nested in response json.
     */
    public JSONObject execute()
    {
        try
        {
            String jsonStr = Request.Get(this.toURL())
                .execute()
                .returnContent()
                .asString();

            JSONObject j = JSONObject.parseObject(jsonStr);

            if (j.getIntValue(KEY_RESP_CODE)!=0)
                throw(new LetvException(
                    j.getIntValue(KEY_RESP_CODE),
                    j.getString(KEY_RESP_MESSAGE)
                ));

            return(j.getJSONObject(KEY_RESP_DATA));
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
            throw(new LetvException(ex));
        }
    }

}
