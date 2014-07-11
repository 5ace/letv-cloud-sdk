package com.github.cuter44.letv;

import com.alibaba.fastjson.*;

import com.github.cuter44.letv.reqs.*;

public class stub
{
    public static void demoVideoUploadInit()
    {
        LetvFactory lf = LetvFactory.getInstance();

        RequestBase req = lf.newVideoUploadInit()
            .setProperty("video_name", "miao.le.ge.mi");

        System.out.println(
            req.build().sign().toURL()
        );

        System.out.println(
            req.execute().toJSONString()
        );

        return;
    }

    public static void main(String[] args)
    {
        demoVideoUploadInit();
    }
}
