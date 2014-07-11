package com.github.cuter44.util.http;

import java.util.Enumeration;
import java.net.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.http.*;
import org.apache.http.client.fluent.*;

public class HttpProxy extends HttpServlet
{
    @Override
    public void doGet(HttpServletRequest inReq, HttpServletResponse outResp)
    {
        try
        {
            // url
            String url = inReq.getParameter("url");
            String host = url.split("/")[2];
            System.out.println(url);

            // get
            Request outReq = Request.Get(url);

            System.out.println();

            // header
            Enumeration<String> reqHeaders = inReq.getHeaderNames();
            while (reqHeaders.hasMoreElements())
            {
                String key = reqHeaders.nextElement();
                outReq.setHeader(key, inReq.getHeader(key));

                System.out.println(key+"="+inReq.getHeader(key));
            }
            outReq.setHeader("Host", host);

            System.out.println();

            HttpResponse inResp = outReq.execute().returnResponse();

            // header
            Header[] respHeaders = inResp.getAllHeaders();
            for (int i=0; i<respHeaders.length; i++)
            {
                outResp.setHeader(respHeaders[i].getName(), respHeaders[i].getValue());
                System.out.println(respHeaders[i].getName()+"="+respHeaders[i].getValue());

            }

            // content
            inResp.getEntity().writeTo(
                outResp.getOutputStream()
            );

            //outResp.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
    }

}
