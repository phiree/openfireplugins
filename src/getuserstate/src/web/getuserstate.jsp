<%@ page language="java" import="java.util.*,org.jivesoftware.openfire.util.VariableUtil,org.jivesoftware.util.*" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jstl/fmt_rt" prefix="fmt" %>

<%
String urlvalue =VariableUtil.url;

%>

<html>
    <head>
        <title>设置网址</title>
        <meta name="pageID" content="getuserstate"/>
    </head>
    <body>

<form action="/plugins/getuserstate/seturl" method="post">    
<fieldset>
    <legend>设置发送的网址</legend>
    <div>
    <p>
          网址:<input type="text" name="url" value="<%=urlvalue%>" style="width: 80%">
    </p>
    </div>
</fieldset>

<br><br>

<input type="submit" value="保存">
</form>

</body>
</html>