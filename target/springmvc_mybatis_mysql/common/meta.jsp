<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%
   //设置无缓存
   response.setHeader("progma","no-cache");   
   response.setHeader("Cache-Control","no-cache");   
%> 

<c:set var="ctx" value="<%=request.getContextPath() %>"/>
<script src="${ctx }/js/jquery-1.10.2.js" type="text/javascript"></script>
<script src="${ctx }/js/validform_v5.3.2_min.js" type="text/javascript"></script>
<script src="${ctx }/js/ztree/jquery.ztree.all-3.5.min.js" type="text/javascript"></script>
<script src="${ctx }/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
<script src="${ctx }/js/easyui/easyui-lang-zh_CN.js" type="text/javascript"></script>
<script src="${ctx }/js/easyui/validator.js" type="text/javascript"></script>
<script src="${ctx }/js/jPaginate/jquery.paginate.js" type="text/javascript"></script>
<script src="${ctx }/js/util.js" type="text/javascript"></script>
<script src="${ctx }/js/windowHeigth.js" type="text/javascript"></script>

<link href="${ctx }/css/jquery-easyui-1.3.4/easyui.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/jquery-easyui-1.3.4/icon.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/zTreeStyle/zTreeStyle.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/uploadify-v3.1/uploadify.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/public.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/content.css" rel="stylesheet" type="text/css" />
<link href="${ctx }/css/global.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="${ctx }/css/jPaginate/style.css"/>

