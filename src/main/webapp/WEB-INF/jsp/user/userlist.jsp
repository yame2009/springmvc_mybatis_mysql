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
    <c:forEach items="${users}" var="user" varStatus="status">
                <tr>
                    <!-- ${status.index+1}编号 -->
                    <td><c:out value="${user.id}" /></td>
                    <td><c:out value="${user.username}" /></td>
                    <td><c:out value="${user.password}" /></td>
                    <td><c:if test="${user.sex == 1}">
                            <c:out value="男" />
                        </c:if> <c:if test="${user.sex == 0}">
                            <c:out value="女" />
                        </c:if></td>
                    <td><c:out value="${user.email}" /></td>
                    <td>
                        <a href="/user/detail/${user.id}">detail</a>  |
                        <a href="/user/toupdate/${user.id}">update</a> |
                        <a href="/user/delete/${user.id}">delete</a>
                    </td>
                </tr>
    </c:forEach>
    
  </body>
</html>