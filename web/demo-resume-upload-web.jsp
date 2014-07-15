<%@ page contentType="text/html; charset=UTF-8" language="java" errorPage="" %>
<%@ page import="com.github.cuter44.letv.*,com.github.cuter44.letv.reqs.*,com.alibaba.fastjson.*" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
  <title>Resume upload demo</title>
  <meta http-equiv="content-type" content="text/html; charset=UTF-8">
  <!--
    This page is to demostrate the resume.
    Once you understand how it works, you'd better write your own one.
    And, don't submit issue about this page but not relevant with sdk.
  -->
 </head>
 <body>
  <%
    String token = request.getParameter("token");
    if (token == null)
    {
      out.println("require token.");
      return;
    }
  %>
  <%
    VideoUploadResume req = LetvFactory.getInstance().newVideoUploadResume();
    req.setToken(token);

    System.out.println(req.build().sign().toURL());

    JSONObject j = req.build().sign().execute();
    System.out.println(j.toJSONString());

    String upload_url = j.getString("upload_url");
    String progress_url = j.getString("progress_url");

  %>
  <div style="width:100%">
    <form id="form_upload" action="<%=upload_url%>" enctype="multipart/form-data" method="post">
      <input type="file" name="video_file" accept="video/*" />
      <input type="submit" onclick="javascript:doSubmit();" />
      <a href="demo-resume-upload-web.jsp?token=<%=token%>">续传</a>
    </form>
    <div id="progress" style="height:6px; width:0px; background-color:#007722;"></div>
  <div>
  <script>
    
    function doSubmit()
    {
      document.getElementById("form_upload").submit();
      setTimeout("ajaxProgress()", 2000);
    }

    function ajaxProgress()
    {
      xhr = new XMLHttpRequest();
      xhr.open("GET", "<%=request.getContextPath()%>/http-proxy?url="+encodeURIComponent("<%=progress_url%>"));
      xhr.onloadend=function(){
        if (xhr.readyState!=4)
          return;

        console.log(xhr);
        var j = JSON.parse(xhr.responseText);
        var strWidth = (j.data.upload_size/j.data.total_size*100)+"%";
        document.getElementById("progress").style.width = strWidth;
        setTimeout("ajaxProgress()", 2000);
      };
      xhr.send();
    }
  </script>

 </body>
</html>
