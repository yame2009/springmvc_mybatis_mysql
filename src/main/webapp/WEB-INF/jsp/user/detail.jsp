<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path;
%>

<!DOCTYPE HTML>
<html>
  <head>
    <base href="<%=basePath%>">
    <title><%=basePath%></title>
  </head>
  
  <body>
    <h2>用户详细信息页面</h2> <br>
        姓名:     ${user.username}  <br>
        密码:    ${user.password } <br>
        邮箱:    ${user.email } <br/>
        <a href="<%=basePath %>/user/users">返回用户列表</a>
        
  </body>
</html>