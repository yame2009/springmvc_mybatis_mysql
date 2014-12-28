<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>

<script src="//ajax.googleapis.com/ajax/libs/jquery/1.8.3/jquery.min.js"></script>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <form:form modelAttribute="accountmodel" method="post">     
        用户名：<form:input path="username"/><br/>
        密 码：<form:password path="password"/><br/>
        <input type="button" id="submit" value="Submit" />
    </form:form> 
    
    <script type="text/javascript">
        $(function() { 
            $("#submit").click(function() {   
                var postdata = '{"username":"' + $('#username').val() + '","password":"' + $('#password').val() + '"}';   
                $.ajax({  
                    type : 'POST',  
                    contentType : 'application/json',  
                    url : 'http://localhost:8089/tets/databind/requestbodybind',  
                    processData : false,  
                    dataType : 'json',  
                    data : postdata,  
                    success : function(data) {  
                        alert('username : '+data.username+'\npassword : '+data.password);  
                    },  
                    error : function() {  
                        alert('error...');  
                    }  
                }); 
            });
        });
    </script> 
</body>
</html>