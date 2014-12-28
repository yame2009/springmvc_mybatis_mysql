<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/common/meta.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>SpringMVC+Hibernate +MySql+ EasyUI ---CRUD</title>
<script type="text/javascript">
	var searchString;

	function resizeDg(){
		$('#dg').datagrid("resize", { width: $(window).width() * 0.4});
	}
	
	function getCookie(c_name){
	     if (document.cookie.length>0){
		  c_start=document.cookie.indexOf(c_name + "=");
		  if (c_start!=-1){ 
		    c_start=c_start + c_name.length+1;
		    c_end=document.cookie.indexOf(";",c_start);
		    if (c_end==-1) {
		    	c_end=document.cookie.length;
		    }
		    return document.cookie.substring(c_start,c_end);
		   } 
		  }
		return "";
	}
	
	var pageSize = 20;
	var pageNumber = 1;
	var sortName = '';
	var sortOrder = '';
	function initDate(){
		var s = getCookie("role");
		s = decodeURIComponent(s);
		if(s != null && s != ""){
			searchMap = eval('(' + s + ')');
			pageSize = searchMap.rows;
			if(pageSize == null || pageSize == ""){
				pageSize = 20;
			}
			pageNumber = searchMap.pageNumber;
			sortName = searchMap.sortName;
			sortOrder = searchMap.sortOrder;
			$("#name").val(searchMap.name );
		}
	}
	
	$(function(){
		 $("#doSearch").click(function(){
			doSearch();
		});
		initDate();
		var name=$("#name").val();
		$('#dg').datagrid({
		    url:"${ctx }/user/list",
			pagination:true,
			singleSelect:true,
		    pageSize:pageSize,
		    pageNumber:pageNumber,
		    sortOrder:sortOrder,
		    sortName:sortName,
		    queryParams:{  
		        name:name,
		    },
		    width:800,
		   	columns:[[
		   		{field:'name',title:'名称', width:100, align:"center",sortable:true},
		   		{field:'age',title:'年龄', width:50, align:"center",sortable:true},
		   		{field:'address',title:'地址', width:50, align:"center",sortable:true},
		   		{field:'operation',title:'操作', width:340, align:"center", sortable:false,
		   			formatter:function(value,row,index){
		   				var s ="";
		                s+="<a href=\"javascript:void(0)\"><span onclick=\"javaScript:gotoModify('"+row.id+"');\">修改</span></a>";
               			s += "|";
		                s+="<a href=\"javascript:void(0)\"><span onclick=\"javaScript:gotoDel('"+row.id+"');\">删除</span>&nbsp;&nbsp;</a>";
			            return s;
		   			}
		   		}
		   	]]
		});
		 var p = $('#dg').datagrid('getPager');    
         $(p).pagination({    
              pageList: [10,20,50,100]
          });  
		
		$("#doSearch").click(function(){
			doSearch();
		});
	});
	
	
	function gotoAdd(){
		var url = '${ctx }/user/gotoAdd';
		window.location.href=url;
	}
	function gotoModify(id){
		var url = '${ctx}/user/gotoModify?id='+id;
		window.location.href=url;
	}
	function gotoDel(id){
		if(!confirm('确定删除所选记录？')){
			return;
		}
		var url = '${ctx}/user/delete?id='+id;
		$.ajax({
			type : 'post',
			url : url,
			dataType: "json",
    			success:function(data){
					if(data.success == true){
						doSearch();
					}else{
						alert(data.msg);
					}
				}
			});
	}
		
	function doSearch(){
		var name=$("#name").val();
		/* var schoolId=$("#schoolId").val(); */
		$("#dg").datagrid('load',{  
	        name:name
	    }); //重新载入 
	}
		
</script>
</head>
<body onload="resizeDg();" onresize="resizeDg();" >
<div class="neirong">
<div class="add-content" style="margin-top:0">
	<div class="xinxi2">
       	<div class="search_box">
           <p>名称： <input name="name" id="name" type="text" /></p>
           <a href="javascript:void(0);" id="doSearch" class="blank_btn">查询</a></div>
           <div class="btn_div">
           <a href="javascript:void(0);" onclick="gotoAdd();" id="xtsz_rygl_jsgl_add" class="blank_btn">新增</a>
           </div>
       </div>
	<div class="contant_list" >
		<!-- c_top start-->
		<table  width="100%">
			<tr>
				<td>
					<table id="dg"></table>
				</td>
			</tr>
		</table>
	</div>
  </div>
</div>
</body>
</html>