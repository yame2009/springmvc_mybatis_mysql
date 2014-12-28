<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <h2>用户信息展示</h2>   <p><a href="<%=basePath %>/user/add">添加信息</a></p>
    <c:forEach items="${users}" var="usermap">
        姓名:     <a href="<%=basePath %>/user/${usermap.value.username }">${usermap.value.username}  </a>
        密码:    ${usermap.value.password }
        邮箱:    ${usermap.value.email }
        <a href="<%=basePath %>/user/${usermap.value.username }/update">修改</a>
        <a href="<%=basePath %>/user/${usermap.value.username }/delete">删除</a>
        <br/>
    </c:forEach>
  </body>
</html>