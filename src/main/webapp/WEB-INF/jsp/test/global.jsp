<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>    
    <title>spring国际化</title>
  </head>
  
  <body style="font-size: 13px;">
  
      <span>
          
          <a href="global.html?locale=zh">中文版</a> | <!-- 对应 messages_zh.properties文件-->
          <a href="global.html?locale=ja">日文版</a> |    <!-- 对应 messages_ja.properties文件-->
          <a href="global.html?locale=ko">韩文版</a> |    <!-- 对应 messages_ko.properties文件-->
          <a href="global.html?locale=en">英文版</a>     <!-- 对应 messages_en.properties文件-->
      </span>
      
      <!-- 使用message 标签配置需要显示的国际化文本, 
           code对应国际化文件中对应的键的名称,arguments 对应国际化属性文件中的参数。 -->
    <p>
           <spring:message code="main.title" arguments="苏若年,林允熙"/> ,
           <spring:message code="main.target"/>
    </p>
     <p/>
     
    下面展示的是后台获取的国际化信息：<br/>
    ${money}<br/>
    ${date}<br/>

    下面展示的是视图中直接绑定的国际化信息：<br/>
    <spring:message code="money"/>:<br/>
    <spring:eval expression="contentModel.money"></spring:eval><br/>
    <spring:message code="date"/>:<br/>
    <spring:eval expression="contentModel.date"></spring:eval><br/>
    
  </body>
</html>