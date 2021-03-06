<%@page import="com.up72.auth.model.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib tagdir="/WEB-INF/tags/simpletable" prefix="simpletable"%>
<%@ include file="/common/taglibs.jsp" %>
<up72:override name="head">
<title><%=WorkGroup.TABLE_ALIAS%> 维护</title>
<script src="${ctx}/scripts/rest.js" ></script>		
<link href="${ctx}/scripts/simpletable/simpletable.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/scripts/simpletable/simpletable.js"></script>
<link href="${ctx}/scripts/grid/css/flexigrid.css" type="text/css" rel="stylesheet">
<script type="text/javascript" src="${ctx}/scripts/grid/flexigrid-source.js"></script>
<script type="text/javascript" >
	$(document).ready(function() {
		// 分页需要依赖的初始化动作
		window.simpleTable = new SimpleTable('admin_auth_workGroup_search_form',${page.thisPageNumber},${page.pageSize},'${pageRequest.sortColumns}');
	});
</script>
</up72:override>

<up72:override name="content">
<!-- 当前位置 -->
<div class="head_content">
   		<div class="navBar"  style="display: none;">  » <a class="" href="${ctx}/admin/auth/workGroup" ><%=WorkGroup.TABLE_ALIAS%>管理</a></div>
</div>
<!-- END 当前位置 -->
<!--查询-->
<div class="up72_search">
  <form method="get" id="admin_auth_workGroup_search_form" name="admin_auth_workGroup_search_form" action="${ctx}/admin/auth/workGroup">
  	<input type="hidden" id="AUTH_PERM_ID" name="AUTH_PERM_ID" value="${AUTH_PERMISSION.id}" />
	<div class="search_con">
		<div class="search_txt"><%=WorkGroup.ALIAS_NAME%>：<input type="text" id="name" name="name" class="input_txt" value="${query.name}"></div>
		<div class="search_btn"><div class="input_button"><button name="btnU" type="submit" onclick="$(this).parents('form').submit();" class="button" value="查询"><span>查询</span></button></div></div>
	</div>
  </form>
</div>
<!--end查询-->
<form id="admin_auth_workGroup_page_form" name="admin_auth_workGroup_page_form">
  	<table id="gridTable">
		<thead>
			<tr>
				<th showColumn="checkbox" width="25"><input type="checkbox" id="checkall" onclick="setAllCheckboxState('items',this.checked);" /></th>
				<th showColumn="index" width="20">序号</th>
				<th showColumn="option" width="30"><label>操作</label></th>
				<th showColumn="name" sortColumn="NAME" width="180"><%=WorkGroup.ALIAS_NAME%></th>
				<th showColumn="addTime" sortColumn="ADD_TIME" width="100"><%=WorkGroup.ALIAS_ADD_TIME%></th>
				<th showColumn="remark" sortColumn="REMARK" width="100"><%=WorkGroup.ALIAS_REMARK%></th>
				<th showColumn="enabled" sortColumn="ENABLED"  width="50"><%=WorkGroup.ALIAS_ENABLED%></th>
				<th showColumn="manager" sortColumn="MANAGER"  width="100"><%=WorkGroup.ALIAS_MANAGER%></th>
			</tr>
		</thead>
		<tbody>
	  <c:forEach items="${page.result}" var="item" varStatus="status">
			<tr>
				<td showColumn="checkbox"><input type="checkbox" id="items" name="items" value="${item.id}" class="sel" tags="null"></td>
				<td showColumn="index">${page.thisPageFirstElementNumber + status.index}</td>
				<td showColumn="option"><a href="javascript:;"  onclick="show('${ctx}/admin/auth/workGroup/${item.id}/edit','<%=WorkGroup.TABLE_ALIAS%>编辑',600)" class="sysiconBtnNoIcon">编辑</a>&nbsp;</td>
				<td showColumn="name"><c:out value='${item.name}'/>&nbsp;</td>
				<td showColumn="addTimeDate"><fmt:formatDate value="${item.addTimeDate}" pattern="yyyy-MM-dd"/>&nbsp;</td>
				<td showColumn="remark"><c:out value='${item.remark}'/>&nbsp;</td>
				<td showColumn="enabled">
					<c:choose>
				 		<c:when test="${item.enabled == 1}">启用</c:when>
				 		<c:when test="${item.enabled == 0}">禁用</c:when>
				 	</c:choose>&nbsp;
				</td>
				<td showColumn="manager">&nbsp;</td>
			</tr>
		</c:forEach>
 	 	</tbody>
	</table>
	<simpletable:pageToolbar page="${page}"></simpletable:pageToolbar>
</form>
<script type="text/javascript">
	// 列选择显示处理
	$.showcolumn(${showColumn});
	// 排序处理
	$.sortcolumn({
		form : "#admin_auth_workGroup_search_form",
		data : "pageNumber=${page.thisPageNumber}&pageSize=${page.pageSize}",
		columns : $("#gridTable th[sortColumn]"),
		sortColumns : "${pageRequest.sortColumns}"
	});
	$("#gridTable").rowAction({	
		url : "${ctx}/admin/auth/workGroupMember/",
		except : ["checkbox","index","option"],
		"pop" : false,
		poptitle : "权限机构管理",
		idname : "workGroupId"
	});
	// 表格列表处理
	$('#gridTable').flexigrid({
		height: 'auto',
		striped : true,
		buttons : [
			{name: '添加', bclass: 'addorder', onpress : function(){show('${ctx}/admin/auth/workGroup/new?organizationId=${organizationId}','<%=WorkGroup.TABLE_ALIAS%>添加',600)}},
			{name: '删除', bclass: 'delete', onpress : function(){doRestBatchDelete('${ctx}/admin/auth/workGroup','items',document.forms.admin_auth_workGroup_page_form)}}
		]
	});
</script>
</up72:override>
<%@ include file="base.jsp" %>