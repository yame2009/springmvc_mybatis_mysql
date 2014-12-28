<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>欢迎你！！！</h1>
	访问地址：http://localhost:8089/test/index2.do<div>
	    <c:out value="${liming}"></c:out>  
	</div>
	
	<script>
  window.location.href=" <c:url value="/index.html"/>";
</script>
    
</body>
</html>