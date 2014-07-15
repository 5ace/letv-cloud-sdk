<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="com.github.cuter44.letv.*,com.github.cuter44.letv.reqs.*,com.alibaba.fastjson.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title>Web upload demo</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <!--
    This page is to demostrate the upload... and the progress.
    Once you understand how it works, you'd better write your own one.
    And, don't submit issue about this page but not relevant with sdk.
  -->
 </head>
 <body>
  <%
    String video_name = request.getParameter("video_name");
  %>
  <%
    if (video_name == null)
    {
  %>
      <form>
        <input name="video_name" placeholder="视频标题?" />
        <input type="submit" />
      </form>
  <%
    }
    else
    {
      VideoUploadFlash req = LetvFactory.getInstance().newVideoUploadFlash();
      req.setProperty("video_name", video_name)
        .setProperty("client_ip", request.getRemoteAddr());

      JSONObject j = req.build().sign().execute();
      out.println(
        j.getString("flash_upload")
      );
  %>
  <%
    }
  %>
  
 </body>
</html>
