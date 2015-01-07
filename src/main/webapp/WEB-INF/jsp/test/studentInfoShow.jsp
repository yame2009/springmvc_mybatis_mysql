<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    用户名：${studentInfo.name}<br/>
    密 码：${studentInfo.password}<br/>
    生日：${studentInfo.birthday}<br/>
    
    
    <br/>
    <br/>
    spring:eval 用户名:<spring:eval expression="studentInfo.name"></spring:eval><br/>
    spring:eval 密 码:<spring:eval expression="studentInfo.password"></spring:eval><br/>
    spring:eval 生日日期:<spring:eval expression="studentInfo.birthday"></spring:eval><br/>
    
</body>
</html>