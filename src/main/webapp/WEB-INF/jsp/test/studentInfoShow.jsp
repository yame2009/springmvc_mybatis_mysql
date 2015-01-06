<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    用户名：${studentInfo.username}<br/>
    密 码：${studentInfo.password}<br/>
    生日：${studentInfo.birthday}<br/>
    
    <br/>
    <br/>
     用户名:<br/>
    <spring:eval expression="studentInfo.username></spring:eval><br/>
    密 码:<br/>
    <spring:eval expression="studentInfo.password"></spring:eval><br/>
     生日日期:<br/>
    <spring:eval expression="studentInfo.birthday"></spring:eval><br/>
    
</body>
</html>