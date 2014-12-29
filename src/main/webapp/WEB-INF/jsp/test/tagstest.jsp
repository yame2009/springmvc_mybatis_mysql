<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
    <form:form modelAttribute="contentModel" method="post">     
        
        input 标签：<form:input path="username"/><br/>
        password 标签：<form:password path="password"/><br/>
        绑定boolean的checkbox 标签：<br/>
        <form:checkbox path="testBoolean"/><br/>
        绑定Array的checkbox 标签：<br/>
        <form:checkbox path="testArray" value="arrayItem 路人甲"/>arrayItem 路人甲
        <form:checkbox path="testArray" value="arrayItem 路人乙"/>arrayItem 路人乙
        <form:checkbox path="testArray" value="arrayItem 路人丙"/>arrayItem 路人丙
        <form:checkbox path="testArray" value="arrayItem 路人丁"/>arrayItem 路人丁<br/>
        绑定Array的checkboxs 标签：<br/>
        <form:checkboxes path="selectArray" items="${contentModel.testArray}"/><br/>
        绑定Map的checkboxs 标签：<br/>
        <form:checkboxes path="selectIds" items="${contentModel.testMap}"/><br/>
        绑定Integer的radiobutton 标签：<br/>
        <form:radiobutton path="radiobuttonId" value="0"/>0
        <form:radiobutton path="radiobuttonId" value="1"/>1
        <form:radiobutton path="radiobuttonId" value="2"/>2<br/>
        绑定Map的radiobuttons 标签：<br/>
        <form:radiobuttons path="selectId" items="${contentModel.testMap}"/><br/>
        绑定Map的select 标签：<br/>
        <form:select path="selectId" items="${contentModel.testMap}"/><br/>
        不绑定items数据直接在form:option添加的select 标签：<br/>
        <form:select path="selectId">  
           <option>请选择人员</option>
           <form:option value="1">路人甲</form:option>
           <form:option value="2">路人乙</form:option>
           <form:option value="3">路人丙</form:option>
        </form:select><br/>
        不绑定items数据直接在html的option添加的select 标签：<br/>
        <form:select path="selectId">  
           <option>请选择人员</option> 
           <option value="1">路人甲</option>
           <option value="2">路人乙</option>
           <option value="3">路人丙</option>  
        </form:select><br/>
        用form:option绑定items的select 标签：<br/>
        <form:select path="selectId">  
            <option/>请选择人员
            <form:options items="${contentModel.testMap}"/>  
        </form:select><br/>
        textarea 标签：
        <form:textarea path="remark"/><br/>

        <input type="submit" value="Submit" />
        
    </form:form>  
</body>
</html>